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

import androidx.constraintlayout.core.widgets.*
import androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour
import kotlin.math.max

class DependencyGraph(private val container: ConstraintWidgetContainer) {
    private var mNeedBuildGraph = true
    private var mNeedRedoMeasures = true
    private val mContainer: ConstraintWidgetContainer
    private val mRuns = ArrayList<WidgetRun>()

    // TODO: Unused, should we delete?
    private val runGroups = ArrayList<RunGroup>()
    private var mMeasurer: BasicMeasure.Measurer? = null
    private val mMeasure = BasicMeasure.Measure()
    fun setMeasurer(measurer: BasicMeasure.Measurer?) {
        mMeasurer = measurer
    }

    private fun computeWrap(container: ConstraintWidgetContainer, orientation: Int): Int {
        val count = mGroups.size
        var wrapSize: Long = 0
        for (i in 0 until count) {
            val run = mGroups[i]
            val size = run.computeWrapSize(container, orientation)
            wrapSize = max(wrapSize, size)
        }
        return wrapSize.toInt()
    }

    /**
     * Find and mark terminal widgets (trailing widgets) -- they are the only
     * ones we need to care for wrap_content checks
     * @param horizontalBehavior
     * @param verticalBehavior
     */
    fun defineTerminalWidgets(horizontalBehavior: DimensionBehaviour, verticalBehavior: DimensionBehaviour) {
        if (mNeedBuildGraph) {
            buildGraph()
            if (USE_GROUPS) {
                var hasBarrier = false
                for (widget in container.children) {
                    widget.isTerminalWidget[ConstraintWidget.HORIZONTAL] = true
                    widget.isTerminalWidget[ConstraintWidget.VERTICAL] = true
                    if (widget is Barrier) {
                        hasBarrier = true
                    }
                }
                if (!hasBarrier) {
                    for (group in mGroups) {
                        group.defineTerminalWidgets(horizontalBehavior == DimensionBehaviour.WRAP_CONTENT, verticalBehavior == DimensionBehaviour.WRAP_CONTENT)
                    }
                }
            }
        }
    }

    /**
     * Try to measure the layout by solving the graph of constraints directly
     *
     * @param optimizeWrap use the wrap_content optimizer
     * @return true if all widgets have been resolved
     */
    fun directMeasure(optimizeWrap: Boolean): Boolean {
        var optimizeWrap = optimizeWrap
        optimizeWrap = optimizeWrap and USE_GROUPS
        if (mNeedBuildGraph || mNeedRedoMeasures) {
            for (widget in container.children) {
                widget.ensureWidgetRuns()
                widget.measured = false
                widget.horizontalRun?.reset()
                widget.verticalRun?.reset()
            }
            container.ensureWidgetRuns()
            container.measured = false
            container.horizontalRun?.reset()
            container.verticalRun?.reset()
            mNeedRedoMeasures = false
        }
        val avoid = basicMeasureWidgets(mContainer)
        if (avoid) {
            return false
        }
        container.x = 0
        container.y = 0
        val originalHorizontalDimension = container.getDimensionBehaviour(ConstraintWidget.HORIZONTAL)
        val originalVerticalDimension = container.getDimensionBehaviour(ConstraintWidget.VERTICAL)
        if (mNeedBuildGraph) {
            buildGraph()
        }
        val x1 = container.x
        val y1 = container.y
        container.horizontalRun?.start?.resolve(x1)
        container.verticalRun?.start?.resolve(y1)

        // Let's do the easy steps first -- anything that can be immediately measured
        // Whatever is left for the dimension will be match_constraints.
        measureWidgets()

        // If we have to support wrap, let's see if we can compute it directly
        if (originalHorizontalDimension == DimensionBehaviour.WRAP_CONTENT || originalVerticalDimension == DimensionBehaviour.WRAP_CONTENT) {
            if (optimizeWrap) {
                for (run in mRuns) {
                    if (!run.supportsWrapComputation()) {
                        optimizeWrap = false
                        break
                    }
                }
            }
            if (optimizeWrap && originalHorizontalDimension == DimensionBehaviour.WRAP_CONTENT) {
                container.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
                container.width = computeWrap(container, ConstraintWidget.HORIZONTAL)
                container.horizontalRun?.dimension?.resolve(container.width)
            }
            if (optimizeWrap && originalVerticalDimension == DimensionBehaviour.WRAP_CONTENT) {
                container.verticalDimensionBehaviour = DimensionBehaviour.FIXED
                container.height = computeWrap(container, ConstraintWidget.VERTICAL)
                container.verticalRun?.dimension?.resolve(container.height)
            }
        }
        var checkRoot = false

        // Now, depending on our own dimension behavior, we may want to solve
        // one dimension before the other
        if (container.mListDimensionBehaviors[ConstraintWidget.HORIZONTAL] == DimensionBehaviour.FIXED
            || container.mListDimensionBehaviors[ConstraintWidget.HORIZONTAL] == DimensionBehaviour.MATCH_PARENT
        ) {

            // solve horizontal dimension
            val x2 = x1 + container.width
            container.horizontalRun?.end?.resolve(x2)
            container.horizontalRun?.dimension?.resolve(x2 - x1)
            measureWidgets()
            if (container.mListDimensionBehaviors[ConstraintWidget.VERTICAL] == DimensionBehaviour.FIXED
                || container.mListDimensionBehaviors[ConstraintWidget.VERTICAL] == DimensionBehaviour.MATCH_PARENT
            ) {
                val y2 = y1 + container.height
                container.verticalRun?.end?.resolve(y2)
                container.verticalRun?.dimension?.resolve(y2 - y1)
            }
            measureWidgets()
            checkRoot = true
        } else {
            // we'll bail out to the solver...
        }

        // Let's apply what we did resolve
        for (run in mRuns) {
            if (run.widget === container && !run.isResolved) {
                continue
            }
            run.applyToWidget()
        }
        var allResolved = true
        for (run in mRuns) {
            if (!checkRoot && run.widget === container) {
                continue
            }
            if (!run.start.resolved) {
                allResolved = false
                break
            }
            if (!run.end.resolved && run !is GuidelineReference) {
                allResolved = false
                break
            }
            if (!run.dimension.resolved && run !is ChainRun && run !is GuidelineReference) {
                allResolved = false
                break
            }
        }
        if (originalHorizontalDimension != null) {
            container.horizontalDimensionBehaviour = originalHorizontalDimension
        }
        if (originalVerticalDimension != null) {
            container.verticalDimensionBehaviour = originalVerticalDimension
        }
        return allResolved
    }

    fun directMeasureSetup(optimizeWrap: Boolean): Boolean {
        if (mNeedBuildGraph) {
            for (widget in container.children) {
                widget.ensureWidgetRuns()
                widget.measured = false
                widget.horizontalRun?.dimension?.resolved = false
                widget.horizontalRun?.isResolved = false
                widget.horizontalRun?.reset()
                widget.verticalRun?.dimension?.resolved = false
                widget.verticalRun?.isResolved = false
                widget.verticalRun?.reset()
            }
            container.ensureWidgetRuns()
            container.measured = false
            container.horizontalRun?.dimension?.resolved = false
            container.horizontalRun?.isResolved = false
            container.horizontalRun?.reset()
            container.verticalRun?.dimension?.resolved = false
            container.verticalRun?.isResolved = false
            container.verticalRun?.reset()
            buildGraph()
        }
        val avoid = basicMeasureWidgets(mContainer)
        if (avoid) {
            return false
        }
        container.x = 0
        container.y = 0
        container.horizontalRun?.start?.resolve(0)
        container.verticalRun?.start?.resolve(0)
        return true
    }

    fun directMeasureWithOrientation(optimizeWrap: Boolean, orientation: Int): Boolean {
        var optimizeWrap = optimizeWrap
        optimizeWrap = optimizeWrap and USE_GROUPS
        val originalHorizontalDimension = container.getDimensionBehaviour(ConstraintWidget.HORIZONTAL)
        val originalVerticalDimension = container.getDimensionBehaviour(ConstraintWidget.VERTICAL)
        val x1 = container.x
        val y1 = container.y

        // If we have to support wrap, let's see if we can compute it directly
        if (optimizeWrap && (originalHorizontalDimension == DimensionBehaviour.WRAP_CONTENT || originalVerticalDimension == DimensionBehaviour.WRAP_CONTENT)) {
            for (run in mRuns) {
                if (run.orientation == orientation
                    && !run.supportsWrapComputation()
                ) {
                    optimizeWrap = false
                    break
                }
            }
            if (orientation == ConstraintWidget.HORIZONTAL) {
                if (optimizeWrap && originalHorizontalDimension == DimensionBehaviour.WRAP_CONTENT) {
                    container.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
                    container.width = computeWrap(container, ConstraintWidget.HORIZONTAL)
                    container.horizontalRun?.dimension?.resolve(container.width)
                }
            } else {
                if (optimizeWrap && originalVerticalDimension == DimensionBehaviour.WRAP_CONTENT) {
                    container.verticalDimensionBehaviour = DimensionBehaviour.FIXED
                    container.height = computeWrap(container, ConstraintWidget.VERTICAL)
                    container.verticalRun?.dimension?.resolve(container.height)
                }
            }
        }
        var checkRoot = false

        // Now, depending on our own dimension behavior, we may want to solve
        // one dimension before the other
        if (orientation == ConstraintWidget.HORIZONTAL) {
            if (container.mListDimensionBehaviors[ConstraintWidget.HORIZONTAL] == DimensionBehaviour.FIXED
                || container.mListDimensionBehaviors[ConstraintWidget.HORIZONTAL] == DimensionBehaviour.MATCH_PARENT
            ) {
                val x2 = x1 + container.width
                container.horizontalRun?.end?.resolve(x2)
                container.horizontalRun?.dimension?.resolve(x2 - x1)
                checkRoot = true
            }
        } else {
            if (container.mListDimensionBehaviors[ConstraintWidget.VERTICAL] == DimensionBehaviour.FIXED
                || container.mListDimensionBehaviors[ConstraintWidget.VERTICAL] == DimensionBehaviour.MATCH_PARENT
            ) {
                val y2 = y1 + container.height
                container.verticalRun?.end?.resolve(y2)
                container.verticalRun?.dimension?.resolve(y2 - y1)
                checkRoot = true
            }
        }
        measureWidgets()

        // Let's apply what we did resolve
        for (run in mRuns) {
            if (run.orientation != orientation) {
                continue
            }
            if (run.widget === container && !run.isResolved) {
                continue
            }
            run.applyToWidget()
        }
        var allResolved = true
        for (run in mRuns) {
            if (run.orientation != orientation) {
                continue
            }
            if (!checkRoot && run.widget === container) {
                continue
            }
            if (!run.start.resolved) {
                allResolved = false
                break
            }
            if (!run.end.resolved) {
                allResolved = false
                break
            }
            if (run !is ChainRun && !run.dimension.resolved) {
                allResolved = false
                break
            }
        }
        if (originalHorizontalDimension != null) {
            container.horizontalDimensionBehaviour = originalHorizontalDimension
        }
        if (originalVerticalDimension != null) {
            container.verticalDimensionBehaviour = originalVerticalDimension
        }
        return allResolved
    }

    /**
     * Convenience function to fill in the measure spec
     *
     * @param widget the widget to measure
     * @param horizontalBehavior
     * @param horizontalDimension
     * @param verticalBehavior
     * @param verticalDimension
     */
    private fun measure(
        widget: ConstraintWidget,
        horizontalBehavior: DimensionBehaviour, horizontalDimension: Int,
        verticalBehavior: DimensionBehaviour, verticalDimension: Int
    ) {
        mMeasure.horizontalBehavior = horizontalBehavior
        mMeasure.verticalBehavior = verticalBehavior
        mMeasure.horizontalDimension = horizontalDimension
        mMeasure.verticalDimension = verticalDimension
        mMeasurer!!.measure(widget, mMeasure)
        widget.width = mMeasure.measuredWidth
        widget.height = mMeasure.measuredHeight
        widget.hasBaseline = mMeasure.measuredHasBaseline
        widget.baselineDistance = mMeasure.measuredBaseline
    }

    private fun basicMeasureWidgets(constraintWidgetContainer: ConstraintWidgetContainer): Boolean {
        for (widget in constraintWidgetContainer.children) {
            var horizontal = widget.mListDimensionBehaviors[ConstraintWidget.HORIZONTAL]
            var vertical = widget.mListDimensionBehaviors[ConstraintWidget.VERTICAL]
            if (widget.visibility == ConstraintWidget.GONE) {
                widget.measured = true
                continue
            }

            // Basic validation
            // TODO: might move this earlier in the process
            if (widget.mMatchConstraintPercentWidth < 1 && horizontal == DimensionBehaviour.MATCH_CONSTRAINT) {
                widget.mMatchConstraintDefaultWidth = ConstraintWidget.MATCH_CONSTRAINT_PERCENT
            }
            if (widget.mMatchConstraintPercentHeight < 1 && vertical == DimensionBehaviour.MATCH_CONSTRAINT) {
                widget.mMatchConstraintDefaultHeight = ConstraintWidget.MATCH_CONSTRAINT_PERCENT
            }
            if (widget.dimensionRatio > 0) {
                if (horizontal == DimensionBehaviour.MATCH_CONSTRAINT && (vertical == DimensionBehaviour.WRAP_CONTENT || vertical == DimensionBehaviour.FIXED)) {
                    widget.mMatchConstraintDefaultWidth = ConstraintWidget.MATCH_CONSTRAINT_RATIO
                } else if (vertical == DimensionBehaviour.MATCH_CONSTRAINT && (horizontal == DimensionBehaviour.WRAP_CONTENT || horizontal == DimensionBehaviour.FIXED)) {
                    widget.mMatchConstraintDefaultHeight = ConstraintWidget.MATCH_CONSTRAINT_RATIO
                } else if (horizontal == DimensionBehaviour.MATCH_CONSTRAINT && vertical == DimensionBehaviour.MATCH_CONSTRAINT) {
                    if (widget.mMatchConstraintDefaultWidth == ConstraintWidget.MATCH_CONSTRAINT_SPREAD) {
                        widget.mMatchConstraintDefaultWidth = ConstraintWidget.MATCH_CONSTRAINT_RATIO
                    }
                    if (widget.mMatchConstraintDefaultHeight == ConstraintWidget.MATCH_CONSTRAINT_SPREAD) {
                        widget.mMatchConstraintDefaultHeight = ConstraintWidget.MATCH_CONSTRAINT_RATIO
                    }
                }
            }
            if (horizontal == DimensionBehaviour.MATCH_CONSTRAINT && widget.mMatchConstraintDefaultWidth == ConstraintWidget.MATCH_CONSTRAINT_WRAP) {
                if (widget.mLeft?.target == null || widget.mRight?.target == null) {
                    horizontal = DimensionBehaviour.WRAP_CONTENT
                }
            }
            if (vertical == DimensionBehaviour.MATCH_CONSTRAINT && widget.mMatchConstraintDefaultHeight == ConstraintWidget.MATCH_CONSTRAINT_WRAP) {
                if (widget.mTop.target == null || widget.mBottom.target == null) {
                    vertical = DimensionBehaviour.WRAP_CONTENT
                }
            }
            widget.horizontalRun?.dimensionBehavior = horizontal
            widget.horizontalRun?.matchConstraintsType = widget.mMatchConstraintDefaultWidth
            widget.verticalRun?.dimensionBehavior = vertical
            widget.verticalRun?.matchConstraintsType = widget.mMatchConstraintDefaultHeight
            if ((horizontal == DimensionBehaviour.MATCH_PARENT || horizontal == DimensionBehaviour.FIXED || horizontal == DimensionBehaviour.WRAP_CONTENT)
                && (vertical == DimensionBehaviour.MATCH_PARENT || vertical == DimensionBehaviour.FIXED || vertical == DimensionBehaviour.WRAP_CONTENT)
            ) {
                var width = widget.width
                if (horizontal == DimensionBehaviour.MATCH_PARENT) {
                    width = constraintWidgetContainer.width - (widget.mLeft?.mMargin ?: 0) - (widget.mRight?.mMargin ?: 0)
                    horizontal = DimensionBehaviour.FIXED
                }
                var height = widget.height
                if (vertical == DimensionBehaviour.MATCH_PARENT) {
                    height = constraintWidgetContainer.height - widget.mTop.mMargin - widget.mBottom.mMargin
                    vertical = DimensionBehaviour.FIXED
                }
                measure(widget, horizontal, width, vertical, height)
                widget.horizontalRun?.dimension?.resolve(widget.width)
                widget.verticalRun?.dimension?.resolve(widget.height)
                widget.measured = true
                continue
            }
            if (horizontal == DimensionBehaviour.MATCH_CONSTRAINT && (vertical == DimensionBehaviour.WRAP_CONTENT || vertical == DimensionBehaviour.FIXED)) {
                if (widget.mMatchConstraintDefaultWidth == ConstraintWidget.MATCH_CONSTRAINT_RATIO) {
                    if (vertical == DimensionBehaviour.WRAP_CONTENT) {
                        measure(widget, DimensionBehaviour.WRAP_CONTENT, 0, DimensionBehaviour.WRAP_CONTENT, 0)
                    }
                    val height = widget.height
                    val width = (height * widget.dimensionRatio + 0.5f).toInt()
                    measure(widget, DimensionBehaviour.FIXED, width, DimensionBehaviour.FIXED, height)
                    widget.horizontalRun?.dimension?.resolve(widget.width)
                    widget.verticalRun?.dimension?.resolve(widget.height)
                    widget.measured = true
                    continue
                } else if (widget.mMatchConstraintDefaultWidth == ConstraintWidget.MATCH_CONSTRAINT_WRAP) {
                    measure(widget, DimensionBehaviour.WRAP_CONTENT, 0, vertical, 0)
                    widget.horizontalRun?.dimension?.wrapValue = widget.width
                    continue
                } else if (widget.mMatchConstraintDefaultWidth == ConstraintWidget.MATCH_CONSTRAINT_PERCENT) {
                    if (constraintWidgetContainer.mListDimensionBehaviors[ConstraintWidget.HORIZONTAL] == DimensionBehaviour.FIXED
                        || constraintWidgetContainer.mListDimensionBehaviors[ConstraintWidget.HORIZONTAL] == DimensionBehaviour.MATCH_PARENT
                    ) {
                        val percent = widget.mMatchConstraintPercentWidth
                        val width = (0.5f + percent * constraintWidgetContainer.width).toInt()
                        val height = widget.height
                        measure(widget, DimensionBehaviour.FIXED, width, vertical, height)
                        widget.horizontalRun?.dimension?.resolve(widget.width)
                        widget.verticalRun?.dimension?.resolve(widget.height)
                        widget.measured = true
                        continue
                    }
                } else {
                    // let's verify we have both constraints
                    if (widget.mListAnchors[ConstraintWidget.ANCHOR_LEFT]?.target == null
                        || widget.mListAnchors[ConstraintWidget.ANCHOR_RIGHT]?.target == null
                    ) {
                        measure(widget, DimensionBehaviour.WRAP_CONTENT, 0, vertical, 0)
                        widget.horizontalRun?.dimension?.resolve(widget.width)
                        widget.verticalRun?.dimension?.resolve(widget.height)
                        widget.measured = true
                        continue
                    }
                }
            }
            if (vertical == DimensionBehaviour.MATCH_CONSTRAINT && (horizontal == DimensionBehaviour.WRAP_CONTENT || horizontal == DimensionBehaviour.FIXED)) {
                if (widget.mMatchConstraintDefaultHeight == ConstraintWidget.MATCH_CONSTRAINT_RATIO) {
                    if (horizontal == DimensionBehaviour.WRAP_CONTENT) {
                        measure(widget, DimensionBehaviour.WRAP_CONTENT, 0, DimensionBehaviour.WRAP_CONTENT, 0)
                    }
                    val width = widget.width
                    var ratio = widget.dimensionRatio
                    if (widget.dimensionRatioSide == ConstraintWidget.UNKNOWN) {
                        ratio = 1f / ratio
                    }
                    val height = (width * ratio + 0.5f).toInt()
                    measure(widget, DimensionBehaviour.FIXED, width, DimensionBehaviour.FIXED, height)
                    widget.horizontalRun?.dimension?.resolve(widget.width)
                    widget.verticalRun?.dimension?.resolve(widget.height)
                    widget.measured = true
                    continue
                } else if (widget.mMatchConstraintDefaultHeight == ConstraintWidget.MATCH_CONSTRAINT_WRAP) {
                    measure(widget, horizontal, 0, DimensionBehaviour.WRAP_CONTENT, 0)
                    widget.verticalRun?.dimension?.wrapValue = widget.height
                    continue
                } else if (widget.mMatchConstraintDefaultHeight == ConstraintWidget.MATCH_CONSTRAINT_PERCENT) {
                    if (constraintWidgetContainer.mListDimensionBehaviors[ConstraintWidget.VERTICAL] == DimensionBehaviour.FIXED
                        || constraintWidgetContainer.mListDimensionBehaviors[ConstraintWidget.VERTICAL] == DimensionBehaviour.MATCH_PARENT
                    ) {
                        val percent = widget.mMatchConstraintPercentHeight
                        val width = widget.width
                        val height = (0.5f + percent * constraintWidgetContainer.height).toInt()
                        measure(widget, horizontal, width, DimensionBehaviour.FIXED, height)
                        widget.horizontalRun?.dimension?.resolve(widget.width)
                        widget.verticalRun?.dimension?.resolve(widget.height)
                        widget.measured = true
                        continue
                    }
                } else {
                    // let's verify we have both constraints
                    if (widget.mListAnchors[ConstraintWidget.ANCHOR_TOP]?.target == null
                        || widget.mListAnchors[ConstraintWidget.ANCHOR_BOTTOM]?.target == null
                    ) {
                        measure(widget, DimensionBehaviour.WRAP_CONTENT, 0, vertical, 0)
                        widget.horizontalRun?.dimension?.resolve(widget.width)
                        widget.verticalRun?.dimension?.resolve(widget.height)
                        widget.measured = true
                        continue
                    }
                }
            }
            if (horizontal == DimensionBehaviour.MATCH_CONSTRAINT && vertical == DimensionBehaviour.MATCH_CONSTRAINT) {
                if (widget.mMatchConstraintDefaultWidth == ConstraintWidget.MATCH_CONSTRAINT_WRAP
                    || widget.mMatchConstraintDefaultHeight == ConstraintWidget.MATCH_CONSTRAINT_WRAP
                ) {
                    measure(widget, DimensionBehaviour.WRAP_CONTENT, 0, DimensionBehaviour.WRAP_CONTENT, 0)
                    widget.horizontalRun?.dimension?.wrapValue = widget.width
                    widget.verticalRun?.dimension?.wrapValue = widget.height
                } else if (widget.mMatchConstraintDefaultHeight == ConstraintWidget.MATCH_CONSTRAINT_PERCENT && widget.mMatchConstraintDefaultWidth == ConstraintWidget.MATCH_CONSTRAINT_PERCENT && constraintWidgetContainer.mListDimensionBehaviors[ConstraintWidget.HORIZONTAL] == DimensionBehaviour.FIXED && constraintWidgetContainer.mListDimensionBehaviors[ConstraintWidget.VERTICAL] == DimensionBehaviour.FIXED) {
                    val horizPercent = widget.mMatchConstraintPercentWidth
                    val vertPercent = widget.mMatchConstraintPercentHeight
                    val width = (0.5f + horizPercent * constraintWidgetContainer.width).toInt()
                    val height = (0.5f + vertPercent * constraintWidgetContainer.height).toInt()
                    measure(widget, DimensionBehaviour.FIXED, width, DimensionBehaviour.FIXED, height)
                    widget.horizontalRun?.dimension?.resolve(widget.width)
                    widget.verticalRun?.dimension?.resolve(widget.height)
                    widget.measured = true
                }
            }
        }
        return false
    }

    fun measureWidgets() {
        for (widget in container.children) {
            if (widget.measured) {
                continue
            }
            val horiz = widget.mListDimensionBehaviors[ConstraintWidget.HORIZONTAL]
            val vert = widget.mListDimensionBehaviors[ConstraintWidget.VERTICAL]
            val horizMatchConstraintsType = widget.mMatchConstraintDefaultWidth
            val vertMatchConstraintsType = widget.mMatchConstraintDefaultHeight
            val horizWrap = (horiz == DimensionBehaviour.WRAP_CONTENT
                    || horiz == DimensionBehaviour.MATCH_CONSTRAINT && horizMatchConstraintsType == ConstraintWidget.MATCH_CONSTRAINT_WRAP)
            val vertWrap = (vert == DimensionBehaviour.WRAP_CONTENT
                    || vert == DimensionBehaviour.MATCH_CONSTRAINT && vertMatchConstraintsType == ConstraintWidget.MATCH_CONSTRAINT_WRAP)
            val horizResolved = widget.horizontalRun?.dimension?.resolved ?: false
            val vertResolved = widget.verticalRun?.dimension?.resolved ?: false
            if (horizResolved && vertResolved) {
                measure(
                    widget, DimensionBehaviour.FIXED, widget.horizontalRun?.dimension?.value ?: 0,
                    DimensionBehaviour.FIXED, widget.verticalRun?.dimension?.value ?: 0
                )
                widget.measured = true
            } else if (horizResolved && vertWrap) {
                measure(
                    widget, DimensionBehaviour.FIXED, widget.horizontalRun?.dimension?.value ?: 0,
                    DimensionBehaviour.WRAP_CONTENT, widget.verticalRun?.dimension?.value ?: 0
                )
                if (vert == DimensionBehaviour.MATCH_CONSTRAINT) {
                    widget.verticalRun?.dimension?.wrapValue = widget.height
                } else {
                    widget.verticalRun?.dimension?.resolve(widget.height)
                    widget.measured = true
                }
            } else if (vertResolved && horizWrap) {
                measure(
                    widget, DimensionBehaviour.WRAP_CONTENT, widget.horizontalRun?.dimension?.value ?: 0,
                    DimensionBehaviour.FIXED, widget.verticalRun?.dimension?.value ?: 0
                )
                if (horiz == DimensionBehaviour.MATCH_CONSTRAINT) {
                    widget.horizontalRun?.dimension?.wrapValue = widget.width
                } else {
                    widget.horizontalRun?.dimension?.resolve(widget.width)
                    widget.measured = true
                }
            }
            if (widget.measured && widget.verticalRun?.baselineDimension != null) {
                widget.verticalRun?.baselineDimension?.resolve(widget.baselineDistance)
            }
        }
    }

    /**
     * Invalidate the graph of constraints
     */
    fun invalidateGraph() {
        mNeedBuildGraph = true
    }

    /**
     * Mark the widgets as needing to be remeasured
     */
    fun invalidateMeasures() {
        mNeedRedoMeasures = true
    }

    var mGroups = ArrayList<RunGroup>()
    fun buildGraph() {
        // First, let's identify the overall dependency graph
        buildGraph(mRuns)
        if (USE_GROUPS) {
            mGroups.clear()
            // Then get the horizontal and vertical groups
            RunGroup.index = 0
            container.horizontalRun?.let { findGroup(it, ConstraintWidget.HORIZONTAL, mGroups) }
            container.verticalRun?.let { findGroup(it, ConstraintWidget.VERTICAL, mGroups) }
        }
        mNeedBuildGraph = false
    }

    fun buildGraph(runs: ArrayList<WidgetRun>) {
        runs.clear()
        mContainer.horizontalRun?.clear()
        mContainer.verticalRun?.clear()
        mContainer.horizontalRun?.let { runs.add(it) }
        mContainer.verticalRun?.let { runs.add(it) }
        var chainRuns: HashSet<ChainRun>? = null
        for (widget in mContainer.children) {
            if (widget is Guideline) {
                runs.add(GuidelineReference(widget))
                continue
            }
            if (widget.isInHorizontalChain) {
                if (widget.horizontalChainRun == null) {
                    // build the horizontal chain
                    widget.horizontalChainRun = ChainRun(widget, ConstraintWidget.HORIZONTAL)
                }
                if (chainRuns == null) {
                    chainRuns = HashSet()
                }
                chainRuns.add(widget.horizontalChainRun!!)
            } else {
                runs.add(widget.horizontalRun!!)
            }
            if (widget.isInVerticalChain) {
                if (widget.verticalChainRun == null) {
                    // build the vertical chain
                    widget.verticalChainRun = ChainRun(widget, ConstraintWidget.VERTICAL)
                }
                if (chainRuns == null) {
                    chainRuns = HashSet()
                }
                chainRuns.add(widget.verticalChainRun!!)
            } else {
                runs.add(widget.verticalRun!!)
            }
            if (widget is HelperWidget) {
                runs.add(HelperReferences(widget))
            }
        }
        if (chainRuns != null) {
            runs.addAll(chainRuns)
        }
        for (run in runs) {
            run.clear()
        }
        for (run in runs) {
            if (run.widget === mContainer) {
                continue
            }
            run.apply()
        }

//        displayGraph();
    }

    private fun displayGraph() {
        var content = "digraph {\n"
        for (run in mRuns) {
            content = generateDisplayGraph(run, content)
        }
        content += "\n}\n"
        println("content:<<\n$content\n>>")
    }

    private fun applyGroup(node: DependencyNode, orientation: Int, direction: Int, end: DependencyNode?, groups: ArrayList<RunGroup>, group: RunGroup?) {
        var group = group
        val run = node.run
        if (run.runGroup != null || run === container.horizontalRun || run === container.verticalRun) {
            return
        }
        if (group == null) {
            group = RunGroup(run, direction)
            groups.add(group)
        }
        run.runGroup = group
        group.add(run)
        for (dependent in run.start.dependencies) {
            if (dependent is DependencyNode) {
                applyGroup(dependent, orientation, RunGroup.START, end, groups, group)
            }
        }
        for (dependent in run.end.dependencies) {
            if (dependent is DependencyNode) {
                applyGroup(dependent, orientation, RunGroup.END, end, groups, group)
            }
        }
        if (orientation == ConstraintWidget.VERTICAL && run is VerticalWidgetRun) {
            for (dependent in run.baseline.dependencies) {
                if (dependent is DependencyNode) {
                    applyGroup(dependent, orientation, RunGroup.BASELINE, end, groups, group)
                }
            }
        }
        for (target in run.start.targets) {
            if (target === end) {
                group.dual = true
            }
            applyGroup(target, orientation, RunGroup.START, end, groups, group)
        }
        for (target in run.end.targets) {
            if (target === end) {
                group.dual = true
            }
            applyGroup(target, orientation, RunGroup.END, end, groups, group)
        }
        if (orientation == ConstraintWidget.VERTICAL && run is VerticalWidgetRun) {
            for (target in run.baseline.targets) {
                applyGroup(target, orientation, RunGroup.BASELINE, end, groups, group)
            }
        }
    }

    private fun findGroup(run: WidgetRun, orientation: Int, groups: ArrayList<RunGroup>) {
        for (dependent in run.start.dependencies) {
            if (dependent is DependencyNode) {
                applyGroup(dependent, orientation, RunGroup.START, run.end, groups, null)
            } else if (dependent is WidgetRun) {
                applyGroup(dependent.start, orientation, RunGroup.START, run.end, groups, null)
            }
        }
        for (dependent in run.end.dependencies) {
            if (dependent is DependencyNode) {
                applyGroup(dependent, orientation, RunGroup.END, run.start, groups, null)
            } else if (dependent is WidgetRun) {
                applyGroup(dependent.end, orientation, RunGroup.END, run.start, groups, null)
            }
        }
        if (orientation == ConstraintWidget.VERTICAL) {
            for (dependent in (run as VerticalWidgetRun).baseline.dependencies) {
                if (dependent is DependencyNode) {
                    applyGroup(dependent, orientation, RunGroup.BASELINE, null, groups, null)
                }
            }
        }
    }

    private fun generateDisplayNode(node: DependencyNode, centeredConnection: Boolean, content: String): String {
        var content: String = content
        val contentBuilder = StringBuilder(content)
        for (target in node.targets) {
            var constraint = """
                
                ${node.name()}
                """.trimIndent()
            constraint += " -> " + target.name()
            if (node.margin > 0 || centeredConnection || node.run is HelperReferences) {
                constraint += "["
                if (node.margin > 0) {
                    constraint += "label=\"" + node.margin + "\""
                    if (centeredConnection) {
                        constraint += ","
                    }
                }
                if (centeredConnection) {
                    constraint += " style=dashed "
                }
                if (node.run is HelperReferences) {
                    constraint += " style=bold,color=gray "
                }
                constraint += "]"
            }
            constraint += "\n"
            contentBuilder.append(constraint)
        }
        content = contentBuilder.toString()
        //        for (DependencyNode dependency : node.dependencies) {
//            content = generateDisplayNode(dependency, content);
//        }
        return content
    }

    private fun nodeDefinition(run: WidgetRun): String {
        val orientation = if (run is VerticalWidgetRun) ConstraintWidget.VERTICAL else ConstraintWidget.HORIZONTAL
        val name = run.widget.debugName ?: ""
        val definition = StringBuilder(name)
        val behaviour = if (orientation == ConstraintWidget.HORIZONTAL) run.widget.horizontalDimensionBehaviour else run.widget.verticalDimensionBehaviour
        val runGroup = run.runGroup
        if (orientation == ConstraintWidget.HORIZONTAL) {
            definition.append("_HORIZONTAL")
        } else {
            definition.append("_VERTICAL")
        }
        definition.append(" [shape=none, label=<")
        definition.append("<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"2\">")
        definition.append("  <TR>")
        if (orientation == ConstraintWidget.HORIZONTAL) {
            definition.append("    <TD ")
            if (run.start.resolved) {
                definition.append(" BGCOLOR=\"green\"")
            }
            definition.append(" PORT=\"LEFT\" BORDER=\"1\">L</TD>")
        } else {
            definition.append("    <TD ")
            if (run.start.resolved) {
                definition.append(" BGCOLOR=\"green\"")
            }
            definition.append(" PORT=\"TOP\" BORDER=\"1\">T</TD>")
        }
        definition.append("    <TD BORDER=\"1\" ")
        if (run.dimension.resolved && !run.widget.measured) {
            definition.append(" BGCOLOR=\"green\" ")
        } else if (run.dimension.resolved) {
            definition.append(" BGCOLOR=\"lightgray\" ")
        } else if (run.widget.measured) {
            definition.append(" BGCOLOR=\"yellow\" ")
        }
        if (behaviour == DimensionBehaviour.MATCH_CONSTRAINT) {
            definition.append("style=\"dashed\"")
        }
        definition.append(">")
        definition.append(name)
        if (runGroup != null) {
            definition.append(" [")
            definition.append(runGroup.groupIndex + 1)
            definition.append("/")
            definition.append(RunGroup.index)
            definition.append("]")
        }
        definition.append(" </TD>")
        if (orientation == ConstraintWidget.HORIZONTAL) {
            definition.append("    <TD ")
            if (run.end.resolved) {
                definition.append(" BGCOLOR=\"green\"")
            }
            definition.append(" PORT=\"RIGHT\" BORDER=\"1\">R</TD>")
        } else {
            definition.append("    <TD ")
            if ((run as VerticalWidgetRun).baseline.resolved) {
                definition.append(" BGCOLOR=\"green\"")
            }
            definition.append(" PORT=\"BASELINE\" BORDER=\"1\">b</TD>")
            definition.append("    <TD ")
            if (run.end.resolved) {
                definition.append(" BGCOLOR=\"green\"")
            }
            definition.append(" PORT=\"BOTTOM\" BORDER=\"1\">B</TD>")
        }
        definition.append("  </TR></TABLE>")
        definition.append(">];\n")
        return definition.toString()
    }

    private fun generateChainDisplayGraph(chain: ChainRun, content: String): String {
        val orientation = chain.orientation
        val subgroup = StringBuilder("subgraph ")
        subgroup.append("cluster_")
        subgroup.append(chain.widget.debugName)
        if (orientation == ConstraintWidget.HORIZONTAL) {
            subgroup.append("_h")
        } else {
            subgroup.append("_v")
        }
        subgroup.append(" {\n")
        var definitions = ""
        for (run in chain.widgets) {
            subgroup.append(run.widget.debugName)
            if (orientation == ConstraintWidget.HORIZONTAL) {
                subgroup.append("_HORIZONTAL")
            } else {
                subgroup.append("_VERTICAL")
            }
            subgroup.append(";\n")
            definitions = generateDisplayGraph(run, definitions)
        }
        subgroup.append("}\n")
        return content + definitions + subgroup
    }

    private fun isCenteredConnection(start: DependencyNode, end: DependencyNode): Boolean {
        var startTargets = 0
        var endTargets = 0
        for (s in start.targets) {
            if (s !== end) {
                startTargets++
            }
        }
        for (e in end.targets) {
            if (e !== start) {
                endTargets++
            }
        }
        return startTargets > 0 && endTargets > 0
    }

    private fun generateDisplayGraph(root: WidgetRun, content: String): String {
        var content = content
        val start = root.start
        val end = root.end
        val sb = StringBuilder(content)
        if (root !is HelperReferences && start.dependencies.isEmpty() && end.dependencies.isEmpty() and start.targets.isEmpty() && end.targets.isEmpty()) {
            return content
        }
        sb.append(nodeDefinition(root))
        val centeredConnection = isCenteredConnection(start, end)
        content = generateDisplayNode(start, centeredConnection, content)
        content = generateDisplayNode(end, centeredConnection, content)
        if (root is VerticalWidgetRun) {
            val baseline = root.baseline
            content = generateDisplayNode(baseline, centeredConnection, content)
        }
        if (root is HorizontalWidgetRun
            || root is ChainRun && root.orientation == ConstraintWidget.HORIZONTAL
        ) {
            val behaviour = root.widget.horizontalDimensionBehaviour
            if (behaviour == DimensionBehaviour.FIXED
                || behaviour == DimensionBehaviour.WRAP_CONTENT
            ) {
                if (!start.targets.isEmpty() && end.targets.isEmpty()) {
                    sb.append("\n")
                    sb.append(end.name())
                    sb.append(" -> ")
                    sb.append(start.name())
                    sb.append("\n")
                } else if (start.targets.isEmpty() && !end.targets.isEmpty()) {
                    sb.append("\n")
                    sb.append(start.name())
                    sb.append(" -> ")
                    sb.append(end.name())
                    sb.append("\n")
                }
            } else {
                if (behaviour == DimensionBehaviour.MATCH_CONSTRAINT && root.widget.dimensionRatio > 0) {
                    sb.append("\n")
                    sb.append(root.widget.debugName)
                    sb.append("_HORIZONTAL -> ")
                    sb.append(root.widget.debugName)
                    sb.append("_VERTICAL;\n")
                }
            }
        } else if (root is VerticalWidgetRun
            || root is ChainRun && root.orientation == ConstraintWidget.VERTICAL
        ) {
            val behaviour = root.widget.verticalDimensionBehaviour
            if (behaviour == DimensionBehaviour.FIXED
                || behaviour == DimensionBehaviour.WRAP_CONTENT
            ) {
                if (!start.targets.isEmpty() && end.targets.isEmpty()) {
                    sb.append("\n")
                    sb.append(end.name())
                    sb.append(" -> ")
                    sb.append(start.name())
                    sb.append("\n")
                } else if (start.targets.isEmpty() && !end.targets.isEmpty()) {
                    sb.append("\n")
                    sb.append(start.name())
                    sb.append(" -> ")
                    sb.append(end.name())
                    sb.append("\n")
                }
            } else {
                if (behaviour == DimensionBehaviour.MATCH_CONSTRAINT && root.widget.dimensionRatio > 0) {
                    sb.append("\n")
                    sb.append(root.widget.debugName)
                    sb.append("_VERTICAL -> ")
                    sb.append(root.widget.debugName)
                    sb.append("_HORIZONTAL;\n")
                }
            }
        }
        return if (root is ChainRun) {
            generateChainDisplayGraph(root, content)
        } else sb.toString()
    }

    companion object {
        private const val USE_GROUPS = true
    }

    init {
        mContainer = container
    }
}