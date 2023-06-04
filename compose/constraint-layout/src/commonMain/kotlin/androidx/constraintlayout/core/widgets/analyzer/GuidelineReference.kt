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
import androidx.constraintlayout.core.widgets.Guideline

internal class GuidelineReference(widget: ConstraintWidget) : WidgetRun(widget) {
    override fun clear() {
        start.clear()
    }

    override fun reset() {
        start.resolved = false
        end.resolved = false
    }

    override fun supportsWrapComputation(): Boolean {
        return false
    }

    private fun addDependency(node: DependencyNode) {
        start.dependencies.add(node)
        node.targets.add(start)
    }

    override fun update(dependency: Dependency) {
        if (!start.readyToSolve) {
            return
        }
        if (start.resolved) {
            return
        }
        // ready to solve, centering.
        val startTarget = start.targets[0]
        val guideline = widget as Guideline
        val startPos = (0.5f + startTarget.value * guideline.relativePercent).toInt()
        start.resolve(startPos)
    }

    override fun apply() {
        val guideline = widget as Guideline
        val relativeBegin = guideline.relativeBegin
        val relativeEnd = guideline.relativeEnd
        val percent = guideline.relativePercent
        if (guideline.orientation == ConstraintWidget.VERTICAL) {
            if (relativeBegin != -1) {
                start.targets.add(widget.parent!!.horizontalRun!!.start)
                widget.parent!!.horizontalRun!!.start.dependencies.add(start)
                start.margin = relativeBegin
            } else if (relativeEnd != -1) {
                start.targets.add(widget.parent!!.horizontalRun!!.end)
                widget.parent!!.horizontalRun!!.end.dependencies.add(start)
                start.margin = -relativeEnd
            } else {
                start.delegateToWidgetRun = true
                start.targets.add(widget.parent!!.horizontalRun!!.end)
                widget.parent!!.horizontalRun!!.end.dependencies.add(start)
            }
            // FIXME -- if we move the DependencyNode directly in the ConstraintAnchor we'll be good.
            addDependency(widget.horizontalRun!!.start)
            addDependency(widget.horizontalRun!!.end)
        } else {
            if (relativeBegin != -1) {
                start.targets.add(widget.parent!!.verticalRun!!.start)
                widget.parent!!.verticalRun!!.start.dependencies.add(start)
                start.margin = relativeBegin
            } else if (relativeEnd != -1) {
                start.targets.add(widget.parent!!.verticalRun!!.end)
                widget.parent!!.verticalRun!!.end.dependencies.add(start)
                start.margin = -relativeEnd
            } else {
                start.delegateToWidgetRun = true
                start.targets.add(widget.parent!!.verticalRun!!.end)
                widget.parent!!.verticalRun!!.end.dependencies.add(start)
            }
            // FIXME -- if we move the DependencyNode directly in the ConstraintAnchor we'll be good.
            addDependency(widget.verticalRun!!.start)
            addDependency(widget.verticalRun!!.end)
        }
    }

    override fun applyToWidget() {
        val guideline = widget as Guideline
        if (guideline.orientation == ConstraintWidget.VERTICAL) {
            widget.x = start.value
        } else {
            widget.y = start.value
        }
    }

    init {
        widget.horizontalRun?.clear()
        widget.verticalRun?.clear()
        orientation = (widget as Guideline).orientation
    }
}