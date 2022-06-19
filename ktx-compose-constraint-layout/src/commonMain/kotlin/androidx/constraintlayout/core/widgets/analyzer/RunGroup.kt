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
import kotlin.math.max
import kotlin.math.min

class RunGroup(run: WidgetRun?, dir: Int) {
    var position = 0
    var dual = false
    var firstRun: WidgetRun? = null
    var lastRun: WidgetRun? = null
    var runs = ArrayList<WidgetRun>()
    var groupIndex = 0
    var direction: Int
    fun add(run: WidgetRun) {
        runs.add(run)
        lastRun = run
    }

    private fun traverseStart(node: DependencyNode, startPosition: Long): Long {
        val run = node.run
        if (run is HelperReferences) {
            return startPosition
        }
        var position = startPosition

        // first, compute stuff dependent on this node.
        val count = node.dependencies.size
        for (i in 0 until count) {
            val dependency = node.dependencies[i]
            if (dependency is DependencyNode) {
                val nextNode = dependency
                if (nextNode.run === run) {
                    // skip our own sibling node
                    continue
                }
                position = max(position, traverseStart(nextNode, startPosition + nextNode.margin))
            }
        }
        if (node === run.start) {
            // let's go for our sibling
            val dimension = run.wrapDimension
            position = max(position, traverseStart(run.end, startPosition + dimension))
            position = max(position, startPosition + dimension - run.end.margin)
        }
        return position
    }

    private fun traverseEnd(node: DependencyNode, startPosition: Long): Long {
        val run = node.run
        if (run is HelperReferences) {
            return startPosition
        }
        var position = startPosition

        // first, compute stuff dependent on this node.
        val count = node.dependencies.size
        for (i in 0 until count) {
            val dependency = node.dependencies[i]
            if (dependency is DependencyNode) {
                val nextNode = dependency
                if (nextNode.run === run) {
                    // skip our own sibling node
                    continue
                }
                position = min(position, traverseEnd(nextNode, startPosition + nextNode.margin))
            }
        }
        if (node === run.end) {
            // let's go for our sibling
            val dimension = run.wrapDimension
            position = min(position, traverseEnd(run.start, startPosition - dimension))
            position = min(position, startPosition - dimension - run.start.margin)
        }
        return position
    }

    fun computeWrapSize(container: ConstraintWidgetContainer, orientation: Int): Long {
        if (firstRun is ChainRun) {
            val chainRun = firstRun as ChainRun
            if (chainRun.orientation != orientation) {
                return 0
            }
        } else {
            if (orientation == ConstraintWidget.HORIZONTAL) {
                if (firstRun !is HorizontalWidgetRun) {
                    return 0
                }
            } else {
                if (firstRun !is VerticalWidgetRun) {
                    return 0
                }
            }
        }
        val containerStart = if (orientation == ConstraintWidget.HORIZONTAL) container.horizontalRun?.start else container.verticalRun?.start
        val containerEnd = if (orientation == ConstraintWidget.HORIZONTAL) container.horizontalRun?.end else container.verticalRun?.end
        val runWithStartTarget = firstRun!!.start.targets.contains(containerStart)
        val runWithEndTarget = firstRun!!.end.targets.contains(containerEnd)
        var dimension = firstRun!!.wrapDimension
        if (runWithStartTarget && runWithEndTarget) {
            val maxPosition = traverseStart(firstRun!!.start, 0)
            val minPosition = traverseEnd(firstRun!!.end, 0)

            // to compute the gaps, we subtract the margins
            var endGap = maxPosition - dimension
            if (endGap >= -firstRun!!.end.margin) {
                endGap += firstRun!!.end.margin.toLong()
            }
            var startGap = -minPosition - dimension - firstRun!!.start.margin
            if (startGap >= firstRun!!.start.margin) {
                startGap -= firstRun!!.start.margin.toLong()
            }
            val bias = firstRun!!.widget.getBiasPercent(orientation)
            var gap: Long = 0
            if (bias > 0) {
                gap = (startGap / bias + endGap / (1f - bias)).toLong()
            }
            startGap = (0.5f + gap * bias).toLong()
            endGap = (0.5f + gap * (1f - bias)).toLong()
            val runDimension = startGap + dimension + endGap
            dimension = firstRun!!.start.margin + runDimension - firstRun!!.end.margin
        } else if (runWithStartTarget) {
            val maxPosition = traverseStart(firstRun!!.start, firstRun!!.start.margin.toLong())
            val runDimension = firstRun!!.start.margin + dimension
            dimension = max(maxPosition, runDimension)
        } else if (runWithEndTarget) {
            val minPosition = traverseEnd(firstRun!!.end, firstRun!!.end.margin.toLong())
            val runDimension = -firstRun!!.end.margin + dimension
            dimension = max(-minPosition, runDimension)
        } else {
            dimension = firstRun!!.start.margin + firstRun!!.wrapDimension - firstRun!!.end.margin
        }
        return dimension
    }

    private fun defineTerminalWidget(run: WidgetRun, orientation: Int): Boolean {
        if (!run.widget.isTerminalWidget[orientation]) {
            return false
        }
        for (dependency in run.start.dependencies) {
            if (dependency is DependencyNode) {
                val node = dependency
                if (node.run === run) {
                    continue
                }
                if (node === node.run.start) {
                    if (run is ChainRun) {
                        for (widgetChainRun in run.widgets) {
                            defineTerminalWidget(widgetChainRun, orientation)
                        }
                    } else {
                        if (run !is HelperReferences) {
                            run.widget.isTerminalWidget[orientation] = false
                        }
                    }
                    defineTerminalWidget(node.run, orientation)
                }
            }
        }
        for (dependency in run.end.dependencies) {
            if (dependency is DependencyNode) {
                val node = dependency
                if (node.run === run) {
                    continue
                }
                if (node === node.run.start) {
                    if (run is ChainRun) {
                        for (widgetChainRun in run.widgets) {
                            defineTerminalWidget(widgetChainRun, orientation)
                        }
                    } else {
                        if (run !is HelperReferences) {
                            run.widget.isTerminalWidget[orientation] = false
                        }
                    }
                    defineTerminalWidget(node.run, orientation)
                }
            }
        }
        return false
    }

    fun defineTerminalWidgets(horizontalCheck: Boolean, verticalCheck: Boolean) {
        val horizontal = firstRun as? HorizontalWidgetRun
        if (horizontalCheck && horizontal != null) {
            defineTerminalWidget(horizontal, ConstraintWidget.HORIZONTAL)
        }
        val vertical = firstRun as? VerticalWidgetRun
        if (verticalCheck && vertical != null) {
            defineTerminalWidget(vertical, ConstraintWidget.VERTICAL)
        }
    }

    companion object {
        const val START = 0
        const val END = 1
        const val BASELINE = 2
        var index = 0
    }

    init {
        groupIndex = index
        index++
        firstRun = run
        lastRun = run
        direction = dir
    }
}