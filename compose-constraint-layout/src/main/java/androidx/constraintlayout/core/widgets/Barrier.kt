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

import androidx.constraintlayout.core.LinearSystem
import androidx.constraintlayout.core.SolverVariable

/**
 * A Barrier takes multiple widgets
 */
class Barrier : HelperWidget {
    var barrierType = LEFT

    /**
     * Find if this barrier supports gone widgets.
     *
     * @return true if this barrier supports gone widgets, otherwise false
     */
    var allowsGoneWidget = true
    var margin = 0
    var resolved = false

    constructor() {}
    constructor(debugName: String?) {
        this.debugName = debugName
    }

    override fun allowedInBarrier(): Boolean {
        return true
    }

    /**
     * Find if this barrier supports gone widgets.
     *
     * @return true if this barrier supports gone widgets, otherwise false
     *
     */
    @Deprecated(
        """This method should be called {@code getAllowsGoneWidget} such that {@code allowsGoneWidget}
      can be accessed as a property from Kotlin; {@see https://android.github.io/kotlin-guides/interop.html#property-prefixes}.
      Use {@link #getAllowsGoneWidget()} instead."""
    )
    fun allowsGoneWidget(): Boolean {
        return allowsGoneWidget
    }

    override val isResolvedHorizontally: Boolean
        get() = resolved

    override val isResolvedVertically: Boolean
        get() = resolved

    override fun copy(src: ConstraintWidget, map: HashMap<ConstraintWidget, ConstraintWidget>) {
        super.copy(src, map)
        val srcBarrier = src as Barrier
        barrierType = srcBarrier.barrierType
        allowsGoneWidget = srcBarrier.allowsGoneWidget
        margin = srcBarrier.margin
    }

    override fun toString(): String {
        var debug = "[Barrier] $debugName {"
        for (i in 0 until mWidgetsCount) {
            val widget = mWidgets[i]
            if (i > 0) {
                debug += ", "
            }
            debug += widget?.debugName
        }
        debug += "}"
        return debug
    }

    fun markWidgets() {
        for (i in 0 until mWidgetsCount) {
            val widget = mWidgets[i]
            if (!allowsGoneWidget && widget?.allowedInBarrier() != true) {
                continue
            }
            if (barrierType == LEFT || barrierType == RIGHT) {
                widget?.setInBarrier(HORIZONTAL, true)
            } else if (barrierType == TOP || barrierType == BOTTOM) {
                widget?.setInBarrier(VERTICAL, true)
            }
        }
    }

    /**
     * Add this widget to the solver
     *
     * @param system the solver we want to add the widget to
     * @param optimize true if [Optimizer.OPTIMIZATION_GRAPH] is on
     */
    override fun addToSolver(system: LinearSystem, optimize: Boolean) {
        if (LinearSystem.FULL_DEBUG) {
            println("\n----------------------------------------------")
            println("-- adding $debugName to the solver")
            println("----------------------------------------------\n")
        }
        val position: ConstraintAnchor
        mListAnchors[LEFT] = mLeft
        mListAnchors[TOP] = mTop
        mListAnchors[RIGHT] = mRight
        mListAnchors[BOTTOM] = mBottom
        for (i in mListAnchors.indices) {
            mListAnchors[i]?.solverVariable = system.createObjectVariable(mListAnchors[i])
        }
        position = if (barrierType >= 0 && barrierType < 4) {
            mListAnchors[barrierType] ?: return
        } else {
            return
        }
        if (USE_RESOLUTION) {
            if (!resolved) {
                allSolved()
            }
            if (resolved) {
                resolved = false
                if (barrierType == LEFT || barrierType == RIGHT) {
                    system.addEquality(mLeft?.solverVariable, mX)
                    system.addEquality(mRight?.solverVariable, mX)
                } else if (barrierType == TOP || barrierType == BOTTOM) {
                    system.addEquality(mTop.solverVariable, mY)
                    system.addEquality(mBottom.solverVariable, mY)
                }
                return
            }
        }

        // We have to handle the case where some of the elements referenced in the barrier are set as
        // match_constraint; we have to take it in account to set the strength of the barrier.
        var hasMatchConstraintWidgets = false
        for (i in 0 until mWidgetsCount) {
            val widget = mWidgets[i]
            if (!allowsGoneWidget && widget?.allowedInBarrier() != true) {
                continue
            }
            if ((barrierType == LEFT || barrierType == RIGHT)
                && widget?.horizontalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT
                && widget.mLeft?.target != null && widget.mRight?.target != null
            ) {
                hasMatchConstraintWidgets = true
                break
            } else if ((barrierType == TOP || barrierType == BOTTOM)
                && widget?.verticalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT
                && widget.mTop.target != null && widget.mBottom.target != null
            ) {
                hasMatchConstraintWidgets = true
                break
            }
        }
        val mHasHorizontalCenteredDependents = mLeft?.hasCenteredDependents() == true || mRight?.hasCenteredDependents() == true
        val mHasVerticalCenteredDependents = mTop.hasCenteredDependents() || mBottom.hasCenteredDependents()
        val applyEqualityOnReferences = !hasMatchConstraintWidgets && (barrierType == LEFT && mHasHorizontalCenteredDependents
                || barrierType == TOP && mHasVerticalCenteredDependents
                || barrierType == RIGHT && mHasHorizontalCenteredDependents
                || barrierType == BOTTOM && mHasVerticalCenteredDependents)
        var equalityOnReferencesStrength = SolverVariable.STRENGTH_EQUALITY
        if (!applyEqualityOnReferences) {
            equalityOnReferencesStrength = SolverVariable.STRENGTH_HIGHEST
        }
        for (i in 0 until mWidgetsCount) {
            val widget = mWidgets[i] ?: continue
            if (!allowsGoneWidget && !widget.allowedInBarrier()) {
                continue
            }
            val target = system.createObjectVariable(widget.mListAnchors[barrierType])
            widget.mListAnchors[barrierType]?.solverVariable = target
            var margin = 0
            if (widget.mListAnchors[barrierType]?.target != null
                && widget.mListAnchors[barrierType]?.target?.owner === this
            ) {
                margin += widget.mListAnchors[barrierType]?.mMargin ?: 0
            }
            if (barrierType == LEFT || barrierType == TOP) {
                system.addLowerBarrier(position.solverVariable, target, this.margin - margin, hasMatchConstraintWidgets)
            } else {
                system.addGreaterBarrier(position.solverVariable, target, this.margin + margin, hasMatchConstraintWidgets)
            }
            if (USE_RELAX_GONE) {
                if (widget.visibility != GONE || widget is Guideline || widget is Barrier) {
                    system.addEquality(position.solverVariable, target, this.margin + margin, equalityOnReferencesStrength)
                }
            } else {
                system.addEquality(position.solverVariable, target, this.margin + margin, equalityOnReferencesStrength)
            }
        }
        val barrierParentStrength = SolverVariable.STRENGTH_HIGHEST
        val barrierParentStrengthOpposite = SolverVariable.STRENGTH_NONE
        if (barrierType == LEFT) {
            system.addEquality(mRight?.solverVariable, mLeft?.solverVariable, 0, SolverVariable.STRENGTH_FIXED)
            system.addEquality(mLeft?.solverVariable, parent?.mRight?.solverVariable, 0, barrierParentStrength)
            system.addEquality(mLeft?.solverVariable, parent?.mLeft?.solverVariable, 0, barrierParentStrengthOpposite)
        } else if (barrierType == RIGHT) {
            system.addEquality(mLeft?.solverVariable, mRight?.solverVariable, 0, SolverVariable.STRENGTH_FIXED)
            system.addEquality(mLeft?.solverVariable, parent?.mLeft?.solverVariable, 0, barrierParentStrength)
            system.addEquality(mLeft?.solverVariable, parent?.mRight?.solverVariable, 0, barrierParentStrengthOpposite)
        } else if (barrierType == TOP) {
            system.addEquality(mBottom.solverVariable, mTop.solverVariable, 0, SolverVariable.STRENGTH_FIXED)
            system.addEquality(mTop.solverVariable, parent?.mBottom?.solverVariable, 0, barrierParentStrength)
            system.addEquality(mTop.solverVariable, parent?.mTop?.solverVariable, 0, barrierParentStrengthOpposite)
        } else if (barrierType == BOTTOM) {
            system.addEquality(mTop.solverVariable, mBottom.solverVariable, 0, SolverVariable.STRENGTH_FIXED)
            system.addEquality(mTop.solverVariable, parent?.mTop?.solverVariable, 0, barrierParentStrength)
            system.addEquality(mTop.solverVariable, parent?.mBottom?.solverVariable, 0, barrierParentStrengthOpposite)
        }
    }

    val orientation: Int
        get() {
            when (barrierType) {
                LEFT, RIGHT -> return HORIZONTAL
                TOP, BOTTOM -> return VERTICAL
            }
            return UNKNOWN
        }

    fun allSolved(): Boolean {
        if (!USE_RESOLUTION) {
            return false
        }
        var hasAllWidgetsResolved = true
        for (i in 0 until mWidgetsCount) {
            val widget = mWidgets[i] ?: continue
            if (!allowsGoneWidget && !widget.allowedInBarrier()) {
                continue
            }
            if ((barrierType == LEFT || barrierType == RIGHT) && !widget.isResolvedHorizontally) {
                hasAllWidgetsResolved = false
            } else if ((barrierType == TOP || barrierType == BOTTOM) && !widget.isResolvedVertically) {
                hasAllWidgetsResolved = false
            }
        }
        if (hasAllWidgetsResolved && mWidgetsCount > 0) {
            // we're done!
            var barrierPosition = 0
            var initialized = false
            for (i in 0 until mWidgetsCount) {
                val widget = mWidgets[i] ?: continue
                if (!allowsGoneWidget && !widget.allowedInBarrier()) {
                    continue
                }
                if (!initialized) {
                    if (barrierType == LEFT) {
                        barrierPosition = widget.getAnchor(ConstraintAnchor.Type.LEFT)!!.finalValue
                    } else if (barrierType == RIGHT) {
                        barrierPosition = widget.getAnchor(ConstraintAnchor.Type.RIGHT)!!.finalValue
                    } else if (barrierType == TOP) {
                        barrierPosition = widget.getAnchor(ConstraintAnchor.Type.TOP)!!.finalValue
                    } else if (barrierType == BOTTOM) {
                        barrierPosition = widget.getAnchor(ConstraintAnchor.Type.BOTTOM)!!.finalValue
                    }
                    initialized = true
                }
                if (barrierType == LEFT) {
                    barrierPosition = Math.min(barrierPosition, widget.getAnchor(ConstraintAnchor.Type.LEFT)!!.finalValue)
                } else if (barrierType == RIGHT) {
                    barrierPosition = Math.max(barrierPosition, widget.getAnchor(ConstraintAnchor.Type.RIGHT)!!.finalValue)
                } else if (barrierType == TOP) {
                    barrierPosition = Math.min(barrierPosition, widget.getAnchor(ConstraintAnchor.Type.TOP)!!.finalValue)
                } else if (barrierType == BOTTOM) {
                    barrierPosition = Math.max(barrierPosition, widget.getAnchor(ConstraintAnchor.Type.BOTTOM)!!.finalValue)
                }
            }
            barrierPosition += margin
            if (barrierType == LEFT || barrierType == RIGHT) {
                setFinalHorizontal(barrierPosition, barrierPosition)
            } else {
                setFinalVertical(barrierPosition, barrierPosition)
            }
            if (LinearSystem.FULL_DEBUG) {
                println("*** BARRIER $debugName SOLVED TO $barrierPosition ***")
            }
            resolved = true
            return true
        }
        return false
    }

    companion object {
        const val LEFT = 0
        const val RIGHT = 1
        const val TOP = 2
        const val BOTTOM = 3
        private const val USE_RESOLUTION = true
        private const val USE_RELAX_GONE = false
    }
}