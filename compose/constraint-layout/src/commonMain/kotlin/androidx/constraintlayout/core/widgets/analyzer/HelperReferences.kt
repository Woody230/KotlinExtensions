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

import androidx.constraintlayout.core.widgets.Barrier
import androidx.constraintlayout.core.widgets.ConstraintWidget

internal class HelperReferences(widget: ConstraintWidget?) : WidgetRun(widget!!) {
    override fun clear() {
        runGroup = null
        start.clear()
    }

    override fun reset() {
        start.resolved = false
    }

    override fun supportsWrapComputation(): Boolean {
        return false
    }

    private fun addDependency(node: DependencyNode?) {
        node?.let {
            start.dependencies.add(node)
            node.targets.add(start)
        }
    }

    override fun apply() {
        if (widget is Barrier) {
            start.delegateToWidgetRun = true
            val barrier = widget as Barrier
            val type = barrier.barrierType
            val allowsGoneWidget = barrier.allowsGoneWidget
            when (type) {
                Barrier.LEFT -> {
                    start.type = DependencyNode.Type.LEFT
                    var i = 0
                    while (i < barrier.mWidgetsCount) {
                        val refWidget = barrier.mWidgets[i]
                        if (!allowsGoneWidget && refWidget?.visibility == ConstraintWidget.GONE) {
                            i++
                            continue
                        }
                        refWidget?.horizontalRun?.start?.let { target ->
                            target.dependencies.add(start)
                            start.targets.add(target)
                        }
                        i++
                    }
                    addDependency(widget.horizontalRun?.start)
                    addDependency(widget.horizontalRun?.end)
                }
                Barrier.RIGHT -> {
                    start.type = DependencyNode.Type.RIGHT
                    var i = 0
                    while (i < barrier.mWidgetsCount) {
                        val refWidget = barrier.mWidgets[i]
                        if (!allowsGoneWidget && refWidget?.visibility == ConstraintWidget.GONE) {
                            i++
                            continue
                        }
                        refWidget?.horizontalRun?.end?.let { target ->
                            target.dependencies.add(start)
                            start.targets.add(target)
                        }
                        i++
                    }
                    addDependency(widget.horizontalRun?.start)
                    addDependency(widget.horizontalRun?.end)
                }
                Barrier.TOP -> {
                    start.type = DependencyNode.Type.TOP
                    var i = 0
                    while (i < barrier.mWidgetsCount) {
                        val refwidget = barrier.mWidgets[i]
                        if (!allowsGoneWidget && refwidget?.visibility == ConstraintWidget.GONE) {
                            i++
                            continue
                        }
                        refwidget?.verticalRun?.start?.let { target ->
                            target.dependencies.add(start)
                            start.targets.add(target)
                        }
                        i++
                    }
                    addDependency(widget.verticalRun?.start)
                    addDependency(widget.verticalRun?.end)
                }
                Barrier.BOTTOM -> {
                    start.type = DependencyNode.Type.BOTTOM
                    var i = 0
                    while (i < barrier.mWidgetsCount) {
                        val refwidget = barrier.mWidgets[i]
                        if (!allowsGoneWidget && refwidget?.visibility == ConstraintWidget.GONE) {
                            i++
                            continue
                        }
                        refwidget?.verticalRun?.end?.let { target ->
                            target.dependencies.add(start)
                            start.targets.add(target)
                        }
                        i++
                    }
                    addDependency(widget.verticalRun?.start)
                    addDependency(widget.verticalRun?.end)
                }
            }
        }
    }

    override fun update(dependency: Dependency) {
        val barrier = widget as Barrier
        val type = barrier.barrierType
        var min = -1
        var max = 0
        for (node in start.targets) {
            val value = node.value
            if (min == -1 || value < min) {
                min = value
            }
            if (max < value) {
                max = value
            }
        }
        if (type == Barrier.LEFT || type == Barrier.TOP) {
            start.resolve(min + barrier.margin)
        } else {
            start.resolve(max + barrier.margin)
        }
    }

    override fun applyToWidget() {
        if (widget is Barrier) {
            val barrier = widget as Barrier
            val type = barrier.barrierType
            if (type == Barrier.LEFT
                || type == Barrier.RIGHT
            ) {
                widget.x = start.value
            } else {
                widget.y = start.value
            }
        }
    }
}