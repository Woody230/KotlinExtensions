/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package androidx.constraintlayout.core.widgets

import androidx.constraintlayout.core.widgets.analyzer.Direct.solveChain
import androidx.constraintlayout.core.LinearSystem
import androidx.constraintlayout.core.SolverVariable

/**
 * Chain management and constraints creation
 */
object Chain {
    private const val DEBUG = false
    const val USE_CHAIN_OPTIMIZATION = false

    /**
     * Apply specific rules for dealing with chains of widgets.
     * Chains are defined as a list of widget linked together with bi-directional connections
     * @param constraintWidgetContainer root container
     * @param system the linear system we add the equations to
     * @param widgets
     * @param orientation HORIZONTAL or VERTICAL
     */
    @JvmStatic
    fun applyChainConstraints(constraintWidgetContainer: ConstraintWidgetContainer, system: LinearSystem, widgets: ArrayList<ConstraintWidget>?, orientation: Int) {
        // what to do:
        // Don't skip things. Either the element is GONE or not.
        var offset = 0
        var chainsSize = 0
        var chainsArray: Array<ChainHead?>? = null
        if (orientation == ConstraintWidget.HORIZONTAL) {
            offset = 0
            chainsSize = constraintWidgetContainer.mHorizontalChainsSize
            chainsArray = constraintWidgetContainer.mHorizontalChainsArray
        } else {
            offset = 2
            chainsSize = constraintWidgetContainer.mVerticalChainsSize
            chainsArray = constraintWidgetContainer.mVerticalChainsArray
        }
        for (i in 0 until chainsSize) {
            val first = chainsArray[i] ?: continue
            // we have to make sure we define the ChainHead here, otherwise the values we use may not
            // be correctly initialized (as we initialize them in the ConstraintWidget.addToSolver())
            first.define()
            if (widgets == null || widgets != null && widgets.contains(first.first)) {
                applyChainConstraints(constraintWidgetContainer, system, orientation, offset, first)
            }
        }
    }

    /**
     * Apply specific rules for dealing with chains of widgets.
     * Chains are defined as a list of widget linked together with bi-directional connections
     *
     * @param container the root container
     * @param system the linear system we add the equations to
     * @param orientation HORIZONTAL or VERTICAL
     * @param offset 0 or 2 to accommodate for HORIZONTAL / VERTICAL
     * @param chainHead a chain represented by its main elements
     */
    fun applyChainConstraints(
        container: ConstraintWidgetContainer, system: LinearSystem,
        orientation: Int, offset: Int, chainHead: ChainHead
    ) {
        val first = chainHead.first
        val last = chainHead.last
        val firstVisibleWidget = chainHead.firstVisibleWidget
        var lastVisibleWidget = chainHead.lastVisibleWidget
        val head = chainHead.head
        var widget: ConstraintWidget? = first
        var next: ConstraintWidget? = null
        var done = false
        var totalWeights = chainHead.totalWeight
        val firstMatchConstraintsWidget = chainHead.firstMatchConstraintWidget
        val previousMatchConstraintsWidget = chainHead.lastMatchConstraintWidget
        val isWrapContent = container.mListDimensionBehaviors[orientation] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
        var isChainSpread = false
        var isChainSpreadInside = false
        var isChainPacked = false
        if (orientation == ConstraintWidget.HORIZONTAL) {
            isChainSpread = head?.horizontalChainStyle == ConstraintWidget.CHAIN_SPREAD
            isChainSpreadInside = head?.horizontalChainStyle == ConstraintWidget.CHAIN_SPREAD_INSIDE
            isChainPacked = head?.horizontalChainStyle == ConstraintWidget.CHAIN_PACKED
        } else {
            isChainSpread = head?.verticalChainStyle == ConstraintWidget.CHAIN_SPREAD
            isChainSpreadInside = head?.verticalChainStyle == ConstraintWidget.CHAIN_SPREAD_INSIDE
            isChainPacked = head?.verticalChainStyle == ConstraintWidget.CHAIN_PACKED
        }
        if (USE_CHAIN_OPTIMIZATION && !isWrapContent && solveChain(
                container, system, orientation, offset, chainHead,
                isChainSpread, isChainSpreadInside, isChainPacked
            )
        ) {
            if (LinearSystem.FULL_DEBUG) {
                println("### CHAIN FULLY SOLVED! ###")
            }
            return  // done with the chain!
        } else if (LinearSystem.FULL_DEBUG) {
            println("### CHAIN WASN'T SOLVED DIRECTLY... ###")
        }

        // This traversal will:
        // - set up some basic ordering constraints
        // - build a linked list of matched constraints widgets
        while (!done) {
            val begin = widget!!.mListAnchors[offset]
            var strength = SolverVariable.STRENGTH_HIGHEST
            if (isChainPacked) {
                strength = SolverVariable.STRENGTH_LOW
            }
            var margin = begin?.margin ?: 0
            val isSpreadOnly = (widget.mListDimensionBehaviors[orientation] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
                    && widget.mResolvedMatchConstraintDefault[orientation] == ConstraintWidget.MATCH_CONSTRAINT_SPREAD)
            if (begin?.target != null && widget !== first) {
                margin += begin.target!!.margin
            }
            if (isChainPacked && widget !== first && widget !== firstVisibleWidget) {
                strength = SolverVariable.STRENGTH_FIXED
            }
            if (begin?.target != null) {
                if (widget === firstVisibleWidget) {
                    system.addGreaterThan(
                        begin.solverVariable, begin.target?.solverVariable,
                        margin, SolverVariable.STRENGTH_BARRIER
                    )
                } else {
                    system.addGreaterThan(
                        begin.solverVariable, begin.target?.solverVariable,
                        margin, SolverVariable.STRENGTH_FIXED
                    )
                }
                if (isSpreadOnly && !isChainPacked) {
                    strength = SolverVariable.STRENGTH_EQUALITY
                }
                if (widget === firstVisibleWidget && isChainPacked && widget.isInBarrier(orientation)) {
                    strength = SolverVariable.STRENGTH_EQUALITY
                }
                system.addEquality(
                    begin.solverVariable, begin.target?.solverVariable, margin,
                    strength
                )
            }
            if (isWrapContent) {
                if (widget.visibility != ConstraintWidget.GONE
                    && widget.mListDimensionBehaviors[orientation] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
                ) {
                    system.addGreaterThan(
                        widget.mListAnchors[offset + 1]?.solverVariable,
                        widget.mListAnchors[offset]?.solverVariable, 0,
                        SolverVariable.STRENGTH_EQUALITY
                    )
                }
                system.addGreaterThan(
                    widget.mListAnchors[offset]?.solverVariable,
                    container.mListAnchors[offset]?.solverVariable,
                    0, SolverVariable.STRENGTH_FIXED
                )
            }

            // go to the next widget
            val nextAnchor = widget.mListAnchors[offset + 1]?.target
            if (nextAnchor != null) {
                next = nextAnchor.owner
                if (next.mListAnchors[offset]?.target == null || next.mListAnchors[offset]?.target?.owner !== widget) {
                    next = null
                }
            } else {
                next = null
            }
            if (next != null) {
                widget = next
            } else {
                done = true
            }
        }

        // Make sure we have constraints for the last anchors / targets
        if (lastVisibleWidget != null && last?.mListAnchors?.get(offset + 1)?.target != null) {
            val end = lastVisibleWidget.mListAnchors[offset + 1]
            val isSpreadOnly = (lastVisibleWidget.mListDimensionBehaviors[orientation] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
                    && lastVisibleWidget.mResolvedMatchConstraintDefault[orientation] == ConstraintWidget.MATCH_CONSTRAINT_SPREAD)
            if (isSpreadOnly && !isChainPacked && end?.target?.owner === container) {
                system.addEquality(
                    end.solverVariable, end.target?.solverVariable, -end.margin,
                    SolverVariable.STRENGTH_EQUALITY
                )
            } else if (isChainPacked && end?.target?.owner === container) {
                system.addEquality(
                    end.solverVariable, end.target?.solverVariable, -end.margin,
                    SolverVariable.STRENGTH_HIGHEST
                )
            }
            system.addLowerThan(
                end?.solverVariable,
                last.mListAnchors[offset + 1]?.target?.solverVariable, -(end?.margin ?: 0),
                SolverVariable.STRENGTH_BARRIER
            )
        }

        // ... and make sure the root end is constrained in wrap content.
        if (isWrapContent) {
            system.addGreaterThan(
                container.mListAnchors[offset + 1]?.solverVariable,
                last?.mListAnchors?.get(offset + 1)?.solverVariable,
                last?.mListAnchors?.get(offset + 1)?.margin ?: 0, SolverVariable.STRENGTH_FIXED
            )
        }

        // Now, let's apply the centering / spreading for matched constraints widgets
        val listMatchConstraints = chainHead.mWeightedMatchConstraintsWidgets
        if (listMatchConstraints != null) {
            val count = listMatchConstraints.size
            if (count > 1) {
                var lastMatch: ConstraintWidget? = null
                var lastWeight = 0f
                if (chainHead.mHasUndefinedWeights && !chainHead.mHasComplexMatchWeights) {
                    totalWeights = chainHead.mWidgetsMatchCount.toFloat()
                }
                for (i in 0 until count) {
                    val match = listMatchConstraints[i]
                    var currentWeight = match.mWeight[orientation]
                    if (currentWeight < 0) {
                        if (chainHead.mHasComplexMatchWeights) {
                            system.addEquality(
                                match.mListAnchors[offset + 1]?.solverVariable,
                                match.mListAnchors[offset]?.solverVariable, 0, SolverVariable.STRENGTH_HIGHEST
                            )
                            continue
                        }
                        currentWeight = 1f
                    }
                    if (currentWeight == 0f) {
                        system.addEquality(
                            match.mListAnchors[offset + 1]?.solverVariable,
                            match.mListAnchors[offset]?.solverVariable, 0, SolverVariable.STRENGTH_FIXED
                        )
                        continue
                    }
                    if (lastMatch != null) {
                        val begin = lastMatch.mListAnchors[offset]?.solverVariable
                        val end = lastMatch.mListAnchors[offset + 1]?.solverVariable
                        val nextBegin = match.mListAnchors[offset]?.solverVariable
                        val nextEnd = match.mListAnchors[offset + 1]?.solverVariable
                        val row = system.createRow()
                        row.createRowEqualMatchDimensions(
                            lastWeight, totalWeights, currentWeight,
                            begin, end, nextBegin, nextEnd
                        )
                        system.addConstraint(row)
                    }
                    lastMatch = match
                    lastWeight = currentWeight
                }
            }
        }
        /*
        if (DEBUG) {
            widget = firstVisibleWidget
            while (widget != null) {
                next = widget.mNextChainWidget[orientation]
                widget.mListAnchors[offset].solverVariable.name = "" + widget.debugName + ".left"
                widget.mListAnchors[offset + 1].solverVariable.name = "" + widget.debugName + ".right"
                widget = next
            }
        }*/

        // Finally, let's apply the specific rules dealing with the different chain types
        if (firstVisibleWidget != null && (firstVisibleWidget === lastVisibleWidget || isChainPacked)) {
            var begin = first.mListAnchors[offset]
            var end = last?.mListAnchors?.get(offset + 1)
            val beginTarget = if (begin?.target != null) begin.target?.solverVariable else null
            val endTarget = if (end?.target != null) end.target?.solverVariable else null
            begin = firstVisibleWidget.mListAnchors[offset]
            if (lastVisibleWidget != null) {
                end = lastVisibleWidget.mListAnchors[offset + 1]
            }
            if (beginTarget != null && endTarget != null) {
                var bias = 0.5f
                bias = if (orientation == ConstraintWidget.HORIZONTAL) {
                    head?.horizontalBiasPercent ?: 0f
                } else {
                    head?.verticalBiasPercent ?: 0f
                }
                val beginMargin = begin?.margin ?: 0
                val endMargin = end?.margin ?: 0
                system.addCentering(
                    begin?.solverVariable, beginTarget, beginMargin, bias,
                    endTarget, end?.solverVariable, endMargin, SolverVariable.STRENGTH_CENTERING
                )
            }
        } else if (isChainSpread && firstVisibleWidget != null) {
            // for chain spread, we need to add equal dimensions in between *visible* widgets
            widget = firstVisibleWidget
            var previousVisibleWidget = firstVisibleWidget
            val applyFixedEquality = chainHead.mWidgetsMatchCount > 0 && chainHead.mWidgetsCount == chainHead.mWidgetsMatchCount
            while (widget != null) {
                next = widget.mNextChainWidget[orientation]
                while (next != null && next.visibility == ConstraintWidget.GONE) {
                    next = next.mNextChainWidget[orientation]
                }
                if (next != null || widget === lastVisibleWidget) {
                    val beginAnchor = widget.mListAnchors[offset]
                    val begin = beginAnchor?.solverVariable
                    var beginTarget = if (beginAnchor?.target != null) beginAnchor.target?.solverVariable else null
                    if (previousVisibleWidget !== widget) {
                        beginTarget = previousVisibleWidget?.mListAnchors?.get(offset + 1)?.solverVariable
                    } else if (widget === firstVisibleWidget) {
                        beginTarget = if (first.mListAnchors[offset]?.target != null) first.mListAnchors[offset]?.target?.solverVariable else null
                    }
                    var beginNextAnchor: ConstraintAnchor? = null
                    var beginNext: SolverVariable? = null
                    var beginNextTarget: SolverVariable? = null
                    var beginMargin = beginAnchor?.margin
                    var nextMargin = widget.mListAnchors[offset + 1]?.margin
                    if (next != null) {
                        beginNextAnchor = next.mListAnchors[offset]
                        beginNext = beginNextAnchor?.solverVariable
                    } else {
                        beginNextAnchor = last?.mListAnchors?.get(offset + 1)?.target
                        if (beginNextAnchor != null) {
                            beginNext = beginNextAnchor.solverVariable
                        }
                    }
                    beginNextTarget = widget.mListAnchors[offset + 1]?.solverVariable
                    if (nextMargin != null) {
                        if (beginNextAnchor != null) {
                            nextMargin += beginNextAnchor.margin
                        }
                    }
                    beginMargin = beginMargin?.plus(previousVisibleWidget?.mListAnchors?.get(offset + 1)?.margin ?: 0)
                    if (begin != null && beginTarget != null && beginNext != null && beginNextTarget != null) {
                        var margin1 = beginMargin
                        if (widget === firstVisibleWidget) {
                            margin1 = firstVisibleWidget.mListAnchors[offset]?.margin
                        }
                        var margin2 = nextMargin
                        if (widget === lastVisibleWidget) {
                            margin2 = lastVisibleWidget.mListAnchors[offset + 1]?.margin
                        }
                        var strength = SolverVariable.STRENGTH_EQUALITY
                        if (applyFixedEquality) {
                            strength = SolverVariable.STRENGTH_FIXED
                        }
                        system.addCentering(
                            begin, beginTarget, margin1 ?: 0, 0.5f,
                            beginNext, beginNextTarget, margin2 ?: 0,
                            strength
                        )
                    }
                }
                if (widget.visibility != ConstraintWidget.GONE) {
                    previousVisibleWidget = widget
                }
                widget = next
            }
        } else if (isChainSpreadInside && firstVisibleWidget != null) {
            // for chain spread inside, we need to add equal dimensions in between *visible* widgets
            widget = firstVisibleWidget
            var previousVisibleWidget = firstVisibleWidget
            val applyFixedEquality = chainHead.mWidgetsMatchCount > 0 && chainHead.mWidgetsCount == chainHead.mWidgetsMatchCount
            while (widget != null) {
                next = widget.mNextChainWidget[orientation]
                while (next != null && next.visibility == ConstraintWidget.GONE) {
                    next = next.mNextChainWidget[orientation]
                }
                if (widget !== firstVisibleWidget && widget !== lastVisibleWidget && next != null) {
                    if (next === lastVisibleWidget) {
                        next = null
                    }
                    val beginAnchor = widget.mListAnchors[offset]
                    val begin = beginAnchor?.solverVariable
                    var beginTarget = if (beginAnchor?.target != null) beginAnchor.target?.solverVariable else null
                    beginTarget = previousVisibleWidget?.mListAnchors?.get(offset + 1)?.solverVariable
                    var beginNextAnchor: ConstraintAnchor? = null
                    var beginNext: SolverVariable? = null
                    var beginNextTarget: SolverVariable? = null
                    var beginMargin = beginAnchor?.margin
                    var nextMargin = widget.mListAnchors[offset + 1]?.margin
                    if (next != null) {
                        beginNextAnchor = next.mListAnchors[offset]
                        beginNext = beginNextAnchor?.solverVariable
                        beginNextTarget = if (beginNextAnchor?.target != null) beginNextAnchor.target?.solverVariable else null
                    } else {
                        beginNextAnchor = lastVisibleWidget!!.mListAnchors[offset]
                        if (beginNextAnchor != null) {
                            beginNext = beginNextAnchor.solverVariable
                        }
                        beginNextTarget = widget.mListAnchors[offset + 1]?.solverVariable
                    }
                    if (nextMargin != null) {
                        if (beginNextAnchor != null) {
                            nextMargin += beginNextAnchor.margin
                        }
                    }
                    beginMargin = beginMargin?.plus(previousVisibleWidget?.mListAnchors?.get(offset + 1)?.margin ?: 0)
                    var strength = SolverVariable.STRENGTH_HIGHEST
                    if (applyFixedEquality) {
                        strength = SolverVariable.STRENGTH_FIXED
                    }
                    if (begin != null && beginTarget != null && beginNext != null && beginNextTarget != null) {
                        if (beginMargin != null) {
                            system.addCentering(
                                begin, beginTarget, beginMargin, 0.5f,
                                beginNext, beginNextTarget, nextMargin ?: 0,
                                strength
                            )
                        }
                    }
                }
                if (widget.visibility != ConstraintWidget.GONE) {
                    previousVisibleWidget = widget
                }
                widget = next
            }
            val begin = firstVisibleWidget.mListAnchors[offset]
            val beginTarget = first.mListAnchors[offset]?.target
            val end = lastVisibleWidget!!.mListAnchors[offset + 1]
            val endTarget = last?.mListAnchors?.get(offset + 1)?.target
            val endPointsStrength = SolverVariable.STRENGTH_EQUALITY
            if (beginTarget != null) {
                if (firstVisibleWidget !== lastVisibleWidget) {
                    system.addEquality(begin?.solverVariable, beginTarget.solverVariable, begin?.margin ?: 0, endPointsStrength)
                } else if (endTarget != null) {
                    system.addCentering(
                        begin?.solverVariable, beginTarget.solverVariable, begin?.margin ?: 0, 0.5f,
                        end?.solverVariable, endTarget.solverVariable, end?.margin ?: 0, endPointsStrength
                    )
                }
            }
            if (endTarget != null && firstVisibleWidget !== lastVisibleWidget) {
                system.addEquality(end?.solverVariable, endTarget.solverVariable, -(end?.margin ?: 0), endPointsStrength)
            }
        }

        // final centering, necessary if the chain is larger than the available space...
        if ((isChainSpread || isChainSpreadInside) && firstVisibleWidget != null && firstVisibleWidget !== lastVisibleWidget) {
            var begin = firstVisibleWidget.mListAnchors[offset]
            if (lastVisibleWidget == null) {
                lastVisibleWidget = firstVisibleWidget
            }
            var end = lastVisibleWidget.mListAnchors[offset + 1]
            val beginTarget = if (begin?.target != null) begin.target?.solverVariable else null
            var endTarget = if (end?.target != null) end.target?.solverVariable else null
            if (last !== lastVisibleWidget) {
                val realEnd = last?.mListAnchors?.get(offset + 1)
                endTarget = if (realEnd?.target != null) realEnd.target?.solverVariable else null
            }
            if (firstVisibleWidget === lastVisibleWidget) {
                begin = firstVisibleWidget.mListAnchors[offset]
                end = firstVisibleWidget.mListAnchors[offset + 1]
            }
            if (beginTarget != null && endTarget != null) {
                val bias = 0.5f
                val beginMargin = begin?.margin ?: 0
                val endMargin = lastVisibleWidget.mListAnchors[offset + 1]?.margin ?: 0
                system.addCentering(
                    begin?.solverVariable, beginTarget, beginMargin, bias,
                    endTarget, end?.solverVariable, endMargin, SolverVariable.STRENGTH_EQUALITY
                )
            }
        }
    }
}