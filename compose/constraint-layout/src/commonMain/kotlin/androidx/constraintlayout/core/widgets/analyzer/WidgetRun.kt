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

import androidx.constraintlayout.core.widgets.ConstraintAnchor
import androidx.constraintlayout.core.widgets.ConstraintWidget
import androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour
import kotlin.math.max
import kotlin.math.min

abstract class WidgetRun(var widget: ConstraintWidget) : Dependency {
    @JvmField
    var matchConstraintsType = 0
    @JvmField
    var runGroup: RunGroup? = null
    @JvmField
    var dimensionBehavior: DimensionBehaviour? = null
    @JvmField
    var dimension = DimensionDependency(this)
    @JvmField
    var orientation = ConstraintWidget.HORIZONTAL
    var isResolved = false
    @JvmField
    var start = DependencyNode(this)
    @JvmField
    var end = DependencyNode(this)
    @JvmField
    protected var mRunType = RunType.NONE
    abstract fun clear()
    abstract fun apply()
    abstract fun applyToWidget()
    abstract fun reset()
    abstract fun supportsWrapComputation(): Boolean
    val isCenterConnection: Boolean
        get() {
            var connections = 0
            var count = start.targets.size
            for (i in 0 until count) {
                val dependency = start.targets[i]
                if (dependency.run !== this) {
                    connections++
                }
            }
            count = end.targets.size
            for (i in 0 until count) {
                val dependency = end.targets[i]
                if (dependency.run !== this) {
                    connections++
                }
            }
            return connections >= 2
        }

    fun wrapSize(direction: Int): Long {
        if (dimension.resolved) {
            var size = dimension.value.toLong()
            if (isCenterConnection) { //start.targets.size() > 0 && end.targets.size() > 0) {
                size += (start.margin - end.margin).toLong()
            } else {
                if (direction == RunGroup.START) {
                    size += start.margin
                } else {
                    size -= end.margin
                }
            }
            return size
        }
        return 0
    }

    protected fun getTarget(anchor: ConstraintAnchor): DependencyNode? {
        if (anchor.target == null) {
            return null
        }
        var target: DependencyNode? = null
        val targetWidget = anchor.target?.owner
        val targetType = anchor.target?.type
        when (targetType) {
            ConstraintAnchor.Type.LEFT -> {
                val run = targetWidget?.horizontalRun
                target = run?.start
            }
            ConstraintAnchor.Type.RIGHT -> {
                val run = targetWidget?.horizontalRun
                target = run?.end
            }
            ConstraintAnchor.Type.TOP -> {
                val run = targetWidget?.verticalRun
                target = run?.start
            }
            ConstraintAnchor.Type.BASELINE -> {
                val run = targetWidget?.verticalRun
                target = run?.baseline
            }
            ConstraintAnchor.Type.BOTTOM -> {
                val run = targetWidget?.verticalRun
                target = run?.end
            }
            else -> {}
        }
        return target
    }

    protected fun updateRunCenter(dependency: Dependency?, startAnchor: ConstraintAnchor, endAnchor: ConstraintAnchor, orientation: Int) {
        val startTarget = getTarget(startAnchor) ?: return
        val endTarget = getTarget(endAnchor) ?: return
        if (!(startTarget.resolved && endTarget.resolved)) {
            return
        }
        var startPos = startTarget.value + startAnchor.margin
        var endPos = endTarget.value - endAnchor.margin
        val distance = endPos - startPos
        if (!dimension.resolved
            && dimensionBehavior == DimensionBehaviour.MATCH_CONSTRAINT
        ) {
            resolveDimension(orientation, distance)
        }
        if (!dimension.resolved) {
            return
        }
        if (dimension.value == distance) {
            start.resolve(startPos)
            end.resolve(endPos)
            return
        }

        // Otherwise, we have to center
        var bias = if (orientation == ConstraintWidget.HORIZONTAL) widget.horizontalBiasPercent else widget.verticalBiasPercent
        if (startTarget === endTarget) {
            startPos = startTarget.value
            endPos = endTarget.value
            // TODO: taking advantage of bias here would be a nice feature to support,
            // but for now let's stay compatible with 1.1
            bias = 0.5f
        }
        val availableDistance = endPos - startPos - dimension.value
        start.resolve((0.5f + startPos + availableDistance * bias).toInt())
        end.resolve(start.value + dimension.value)
    }

    private fun resolveDimension(orientation: Int, distance: Int) {
        when (matchConstraintsType) {
            ConstraintWidget.MATCH_CONSTRAINT_SPREAD -> {
                dimension.resolve(getLimitedDimension(distance, orientation))
            }
            ConstraintWidget.MATCH_CONSTRAINT_PERCENT -> {
                val parent = widget.parent
                if (parent != null) {
                    val run = if (orientation == ConstraintWidget.HORIZONTAL) parent.horizontalRun else parent.verticalRun
                    if (run?.dimension?.resolved == true) {
                        val percent = if (orientation == ConstraintWidget.HORIZONTAL) widget.mMatchConstraintPercentWidth else widget.mMatchConstraintPercentHeight
                        val targetDimensionValue = run.dimension.value
                        val size = (0.5f + targetDimensionValue * percent).toInt()
                        dimension.resolve(getLimitedDimension(size, orientation))
                    }
                }
            }
            ConstraintWidget.MATCH_CONSTRAINT_WRAP -> {
                val wrapValue = getLimitedDimension(dimension.wrapValue, orientation)
                dimension.resolve(min(wrapValue, distance))
            }
            ConstraintWidget.MATCH_CONSTRAINT_RATIO -> {
                if (widget.horizontalRun?.dimensionBehavior == DimensionBehaviour.MATCH_CONSTRAINT && widget.horizontalRun?.matchConstraintsType == ConstraintWidget.MATCH_CONSTRAINT_RATIO && widget.verticalRun?.dimensionBehavior == DimensionBehaviour.MATCH_CONSTRAINT && widget.verticalRun?.matchConstraintsType == ConstraintWidget.MATCH_CONSTRAINT_RATIO) {
                    // pof
                } else {
                    val run = if (orientation == ConstraintWidget.HORIZONTAL) widget.verticalRun else widget.horizontalRun
                    if (run?.dimension?.resolved == true) {
                        val ratio = widget.dimensionRatio
                        val value: Int
                        value = if (orientation == ConstraintWidget.VERTICAL) {
                            (0.5f + run.dimension.value / ratio).toInt()
                        } else {
                            (0.5f + ratio * run.dimension.value).toInt()
                        }
                        dimension.resolve(value)
                    }
                }
            }
            else -> {}
        }
    }

    protected fun updateRunStart(dependency: Dependency?) {}
    protected fun updateRunEnd(dependency: Dependency?) {}
    override fun update(dependency: Dependency) {}
    protected fun getLimitedDimension(dimension: Int, orientation: Int): Int {
        var dimension = dimension
        if (orientation == ConstraintWidget.HORIZONTAL) {
            val max = widget.mMatchConstraintMaxWidth
            val min = widget.mMatchConstraintMinWidth
            var value = max(min, dimension)
            if (max > 0) {
                value = min(max, dimension)
            }
            if (value != dimension) {
                dimension = value
            }
        } else {
            val max = widget.mMatchConstraintMaxHeight
            val min = widget.mMatchConstraintMinHeight
            var value = max(min, dimension)
            if (max > 0) {
                value = min(max, dimension)
            }
            if (value != dimension) {
                dimension = value
            }
        }
        return dimension
    }

    protected fun getTarget(anchor: ConstraintAnchor?, orientation: Int): DependencyNode? {
        if (anchor?.target == null) {
            return null
        }
        var target: DependencyNode? = null
        val targetWidget = anchor.target?.owner
        val run = if (orientation == ConstraintWidget.HORIZONTAL) targetWidget?.horizontalRun else targetWidget?.verticalRun
        val targetType = anchor.target?.type
        when (targetType) {
            ConstraintAnchor.Type.TOP, ConstraintAnchor.Type.LEFT -> {
                target = run?.start
            }
            ConstraintAnchor.Type.BOTTOM, ConstraintAnchor.Type.RIGHT -> {
                target = run?.end
            }
            else -> {}
        }
        return target
    }

    protected fun addTarget(node: DependencyNode, target: DependencyNode?, margin: Int) {
        target?.let {
            node.targets.add(target)
            node.margin = margin
            target.dependencies.add(node)
        }
    }

    protected fun addTarget(node: DependencyNode, target: DependencyNode, marginFactor: Int, dimensionDependency: DimensionDependency) {
        node.targets.add(target)
        node.targets.add(dimension)
        node.marginFactor = marginFactor
        node.marginDependency = dimensionDependency
        target.dependencies.add(node)
        dimensionDependency.dependencies.add(node)
    }

    open val wrapDimension: Long
        get() = if (dimension.resolved) {
            dimension.value.toLong()
        } else 0

    enum class RunType {
        NONE, START, END, CENTER
    }
}