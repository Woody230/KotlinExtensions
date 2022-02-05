/*
 * Copyright (C) 2019 The Android Open Source Project
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
package androidx.constraintlayout.core.widgets.analyzer

import androidx.constraintlayout.core.widgets.ConstraintWidget
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer
import androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour
import kotlin.jvm.JvmField
import kotlin.math.max
import kotlin.math.min

class ChainRun(widget: ConstraintWidget, orientation: Int) : WidgetRun(widget) {
    @JvmField
    var widgets = ArrayList<WidgetRun>()
    private var chainStyle = 0
    override fun toString(): String {
        val log = StringBuilder("ChainRun ")
        log.append(if (orientation == ConstraintWidget.HORIZONTAL) "horizontal : " else "vertical : ")
        for (run in widgets) {
            log.append("<")
            log.append(run)
            log.append("> ")
        }
        return log.toString()
    }

    public override fun supportsWrapComputation(): Boolean {
        val count = widgets.size
        for (i in 0 until count) {
            val run = widgets[i]
            if (!run.supportsWrapComputation()) {
                return false
            }
        }
        return true
    }

    override val wrapDimension: Long
        get() {
            val count = widgets.size
            var wrapDimension: Long = 0
            for (i in 0 until count) {
                val run = widgets[i]
                wrapDimension += run.start.margin.toLong()
                wrapDimension += run.wrapDimension
                wrapDimension += run.end.margin.toLong()
            }
            return wrapDimension
        }

    private fun build() {
        var current = widget
        var previous = current.getPreviousChainMember(orientation)
        while (previous != null) {
            current = previous
            previous = current.getPreviousChainMember(orientation)
        }
        widget = current // first element of the chain
        widgets.add(current.getRun(orientation)!!)
        var next = current.getNextChainMember(orientation)
        while (next != null) {
            current = next
            widgets.add(current.getRun(orientation)!!)
            next = current.getNextChainMember(orientation)
        }
        for (run in widgets) {
            if (orientation == ConstraintWidget.HORIZONTAL) {
                run.widget.horizontalChainRun = this
            } else if (orientation == ConstraintWidget.VERTICAL) {
                run.widget.verticalChainRun = this
            }
        }
        val isInRtl = orientation == ConstraintWidget.HORIZONTAL && (widget.parent as ConstraintWidgetContainer).isRtl
        if (isInRtl && widgets.size > 1) {
            widget = widgets[widgets.size - 1].widget
        }
        chainStyle = if (orientation == ConstraintWidget.HORIZONTAL) widget.horizontalChainStyle else widget.verticalChainStyle
    }

    public override fun clear() {
        runGroup = null
        for (run in widgets) {
            run.clear()
        }
    }

    public override fun reset() {
        start.resolved = false
        end.resolved = false
    }

    override fun update(node: Dependency) {
        if (!(start.resolved && end.resolved)) {
            return
        }
        val parent = widget.parent
        var isInRtl = false
        if (parent is ConstraintWidgetContainer) {
            isInRtl = parent.isRtl
        }
        val distance = end.value - start.value
        var size = 0
        var numMatchConstraints = 0
        var weights = 0f
        var numVisibleWidgets = 0
        val count = widgets.size
        // let's find the first visible widget...
        var firstVisibleWidget = -1
        for (i in 0 until count) {
            val run = widgets[i]
            if (run.widget.visibility == ConstraintWidget.GONE) {
                continue
            }
            firstVisibleWidget = i
            break
        }
        // now the last visible widget...
        var lastVisibleWidget = -1
        for (i in count - 1 downTo 0) {
            val run = widgets[i]
            if (run.widget.visibility == ConstraintWidget.GONE) {
                continue
            }
            lastVisibleWidget = i
            break
        }
        for (j in 0..1) {
            for (i in 0 until count) {
                val run = widgets[i]
                if (run.widget.visibility == ConstraintWidget.GONE) {
                    continue
                }
                numVisibleWidgets++
                if (i > 0 && i >= firstVisibleWidget) {
                    size += run.start.margin
                }
                var dimension = run.dimension.value
                var treatAsFixed = run.dimensionBehavior != DimensionBehaviour.MATCH_CONSTRAINT
                if (treatAsFixed) {
                    if (orientation == ConstraintWidget.HORIZONTAL && run.widget.horizontalRun?.dimension?.resolved != true) {
                        return
                    }
                    if (orientation == ConstraintWidget.VERTICAL && run.widget.verticalRun?.dimension?.resolved != true) {
                        return
                    }
                } else if (run.matchConstraintsType == ConstraintWidget.MATCH_CONSTRAINT_WRAP && j == 0) {
                    treatAsFixed = true
                    dimension = run.dimension.wrapValue
                    numMatchConstraints++
                } else if (run.dimension.resolved) {
                    treatAsFixed = true
                }
                if (!treatAsFixed) { // only for the first pass
                    numMatchConstraints++
                    val weight = run.widget.mWeight[orientation]
                    if (weight >= 0) {
                        weights += weight
                    }
                } else {
                    size += dimension
                }
                if (i < count - 1 && i < lastVisibleWidget) {
                    size += -run.end.margin
                }
            }
            if (size < distance || numMatchConstraints == 0) {
                break // we are good to go!
            }
            // otherwise, let's do another pass with using match_constraints
            numVisibleWidgets = 0
            numMatchConstraints = 0
            size = 0
            weights = 0f
        }
        var position = start.value
        if (isInRtl) {
            position = end.value
        }
        if (size > distance) {
            if (isInRtl) {
                position += (0.5f + (size - distance) / 2f).toInt()
            } else {
                position -= (0.5f + (size - distance) / 2f).toInt()
            }
        }
        var matchConstraintsDimension = 0
        if (numMatchConstraints > 0) {
            matchConstraintsDimension = (0.5f + (distance - size) / numMatchConstraints.toFloat()).toInt()
            var appliedLimits = 0
            for (i in 0 until count) {
                val run = widgets[i]
                if (run.widget.visibility == ConstraintWidget.GONE) {
                    continue
                }
                if (run.dimensionBehavior == DimensionBehaviour.MATCH_CONSTRAINT && !run.dimension.resolved) {
                    var dimension = matchConstraintsDimension
                    if (weights > 0) {
                        val weight = run.widget.mWeight[orientation]
                        dimension = (0.5f + weight * (distance - size) / weights).toInt()
                    }
                    var max: Int
                    var min: Int
                    var value = dimension
                    if (orientation == ConstraintWidget.HORIZONTAL) {
                        max = run.widget.mMatchConstraintMaxWidth
                        min = run.widget.mMatchConstraintMinWidth
                    } else {
                        max = run.widget.mMatchConstraintMaxHeight
                        min = run.widget.mMatchConstraintMinHeight
                    }
                    if (run.matchConstraintsType == ConstraintWidget.MATCH_CONSTRAINT_WRAP) {
                        value = min(value, run.dimension.wrapValue)
                    }
                    value = max(min, value)
                    if (max > 0) {
                        value = min(max, value)
                    }
                    if (value != dimension) {
                        appliedLimits++
                        dimension = value
                    }
                    run.dimension.resolve(dimension)
                }
            }
            if (appliedLimits > 0) {
                numMatchConstraints -= appliedLimits
                // we have to recompute the sizes
                size = 0
                for (i in 0 until count) {
                    val run = widgets[i]
                    if (run.widget.visibility == ConstraintWidget.GONE) {
                        continue
                    }
                    if (i > 0 && i >= firstVisibleWidget) {
                        size += run.start.margin
                    }
                    size += run.dimension.value
                    if (i < count - 1 && i < lastVisibleWidget) {
                        size += -run.end.margin
                    }
                }
            }
            if (chainStyle == ConstraintWidget.CHAIN_PACKED && appliedLimits == 0) {
                chainStyle = ConstraintWidget.CHAIN_SPREAD
            }
        }
        if (size > distance) {
            chainStyle = ConstraintWidget.CHAIN_PACKED
        }
        if (numVisibleWidgets > 0 && numMatchConstraints == 0 && firstVisibleWidget == lastVisibleWidget) {
            // only one widget of fixed size to display...
            chainStyle = ConstraintWidget.CHAIN_PACKED
        }
        if (chainStyle == ConstraintWidget.CHAIN_SPREAD_INSIDE) {
            var gap = 0
            if (numVisibleWidgets > 1) {
                gap = (distance - size) / (numVisibleWidgets - 1)
            } else if (numVisibleWidgets == 1) {
                gap = (distance - size) / 2
            }
            if (numMatchConstraints > 0) {
                gap = 0
            }
            for (i in 0 until count) {
                var index = i
                if (isInRtl) {
                    index = count - (i + 1)
                }
                val run = widgets[index]
                if (run.widget.visibility == ConstraintWidget.GONE) {
                    run.start.resolve(position)
                    run.end.resolve(position)
                    continue
                }
                if (i > 0) {
                    if (isInRtl) {
                        position -= gap
                    } else {
                        position += gap
                    }
                }
                if (i > 0 && i >= firstVisibleWidget) {
                    if (isInRtl) {
                        position -= run.start.margin
                    } else {
                        position += run.start.margin
                    }
                }
                if (isInRtl) {
                    run.end.resolve(position)
                } else {
                    run.start.resolve(position)
                }
                var dimension = run.dimension.value
                if (run.dimensionBehavior == DimensionBehaviour.MATCH_CONSTRAINT
                    && run.matchConstraintsType == ConstraintWidget.MATCH_CONSTRAINT_WRAP
                ) {
                    dimension = run.dimension.wrapValue
                }
                if (isInRtl) {
                    position -= dimension
                } else {
                    position += dimension
                }
                if (isInRtl) {
                    run.start.resolve(position)
                } else {
                    run.end.resolve(position)
                }
                run.isResolved = true
                if (i < count - 1 && i < lastVisibleWidget) {
                    if (isInRtl) {
                        position -= -run.end.margin
                    } else {
                        position += -run.end.margin
                    }
                }
            }
        } else if (chainStyle == ConstraintWidget.CHAIN_SPREAD) {
            var gap = (distance - size) / (numVisibleWidgets + 1)
            if (numMatchConstraints > 0) {
                gap = 0
            }
            for (i in 0 until count) {
                var index = i
                if (isInRtl) {
                    index = count - (i + 1)
                }
                val run = widgets[index]
                if (run.widget.visibility == ConstraintWidget.GONE) {
                    run.start.resolve(position)
                    run.end.resolve(position)
                    continue
                }
                if (isInRtl) {
                    position -= gap
                } else {
                    position += gap
                }
                if (i > 0 && i >= firstVisibleWidget) {
                    if (isInRtl) {
                        position -= run.start.margin
                    } else {
                        position += run.start.margin
                    }
                }
                if (isInRtl) {
                    run.end.resolve(position)
                } else {
                    run.start.resolve(position)
                }
                var dimension = run.dimension.value
                if (run.dimensionBehavior == DimensionBehaviour.MATCH_CONSTRAINT
                    && run.matchConstraintsType == ConstraintWidget.MATCH_CONSTRAINT_WRAP
                ) {
                    dimension = min(dimension, run.dimension.wrapValue)
                }
                if (isInRtl) {
                    position -= dimension
                } else {
                    position += dimension
                }
                if (isInRtl) {
                    run.start.resolve(position)
                } else {
                    run.end.resolve(position)
                }
                if (i < count - 1 && i < lastVisibleWidget) {
                    if (isInRtl) {
                        position -= -run.end.margin
                    } else {
                        position += -run.end.margin
                    }
                }
            }
        } else if (chainStyle == ConstraintWidget.CHAIN_PACKED) {
            var bias = if (orientation == ConstraintWidget.HORIZONTAL) widget.horizontalBiasPercent else widget.verticalBiasPercent
            if (isInRtl) {
                bias = 1 - bias
            }
            var gap = (0.5f + (distance - size) * bias).toInt()
            if (gap < 0 || numMatchConstraints > 0) {
                gap = 0
            }
            if (isInRtl) {
                position -= gap
            } else {
                position += gap
            }
            for (i in 0 until count) {
                var index = i
                if (isInRtl) {
                    index = count - (i + 1)
                }
                val run = widgets[index]
                if (run.widget.visibility == ConstraintWidget.GONE) {
                    run.start.resolve(position)
                    run.end.resolve(position)
                    continue
                }
                if (i > 0 && i >= firstVisibleWidget) {
                    if (isInRtl) {
                        position -= run.start.margin
                    } else {
                        position += run.start.margin
                    }
                }
                if (isInRtl) {
                    run.end.resolve(position)
                } else {
                    run.start.resolve(position)
                }
                var dimension = run.dimension.value
                if (run.dimensionBehavior == DimensionBehaviour.MATCH_CONSTRAINT
                    && run.matchConstraintsType == ConstraintWidget.MATCH_CONSTRAINT_WRAP
                ) {
                    dimension = run.dimension.wrapValue
                }
                if (isInRtl) {
                    position -= dimension
                } else {
                    position += dimension
                }
                if (isInRtl) {
                    run.start.resolve(position)
                } else {
                    run.end.resolve(position)
                }
                if (i < count - 1 && i < lastVisibleWidget) {
                    if (isInRtl) {
                        position -= -run.end.margin
                    } else {
                        position += -run.end.margin
                    }
                }
            }
        }
    }

    public override fun applyToWidget() {
        for (i in widgets.indices) {
            val run = widgets[i]
            run.applyToWidget()
        }
    }

    private val firstVisibleWidget: ConstraintWidget?
        private get() {
            for (i in widgets.indices) {
                val run = widgets[i]
                if (run.widget.visibility != ConstraintWidget.GONE) {
                    return run.widget
                }
            }
            return null
        }
    private val lastVisibleWidget: ConstraintWidget?
        private get() {
            for (i in widgets.indices.reversed()) {
                val run = widgets[i]
                if (run.widget.visibility != ConstraintWidget.GONE) {
                    return run.widget
                }
            }
            return null
        }

    public override fun apply() {
        for (run in widgets) {
            run.apply()
        }
        val count = widgets.size
        if (count < 1) {
            return
        }

        // get the first and last element of the chain
        val firstWidget = widgets[0].widget
        val lastWidget = widgets[count - 1].widget
        if (orientation == ConstraintWidget.HORIZONTAL) {
            val startAnchor = firstWidget.mLeft
            val endAnchor = lastWidget.mRight
            val startTarget = getTarget(startAnchor, ConstraintWidget.HORIZONTAL)
            var startMargin = startAnchor?.margin ?: 0
            val firstVisibleWidget = firstVisibleWidget
            if (firstVisibleWidget != null) {
                startMargin = firstVisibleWidget.mLeft?.margin ?: 0
            }
            startTarget?.let { addTarget(start, it, startMargin) }
            val endTarget = getTarget(endAnchor, ConstraintWidget.HORIZONTAL)
            var endMargin = endAnchor?.margin ?: 0
            val lastVisibleWidget = lastVisibleWidget
            if (lastVisibleWidget != null) {
                endMargin = lastVisibleWidget.mRight?.margin ?: 0
            }
            if (endTarget != null) {
                addTarget(end, endTarget, -endMargin)
            }
        } else {
            val startAnchor = firstWidget.mTop
            val endAnchor = lastWidget.mBottom
            val startTarget = getTarget(startAnchor, ConstraintWidget.VERTICAL)
            var startMargin = startAnchor.margin
            val firstVisibleWidget = firstVisibleWidget
            if (firstVisibleWidget != null) {
                startMargin = firstVisibleWidget.mTop.margin
            }
            startTarget?.let { addTarget(start, it, startMargin) }
            val endTarget = getTarget(endAnchor, ConstraintWidget.VERTICAL)
            var endMargin = endAnchor.margin
            val lastVisibleWidget = lastVisibleWidget
            if (lastVisibleWidget != null) {
                endMargin = lastVisibleWidget.mBottom.margin
            }
            if (endTarget != null) {
                addTarget(end, endTarget, -endMargin)
            }
        }
        start.updateDelegate = this
        end.updateDelegate = this
    }

    init {
        this.orientation = orientation
        build()
    }
}