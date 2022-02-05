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

class HorizontalWidgetRun(widget: ConstraintWidget?) : WidgetRun(widget!!) {
    override fun toString(): String {
        return "HorizontalRun " + widget.debugName
    }

    override fun clear() {
        runGroup = null
        start.clear()
        end.clear()
        dimension.clear()
        isResolved = false
    }

    override fun reset() {
        isResolved = false
        start.clear()
        start.resolved = false
        end.clear()
        end.resolved = false
        dimension.resolved = false
    }

    override fun supportsWrapComputation(): Boolean {
        return if (super.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            if (super.widget.mMatchConstraintDefaultWidth == ConstraintWidget.MATCH_CONSTRAINT_SPREAD) {
                true
            } else false
        } else true
    }

    override fun apply() {
        if (widget.measured) {
            dimension.resolve(widget.width)
        }
        if (!dimension.resolved) {
            super.dimensionBehavior = widget.horizontalDimensionBehaviour
            if (super.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                if (dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                    val parent = widget.parent
                    if (parent != null
                        && (parent.horizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.FIXED
                                || parent.horizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_PARENT)
                    ) {
                        val resolvedDimension = parent.width - (widget.mLeft?.margin ?: 0) - (widget.mRight?.margin ?: 0)
                        addTarget(start, parent.horizontalRun?.start, widget.mLeft?.margin ?: 0)
                        addTarget(end, parent.horizontalRun?.end, -(widget.mRight?.margin ?: 0))
                        dimension.resolve(resolvedDimension)
                        return
                    }
                }
                if (dimensionBehavior == ConstraintWidget.DimensionBehaviour.FIXED) {
                    dimension.resolve(widget.width)
                }
            }
        } else {
            if (dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                val parent = widget.parent
                if (parent != null
                    && (parent.horizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.FIXED
                            || parent.horizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_PARENT)
                ) {
                    addTarget(start, parent.horizontalRun?.start, widget.mLeft?.margin ?: 0)
                    addTarget(end, parent.horizontalRun?.end, -(widget.mRight?.margin ?: 0))
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
            if (widget.mListAnchors[ConstraintWidget.ANCHOR_LEFT]?.target != null && widget.mListAnchors[ConstraintWidget.ANCHOR_RIGHT]?.target != null) { // <-s-e->
                if (widget.isInHorizontalChain) {
                    start.margin = widget.mListAnchors[ConstraintWidget.ANCHOR_LEFT]?.margin ?: 0
                    end.margin = -(widget.mListAnchors[ConstraintWidget.ANCHOR_RIGHT]?.margin ?: 0)
                } else {
                    val startTarget = widget.mListAnchors[ConstraintWidget.ANCHOR_LEFT]?.let { getTarget(it) }
                    if (startTarget != null) {
                        addTarget(start, startTarget, widget.mListAnchors[ConstraintWidget.ANCHOR_LEFT]?.margin ?: 0)
                    }
                    val endTarget = widget.mListAnchors[ConstraintWidget.ANCHOR_RIGHT]?.let { getTarget(it) }
                    if (endTarget != null) {
                        addTarget(end, endTarget, -(widget.mListAnchors[ConstraintWidget.ANCHOR_RIGHT]?.margin ?: 0))
                    }
                    start.delegateToWidgetRun = true
                    end.delegateToWidgetRun = true
                }
            } else if (widget.mListAnchors[ConstraintWidget.ANCHOR_LEFT]?.target != null) { // <-s-e
                val target = widget.mListAnchors[ConstraintWidget.ANCHOR_LEFT]?.let { getTarget(it) }
                if (target != null) {
                    addTarget(start, target, widget.mListAnchors[ConstraintWidget.ANCHOR_LEFT]?.margin ?: 0)
                    addTarget(end, start, dimension.value)
                }
            } else if (widget.mListAnchors[ConstraintWidget.ANCHOR_RIGHT]?.target != null) {   //   s-e->
                val target = widget.mListAnchors[ConstraintWidget.ANCHOR_RIGHT]?.let { getTarget(it) }
                if (target != null) {
                    addTarget(end, target, -(widget.mListAnchors[ConstraintWidget.ANCHOR_RIGHT]?.margin ?: 0))
                    addTarget(start, end, -dimension.value)
                }
            } else {
                // no connections, nothing to do.
                if (widget !is Helper && widget.parent != null && widget.getAnchor(ConstraintAnchor.Type.CENTER)?.target == null) {
                    widget.parent?.horizontalRun?.start?.let { left ->
                        addTarget(start, left, widget.x)
                        addTarget(end, start, dimension.value)
                    }
                }
            }
        } else {
            if (dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                when (widget.mMatchConstraintDefaultWidth) {
                    ConstraintWidget.MATCH_CONSTRAINT_RATIO -> {
                        widget.verticalRun?.let { verticalRun ->
                            if (widget.mMatchConstraintDefaultHeight == ConstraintWidget.MATCH_CONSTRAINT_RATIO) {
                                // need to look into both side
                                start.updateDelegate = this
                                end.updateDelegate = this
                                verticalRun.start.updateDelegate = this
                                verticalRun.end.updateDelegate = this
                                dimension.updateDelegate = this
                                if (widget.isInVerticalChain) {
                                    dimension.targets.add(verticalRun.dimension)
                                    verticalRun.dimension.dependencies.add(dimension)
                                    verticalRun.dimension.updateDelegate = this
                                    dimension.targets.add(verticalRun.start)
                                    dimension.targets.add(verticalRun.end)
                                    verticalRun.start.dependencies.add(dimension)
                                    verticalRun.end.dependencies.add(dimension)
                                } else if (widget.isInHorizontalChain) {
                                    verticalRun.dimension.targets.add(dimension)
                                    dimension.dependencies.add(verticalRun.dimension)
                                } else {
                                    verticalRun.dimension.targets.add(dimension)
                                }
                            }
                            // we have a ratio, but we depend on the other side computation
                            val targetDimension: DependencyNode = verticalRun.dimension
                            dimension.targets.add(targetDimension)
                            targetDimension.dependencies.add(dimension)
                            widget.verticalRun?.start?.dependencies?.add(dimension)
                            widget.verticalRun?.end?.dependencies?.add(dimension)
                            dimension.delegateToWidgetRun = true
                            dimension.dependencies.add(start)
                            dimension.dependencies.add(end)
                            start.targets.add(dimension)
                            end.targets.add(dimension)
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
            }
            if (widget.mListAnchors[ConstraintWidget.ANCHOR_LEFT]?.target != null && widget.mListAnchors[ConstraintWidget.ANCHOR_RIGHT]?.target != null) { // <-s-d-e->
                if (widget.isInHorizontalChain) {
                    start.margin = widget.mListAnchors[ConstraintWidget.ANCHOR_LEFT]?.margin ?: 0
                    end.margin = -(widget.mListAnchors[ConstraintWidget.ANCHOR_RIGHT]?.margin ?: 0)
                } else {
                    val startTarget = widget.mListAnchors[ConstraintWidget.ANCHOR_LEFT]?.let { getTarget(it) }
                    val endTarget = widget.mListAnchors[ConstraintWidget.ANCHOR_RIGHT]?.let { getTarget(it) }

                    if (false) {
                        if (startTarget != null) {
                            addTarget(start, startTarget, widget.mListAnchors[ConstraintWidget.ANCHOR_LEFT]?.margin ?: 0)
                        }
                        if (endTarget != null) {
                            addTarget(end, endTarget, -(widget.mListAnchors[ConstraintWidget.ANCHOR_RIGHT]?.margin ?: 0))
                        }
                    } else {
                        startTarget?.addDependency(this)
                        endTarget?.addDependency(this)
                    }
                    mRunType = RunType.CENTER
                }
            } else if (widget.mListAnchors[ConstraintWidget.ANCHOR_LEFT]?.target != null) { // <-s<-d<-e
                val target = widget.mListAnchors[ConstraintWidget.ANCHOR_LEFT]?.let { getTarget(it) }
                if (target != null) {
                    addTarget(start, target, widget.mListAnchors[ConstraintWidget.ANCHOR_LEFT]?.margin ?: 0)
                    addTarget(end, start, 1, dimension)
                }
            } else if (widget.mListAnchors[ConstraintWidget.ANCHOR_RIGHT]?.target != null) {   //   s->d->e->
                val target = widget.mListAnchors[ConstraintWidget.ANCHOR_RIGHT]?.let { getTarget(it) }
                if (target != null) {
                    addTarget(end, target, -(widget.mListAnchors[ConstraintWidget.ANCHOR_RIGHT]?.margin ?: 0))
                    addTarget(start, end, -1, dimension)
                }
            } else {
                // no connections, nothing to do.
                if (widget !is Helper && widget.parent != null) {
                    widget.parent?.horizontalRun?.start?.let { left ->
                        addTarget(start, left, widget.x)
                        addTarget(end, start, 1, dimension)
                    }
                }
            }
        }
    }

    private fun computeInsetRatio(dimensions: IntArray, x1: Int, x2: Int, y1: Int, y2: Int, ratio: Float, side: Int) {
        val dx = x2 - x1
        val dy = y2 - y1
        when (side) {
            ConstraintWidget.UNKNOWN -> {
                val candidateX1 = (0.5f + dy * ratio).toInt()
                val candidateY2 = (0.5f + dx / ratio).toInt()
                if (candidateX1 <= dx && dy <= dy) {
                    dimensions[ConstraintWidget.HORIZONTAL] = candidateX1
                    dimensions[ConstraintWidget.VERTICAL] = dy
                } else if (dx <= dx && candidateY2 <= dy) {
                    dimensions[ConstraintWidget.HORIZONTAL] = dx
                    dimensions[ConstraintWidget.VERTICAL] = candidateY2
                }
            }
            ConstraintWidget.HORIZONTAL -> {
                val horizontalSide = (0.5f + dy * ratio).toInt()
                dimensions[ConstraintWidget.HORIZONTAL] = horizontalSide
                dimensions[ConstraintWidget.VERTICAL] = dy
            }
            ConstraintWidget.VERTICAL -> {
                val verticalSide = (0.5f + dx * ratio).toInt()
                dimensions[ConstraintWidget.HORIZONTAL] = dx
                dimensions[ConstraintWidget.VERTICAL] = verticalSide
            }
            else -> {}
        }
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
                widget.mLeft?.let { left ->
                    widget.mRight?.let { right ->
                        updateRunCenter(dependency, left, right, ConstraintWidget.HORIZONTAL)
                    }
                }
                return
            }
            else -> {}
        }
        if (!dimension.resolved) {
            if (dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                when (widget.mMatchConstraintDefaultWidth) {
                    ConstraintWidget.MATCH_CONSTRAINT_RATIO -> {
                        if (widget.mMatchConstraintDefaultHeight == ConstraintWidget.MATCH_CONSTRAINT_SPREAD
                            || widget.mMatchConstraintDefaultHeight == ConstraintWidget.MATCH_CONSTRAINT_RATIO
                        ) {
                            val secondStart = widget.verticalRun?.start
                            val secondEnd = widget.verticalRun?.end
                            val s1 = widget.mLeft?.target != null
                            val s2 = widget.mTop.target != null
                            val e1 = widget.mRight?.target != null
                            val e2 = widget.mBottom.target != null
                            val definedSide = widget.dimensionRatioSide
                            if (s1 && s2 && e1 && e2) {
                                val ratio = widget.dimensionRatio
                                if (secondStart?.resolved == true && secondEnd?.resolved == true) {
                                    if (!(start.readyToSolve && end.readyToSolve)) {
                                        return
                                    }
                                    val x1 = start.targets[0].value + start.margin
                                    val x2 = end.targets[0].value - end.margin
                                    val y1 = secondStart.value + secondStart.margin
                                    val y2 = secondEnd.value - secondEnd.margin
                                    computeInsetRatio(tempDimensions, x1, x2, y1, y2, ratio, definedSide)
                                    dimension.resolve(tempDimensions[ConstraintWidget.HORIZONTAL])
                                    widget.verticalRun?.dimension?.resolve(tempDimensions[ConstraintWidget.VERTICAL])
                                    return
                                }
                                if (start.resolved && end.resolved) {
                                    if (!(secondStart?.readyToSolve == true && secondEnd?.readyToSolve == true)) {
                                        return
                                    }
                                    val x1 = start.value + start.margin
                                    val x2 = end.value - end.margin
                                    val y1 = secondStart.targets[0].value + secondStart.margin
                                    val y2 = secondEnd.targets[0].value - secondEnd.margin
                                    computeInsetRatio(tempDimensions, x1, x2, y1, y2, ratio, definedSide)
                                    dimension.resolve(tempDimensions[ConstraintWidget.HORIZONTAL])
                                    widget.verticalRun?.dimension?.resolve(tempDimensions[ConstraintWidget.VERTICAL])
                                }
                                if (!(start.readyToSolve && end.readyToSolve
                                            && secondStart?.readyToSolve == true
                                            && secondEnd?.readyToSolve == true)
                                ) {
                                    return
                                }
                                val x1 = start.targets[0].value + start.margin
                                val x2 = end.targets[0].value - end.margin
                                val y1 = secondStart.targets[0].value + secondStart.margin
                                val y2 = secondEnd.targets[0].value - secondEnd.margin
                                computeInsetRatio(tempDimensions, x1, x2, y1, y2, ratio, definedSide)
                                dimension.resolve(tempDimensions[ConstraintWidget.HORIZONTAL])
                                widget.verticalRun?.dimension?.resolve(tempDimensions[ConstraintWidget.VERTICAL])
                            } else if (s1 && e1) {
                                if (!(start.readyToSolve && end.readyToSolve)) {
                                    return
                                }
                                val ratio = widget.dimensionRatio
                                val x1 = start.targets[0].value + start.margin
                                val x2 = end.targets[0].value - end.margin
                                when (definedSide) {
                                    ConstraintWidget.UNKNOWN, ConstraintWidget.HORIZONTAL -> {
                                        val dx = x2 - x1
                                        var ldx = getLimitedDimension(dx, ConstraintWidget.HORIZONTAL)
                                        val dy = (0.5f + ldx * ratio).toInt()
                                        val ldy = getLimitedDimension(dy, ConstraintWidget.VERTICAL)
                                        if (dy != ldy) {
                                            ldx = (0.5f + ldy / ratio).toInt()
                                        }
                                        dimension.resolve(ldx)
                                        widget.verticalRun?.dimension?.resolve(ldy)
                                    }
                                    ConstraintWidget.VERTICAL -> {
                                        val dx = x2 - x1
                                        var ldx = getLimitedDimension(dx, ConstraintWidget.HORIZONTAL)
                                        val dy = (0.5f + ldx / ratio).toInt()
                                        val ldy = getLimitedDimension(dy, ConstraintWidget.VERTICAL)
                                        if (dy != ldy) {
                                            ldx = (0.5f + ldy * ratio).toInt()
                                        }
                                        dimension.resolve(ldx)
                                        widget.verticalRun?.dimension?.resolve(ldy)
                                    }
                                    else -> {}
                                }
                            } else if (s2 && e2) {
                                if (!(secondStart?.readyToSolve == true && secondEnd?.readyToSolve == true)) {
                                    return
                                }
                                val ratio = widget.dimensionRatio
                                val y1 = secondStart.targets[0].value + secondStart.margin
                                val y2 = secondEnd.targets[0].value - secondEnd.margin
                                when (definedSide) {
                                    ConstraintWidget.UNKNOWN, ConstraintWidget.VERTICAL -> {
                                        val dy = y2 - y1
                                        var ldy = getLimitedDimension(dy, ConstraintWidget.VERTICAL)
                                        val dx = (0.5f + ldy / ratio).toInt()
                                        val ldx = getLimitedDimension(dx, ConstraintWidget.HORIZONTAL)
                                        if (dx != ldx) {
                                            ldy = (0.5f + ldx * ratio).toInt()
                                        }
                                        dimension.resolve(ldx)
                                        widget.verticalRun?.dimension?.resolve(ldy)
                                    }
                                    ConstraintWidget.HORIZONTAL -> {
                                        val dy = y2 - y1
                                        var ldy = getLimitedDimension(dy, ConstraintWidget.VERTICAL)
                                        val dx = (0.5f + ldy * ratio).toInt()
                                        val ldx = getLimitedDimension(dx, ConstraintWidget.HORIZONTAL)
                                        if (dx != ldx) {
                                            ldy = (0.5f + ldx / ratio).toInt()
                                        }
                                        dimension.resolve(ldx)
                                        widget.verticalRun?.dimension?.resolve(ldy)
                                    }
                                    else -> {}
                                }
                            }
                        } else {
                            var size = 0
                            val ratioSide = widget.dimensionRatioSide
                            when (ratioSide) {
                                ConstraintWidget.HORIZONTAL -> {
                                    size = (0.5f + (widget.verticalRun?.dimension?.value ?: 0) / widget.dimensionRatio).toInt()
                                }
                                ConstraintWidget.VERTICAL -> {
                                    size = (0.5f + (widget.verticalRun?.dimension?.value ?: 0) * widget.dimensionRatio).toInt()
                                }
                                ConstraintWidget.UNKNOWN -> {
                                    size = (0.5f + (widget.verticalRun?.dimension?.value ?: 0) * widget.dimensionRatio).toInt()
                                }
                                else -> {}
                            }
                            dimension.resolve(size)
                        }
                    }
                    ConstraintWidget.MATCH_CONSTRAINT_PERCENT -> {
                        val parent = widget.parent
                        if (parent != null) {
                            if (parent.horizontalRun?.dimension?.resolved == true) {
                                val percent = widget.mMatchConstraintPercentWidth
                                val targetDimensionValue = parent.horizontalRun?.dimension?.value ?: 0
                                val size = (0.5f + targetDimensionValue * percent).toInt()
                                dimension.resolve(size)
                            }
                        }
                    }
                    else -> {}
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
            && dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widget.mMatchConstraintDefaultWidth == ConstraintWidget.MATCH_CONSTRAINT_SPREAD && !widget.isInHorizontalChain
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
                var value = Math.min(availableSpace, dimension.wrapValue)
                val max = widget.mMatchConstraintMaxWidth
                val min = widget.mMatchConstraintMinWidth
                value = Math.max(min, value)
                if (max > 0) {
                    value = Math.min(max, value)
                }
                dimension.resolve(value)
            }
        }
        if (!dimension.resolved) {
            return
        }
        // ready to solve, centering.
        val startTarget = start.targets[0]
        val endTarget = end.targets[0]
        var startPos = startTarget.value + start.margin
        var endPos = endTarget.value + end.margin
        var bias = widget.horizontalBiasPercent
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

    override fun applyToWidget() {
        if (start.resolved) {
            widget.x = start.value
        }
    }

    companion object {
        private val tempDimensions = IntArray(2)
    }

    init {
        start.type = DependencyNode.Type.LEFT
        end.type = DependencyNode.Type.RIGHT
        orientation = ConstraintWidget.HORIZONTAL
    }
}