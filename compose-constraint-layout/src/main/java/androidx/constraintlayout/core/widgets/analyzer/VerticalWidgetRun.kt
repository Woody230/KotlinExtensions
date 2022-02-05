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
import androidx.constraintlayout.core.widgets.ConstraintAnchor
import androidx.constraintlayout.core.widgets.Helper

class VerticalWidgetRun(widget: ConstraintWidget?) : WidgetRun(widget!!) {
    @JvmField
    var baseline = DependencyNode(this)
    var baselineDimension: DimensionDependency? = null
    override fun toString(): String {
        return "VerticalRun " + widget.debugName
    }

    override fun clear() {
        runGroup = null
        start.clear()
        end.clear()
        baseline.clear()
        dimension.clear()
        isResolved = false
    }

    override fun reset() {
        isResolved = false
        start.clear()
        start.resolved = false
        end.clear()
        end.resolved = false
        baseline.clear()
        baseline.resolved = false
        dimension.resolved = false
    }

    override fun supportsWrapComputation(): Boolean {
        return if (super.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            if (super.widget.mMatchConstraintDefaultHeight == ConstraintWidget.MATCH_CONSTRAINT_SPREAD) {
                true
            } else false
        } else true
    }

    override fun update(dependency: Dependency) {
        when (mRunType) {
            RunType.START -> {
                updateRunStart(dependency)
            }
            RunType.END -> {
                updateRunEnd(dependency)
            }
            RunType.CENTER -> {
                updateRunCenter(dependency, widget.mTop, widget.mBottom, ConstraintWidget.VERTICAL)
                return
            }
            else -> {}
        }
        if (true || dependency === dimension) {
            if (dimension.readyToSolve && !dimension.resolved) {
                if (dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    when (widget.mMatchConstraintDefaultHeight) {
                        ConstraintWidget.MATCH_CONSTRAINT_RATIO -> {
                            if (widget.horizontalRun?.dimension?.resolved == true) {
                                var size = 0
                                val ratioSide = widget.dimensionRatioSide
                                when (ratioSide) {
                                    ConstraintWidget.HORIZONTAL -> {
                                        size = (0.5f + (widget.horizontalRun?.dimension?.value ?: 0) * widget.dimensionRatio).toInt()
                                    }
                                    ConstraintWidget.VERTICAL -> {
                                        size = (0.5f + (widget.horizontalRun?.dimension?.value ?: 0) / widget.dimensionRatio).toInt()
                                    }
                                    ConstraintWidget.UNKNOWN -> {
                                        size = (0.5f + (widget.horizontalRun?.dimension?.value ?: 0) / widget.dimensionRatio).toInt()
                                    }
                                    else -> {}
                                }
                                dimension.resolve(size)
                            }
                        }
                        ConstraintWidget.MATCH_CONSTRAINT_PERCENT -> {
                            val parent = widget.parent
                            if (parent != null) {
                                if (parent.verticalRun?.dimension?.resolved == true) {
                                    val percent = widget.mMatchConstraintPercentHeight
                                    val targetDimensionValue = parent.verticalRun?.dimension?.value ?: 0
                                    val size = (0.5f + targetDimensionValue * percent).toInt()
                                    dimension.resolve(size)
                                }
                            }
                        }
                        else -> {}
                    }
                }
            }
        }
        if (!(start.readyToSolve && end.readyToSolve)) {
            return
        }
        if (start.resolved && end.resolved && dimension.resolved) {
            return
        }
        if (!dimension.resolved
            && dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widget.mMatchConstraintDefaultWidth == ConstraintWidget.MATCH_CONSTRAINT_SPREAD && !widget.isInVerticalChain
        ) {
            val startTarget = start.targets[0]
            val endTarget = end.targets[0]
            val startPos = startTarget.value + start.margin
            val endPos = endTarget.value + end.margin
            val distance = endPos - startPos
            start.resolve(startPos)
            end.resolve(endPos)
            dimension.resolve(distance)
            return
        }
        if (!dimension.resolved
            && dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && matchConstraintsType == ConstraintWidget.MATCH_CONSTRAINT_WRAP
        ) {
            if (start.targets.size > 0 && end.targets.size > 0) {
                val startTarget = start.targets[0]
                val endTarget = end.targets[0]
                val startPos = startTarget.value + start.margin
                val endPos = endTarget.value + end.margin
                val availableSpace = endPos - startPos
                if (availableSpace < dimension.wrapValue) {
                    dimension.resolve(availableSpace)
                } else {
                    dimension.resolve(dimension.wrapValue)
                }
            }
        }
        if (!dimension.resolved) {
            return
        }
        // ready to solve, centering.
        if (start.targets.size > 0 && end.targets.size > 0) {
            val startTarget = start.targets[0]
            val endTarget = end.targets[0]
            var startPos = startTarget.value + start.margin
            var endPos = endTarget.value + end.margin
            var bias = widget.verticalBiasPercent
            if (startTarget === endTarget) {
                startPos = startTarget.value
                endPos = endTarget.value
                // TODO: this might be a nice feature to support, but I guess for now let's stay
                // compatible with 1.1
                bias = 0.5f
            }
            val distance = endPos - startPos - dimension.value
            start.resolve((0.5f + startPos + distance * bias).toInt())
            end.resolve(start.value + dimension.value)
        }
    }

    override fun apply() {
        if (widget.measured) {
            dimension.resolve(widget.height)
        }
        if (!dimension.resolved) {
            super.dimensionBehavior = widget.verticalDimensionBehaviour
            if (widget.hasBaseline()) {
                baselineDimension = BaselineDimensionDependency(this)
            }
            if (super.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                if (dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                    val parent = widget.parent
                    if (parent != null && parent.verticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.FIXED) {
                        val resolvedDimension = parent.height - widget.mTop.margin - widget.mBottom.margin
                        addTarget(start, parent.verticalRun?.start, widget.mTop.margin)
                        addTarget(end, parent.verticalRun?.end, -widget.mBottom.margin)
                        dimension.resolve(resolvedDimension)
                        return
                    }
                }
                if (dimensionBehavior == ConstraintWidget.DimensionBehaviour.FIXED) {
                    dimension.resolve(widget.height)
                }
            }
        } else {
            if (dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                val parent = widget.parent
                if (parent != null && parent.verticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.FIXED) {
                    addTarget(start, parent.verticalRun?.start, widget.mTop.margin)
                    addTarget(end, parent.verticalRun?.end, -widget.mBottom.margin)
                    return
                }
            }
        }
        // three basic possibilities:
        // <-s-e->
        // <-s-e
        //   s-e->
        // and a variation if the dimension is not yet known:
        // <-s-d-e->
        // <-s<-d<-e
        //   s->d->e->
        if (dimension.resolved && widget.measured) {
            if (widget.mListAnchors[ConstraintWidget.ANCHOR_TOP]?.target != null && widget.mListAnchors[ConstraintWidget.ANCHOR_BOTTOM]?.target != null) { // <-s-e->
                if (widget.isInVerticalChain) {
                    start.margin = widget.mListAnchors[ConstraintWidget.ANCHOR_TOP]?.margin ?: 0
                    end.margin = -(widget.mListAnchors[ConstraintWidget.ANCHOR_BOTTOM]?.margin ?: 0)
                } else {
                    val startTarget = widget.mListAnchors[ConstraintWidget.ANCHOR_TOP]?.let { getTarget(it) }
                    if (startTarget != null) {
                        addTarget(start, startTarget, widget.mListAnchors[ConstraintWidget.ANCHOR_TOP]?.margin ?: 0)
                    }
                    val endTarget = widget.mListAnchors[ConstraintWidget.ANCHOR_BOTTOM]?.let { getTarget(it) }
                    if (endTarget != null) {
                        addTarget(end, endTarget, -(widget.mListAnchors[ConstraintWidget.ANCHOR_BOTTOM]?.margin ?: 0))
                    }
                    start.delegateToWidgetRun = true
                    end.delegateToWidgetRun = true
                }
                if (widget.hasBaseline()) {
                    addTarget(baseline, start, widget.baselineDistance)
                }
            } else if (widget.mListAnchors[ConstraintWidget.ANCHOR_TOP]?.target != null) { // <-s-e
                val target = widget.mListAnchors[ConstraintWidget.ANCHOR_TOP]?.let { getTarget(it) }
                if (target != null) {
                    addTarget(start, target, widget.mListAnchors[ConstraintWidget.ANCHOR_TOP]?.margin ?: 0)
                    addTarget(end, start, dimension.value)
                    if (widget.hasBaseline()) {
                        addTarget(baseline, start, widget.baselineDistance)
                    }
                }
            } else if (widget.mListAnchors[ConstraintWidget.ANCHOR_BOTTOM]?.target != null) {   //   s-e->
                val target = widget.mListAnchors[ConstraintWidget.ANCHOR_BOTTOM]?.let { getTarget(it) }
                if (target != null) {
                    addTarget(end, target, -(widget.mListAnchors[ConstraintWidget.ANCHOR_BOTTOM]?.margin ?: 0))
                    addTarget(start, end, -dimension.value)
                }
                if (widget.hasBaseline()) {
                    addTarget(baseline, start, widget.baselineDistance)
                }
            } else if (widget.mListAnchors[ConstraintWidget.ANCHOR_BASELINE]?.target != null) {
                val target = widget.mListAnchors[ConstraintWidget.ANCHOR_BASELINE]?.let { getTarget(it) }
                if (target != null) {
                    addTarget(baseline, target, 0)
                    addTarget(start, baseline, -widget.baselineDistance)
                    addTarget(end, start, dimension.value)
                }
            } else {
                // no connections, nothing to do.
                if (widget !is Helper && widget.parent != null && widget.getAnchor(ConstraintAnchor.Type.CENTER)?.target == null) {
                    val top = widget.parent!!.verticalRun?.start
                    addTarget(start, top, widget.y)
                    addTarget(end, start, dimension.value)
                    if (widget.hasBaseline()) {
                        addTarget(baseline, start, widget.baselineDistance)
                    }
                }
            }
        } else {
            if (!dimension.resolved && dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                when (widget.mMatchConstraintDefaultHeight) {
                    ConstraintWidget.MATCH_CONSTRAINT_RATIO -> {
                        if (!widget.isInVerticalChain) {
                            // need to look into both side
                            // When equal then let the HorizontalWidgetRun::update() deal with it.
                            if (widget.mMatchConstraintDefaultWidth != ConstraintWidget.MATCH_CONSTRAINT_RATIO) {
                                // we have a ratio, but we depend on the other side computation
                                widget.horizontalRun?.dimension?.let { targetDimension ->
                                    dimension.targets.add(targetDimension)
                                    targetDimension.dependencies.add(dimension)
                                    dimension.delegateToWidgetRun = true
                                    dimension.dependencies.add(start)
                                    dimension.dependencies.add(end)
                                }
                            }
                        }
                    }
                    ConstraintWidget.MATCH_CONSTRAINT_PERCENT -> {
                        // we need to look up the parent dimension
                        widget.parent?.verticalRun?.let { verticalRun ->
                            val targetDimension: DependencyNode = verticalRun.dimension
                            dimension.targets.add(targetDimension)
                            targetDimension.dependencies.add(dimension)
                            dimension.delegateToWidgetRun = true
                            dimension.dependencies.add(start)
                            dimension.dependencies.add(end)
                        }
                    }
                    ConstraintWidget.MATCH_CONSTRAINT_SPREAD -> {}
                    else -> {}
                }
            } else {
                dimension.addDependency(this)
            }
            if (widget.mListAnchors[ConstraintWidget.ANCHOR_TOP]?.target != null && widget.mListAnchors[ConstraintWidget.ANCHOR_BOTTOM]?.target != null) { // <-s-d-e->
                if (widget.isInVerticalChain) {
                    start.margin = widget.mListAnchors[ConstraintWidget.ANCHOR_TOP]?.margin ?: 0
                    end.margin = -(widget.mListAnchors[ConstraintWidget.ANCHOR_BOTTOM]?.margin ?: 0)
                } else {
                    val startTarget = widget.mListAnchors[ConstraintWidget.ANCHOR_TOP]?.let { getTarget(it) }
                    val endTarget = widget.mListAnchors[ConstraintWidget.ANCHOR_BOTTOM]?.let { getTarget(it) }

                    if (false) {
                        if (startTarget != null) {
                            addTarget(start, startTarget, widget.mListAnchors[ConstraintWidget.ANCHOR_TOP]?.margin ?: 0)
                        }
                        if (endTarget != null) {
                            addTarget(end, endTarget, -(widget.mListAnchors[ConstraintWidget.ANCHOR_BOTTOM]?.margin ?: 0))
                        }
                    } else {
                        startTarget?.addDependency(this)
                        endTarget?.addDependency(this)
                    }
                    mRunType = RunType.CENTER
                }
                if (widget.hasBaseline()) {
                    addTarget(baseline, start, 1, baselineDimension!!)
                }
            } else if (widget.mListAnchors[ConstraintWidget.ANCHOR_TOP]?.target != null) { // <-s<-d<-e
                val target = widget.mListAnchors[ConstraintWidget.ANCHOR_TOP]?.let { getTarget(it) }
                if (target != null) {
                    addTarget(start, target, widget.mListAnchors[ConstraintWidget.ANCHOR_TOP]?.margin ?: 0)
                    addTarget(end, start, 1, dimension)
                    if (widget.hasBaseline()) {
                        addTarget(baseline, start, 1, baselineDimension!!)
                    }
                    if (dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                        if (widget.dimensionRatio > 0) {
                            widget.horizontalRun?.let { horizontalRun ->
                                if (horizontalRun.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                                    horizontalRun.dimension.dependencies.add(dimension)
                                    dimension.targets.add(horizontalRun.dimension)
                                    dimension.updateDelegate = this
                                }
                            }
                        }
                    }
                }
            } else if (widget.mListAnchors[ConstraintWidget.ANCHOR_BOTTOM]?.target != null) {   //   s->d->e->
                val target = widget.mListAnchors[ConstraintWidget.ANCHOR_BOTTOM]?.let { getTarget(it) }
                if (target != null) {
                    addTarget(end, target, -(widget.mListAnchors[ConstraintWidget.ANCHOR_BOTTOM]?.margin ?: 0))
                    addTarget(start, end, -1, dimension)
                    if (widget.hasBaseline()) {
                        addTarget(baseline, start, 1, baselineDimension!!)
                    }
                }
            } else if (widget.mListAnchors[ConstraintWidget.ANCHOR_BASELINE]?.target != null) {
                val target = widget.mListAnchors[ConstraintWidget.ANCHOR_BASELINE]?.let { getTarget(it) }
                if (target != null) {
                    addTarget(baseline, target, 0)
                    addTarget(start, baseline, -1, baselineDimension!!)
                    addTarget(end, start, 1, dimension)
                }
            } else {
                // no connections, nothing to do.
                if (widget !is Helper && widget.parent != null) {
                    val top = widget.parent!!.verticalRun?.start
                    addTarget(start, top, widget.y)
                    addTarget(end, start, 1, dimension)
                    if (widget.hasBaseline()) {
                        addTarget(baseline, start, 1, baselineDimension!!)
                    }
                    if (dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                        if (widget.dimensionRatio > 0) {
                            widget.horizontalRun?.let { horizontalRun ->
                                if (horizontalRun.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                                    horizontalRun.dimension.dependencies.add(dimension)
                                    dimension.targets.add(horizontalRun.dimension)
                                    dimension.updateDelegate = this
                                }
                            }
                        }
                    }
                }
            }

            // if dimension has no dependency, mark it as ready to solve
            if (dimension.targets.size == 0) {
                dimension.readyToSolve = true
            }
        }
    }

    override fun applyToWidget() {
        if (start.resolved) {
            widget.y = start.value
        }
    }

    init {
        start.type = DependencyNode.Type.TOP
        end.type = DependencyNode.Type.BOTTOM
        baseline.type = DependencyNode.Type.BASELINE
        orientation = ConstraintWidget.VERTICAL
    }
}