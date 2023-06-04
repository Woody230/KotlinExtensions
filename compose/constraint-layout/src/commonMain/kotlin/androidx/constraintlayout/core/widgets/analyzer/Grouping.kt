/*
 * Copyright (C) 2020 The Android Open Source Project
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
import androidx.constraintlayout.core.widgets.ConstraintAnchor
import androidx.constraintlayout.core.widgets.ConstraintWidget
import androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer
import androidx.constraintlayout.core.widgets.Flow
import androidx.constraintlayout.core.widgets.Guideline
import androidx.constraintlayout.core.widgets.HelperWidget

/**
 * Implements a simple grouping mechanism, to group interdependent widgets together.
 *
 * TODO: we should move towards a more leaner implementation -- this is more expensive as it could be.
 */
object Grouping {
    private const val DEBUG = false
    private const val DEBUG_GROUPING = false
    fun validInGroup(
        layoutHorizontal: DimensionBehaviour, layoutVertical: DimensionBehaviour,
        widgetHorizontal: DimensionBehaviour, widgetVertical: DimensionBehaviour
    ): Boolean {
        val fixedHorizontal =
            widgetHorizontal == DimensionBehaviour.FIXED || widgetHorizontal == DimensionBehaviour.WRAP_CONTENT || widgetHorizontal == DimensionBehaviour.MATCH_PARENT && layoutHorizontal != DimensionBehaviour.WRAP_CONTENT
        val fixedVertical =
            widgetVertical == DimensionBehaviour.FIXED || widgetVertical == DimensionBehaviour.WRAP_CONTENT || widgetVertical == DimensionBehaviour.MATCH_PARENT && layoutVertical != DimensionBehaviour.WRAP_CONTENT
        return if (fixedHorizontal || fixedVertical) {
            true
        } else false
    }

    @JvmStatic
    fun simpleSolvingPass(layout: ConstraintWidgetContainer, measurer: BasicMeasure.Measurer?): Boolean {
        if (DEBUG) {
            println("*** GROUP SOLVING ***")
        }
        val children = layout.children
        val count = children.size
        var verticalGuidelines: ArrayList<Guideline>? = null
        var horizontalGuidelines: ArrayList<Guideline>? = null
        var horizontalBarriers: ArrayList<HelperWidget>? = null
        var verticalBarriers: ArrayList<HelperWidget>? = null
        var isolatedHorizontalChildren: ArrayList<ConstraintWidget>? = null
        var isolatedVerticalChildren: ArrayList<ConstraintWidget>? = null
        for (i in 0 until count) {
            val child = children[i]
            if (!validInGroup(
                    layout.horizontalDimensionBehaviour, layout.verticalDimensionBehaviour,
                    child.horizontalDimensionBehaviour, child.verticalDimensionBehaviour
                )
            ) {
                if (DEBUG) {
                    println("*** NO GROUP SOLVING ***")
                }
                return false
            }
            if (child is Flow) {
                return false
            }
        }
        layout.mMetrics?.let {
            it.grouping++
        }
        for (i in 0 until count) {
            val child = children[i]
            if (!validInGroup(
                    layout.horizontalDimensionBehaviour, layout.verticalDimensionBehaviour,
                    child.horizontalDimensionBehaviour, child.verticalDimensionBehaviour
                )
            ) {
                ConstraintWidgetContainer.measure(0, child, measurer, layout.mMeasure, BasicMeasure.Measure.SELF_DIMENSIONS)
            }
            if (child is Guideline) {
                val guideline = child
                if (guideline.orientation == ConstraintWidget.HORIZONTAL) {
                    if (horizontalGuidelines == null) {
                        horizontalGuidelines = ArrayList()
                    }
                    horizontalGuidelines.add(guideline)
                }
                if (guideline.orientation == ConstraintWidget.VERTICAL) {
                    if (verticalGuidelines == null) {
                        verticalGuidelines = ArrayList()
                    }
                    verticalGuidelines.add(guideline)
                }
            }
            if (child is HelperWidget) {
                if (child is Barrier) {
                    val barrier = child
                    if (barrier.orientation == ConstraintWidget.HORIZONTAL) {
                        if (horizontalBarriers == null) {
                            horizontalBarriers = ArrayList()
                        }
                        horizontalBarriers.add(barrier)
                    }
                    if (barrier.orientation == ConstraintWidget.VERTICAL) {
                        if (verticalBarriers == null) {
                            verticalBarriers = ArrayList()
                        }
                        verticalBarriers.add(barrier)
                    }
                } else {
                    val helper = child
                    if (horizontalBarriers == null) {
                        horizontalBarriers = ArrayList()
                    }
                    horizontalBarriers.add(helper)
                    if (verticalBarriers == null) {
                        verticalBarriers = ArrayList()
                    }
                    verticalBarriers.add(helper)
                }
            }
            if (child.mLeft?.target == null && child.mRight?.target == null && child !is Guideline && child !is Barrier) {
                if (isolatedHorizontalChildren == null) {
                    isolatedHorizontalChildren = ArrayList()
                }
                isolatedHorizontalChildren.add(child)
            }
            if (child.mTop.target == null && child.mBottom.target == null && child.mBaseline?.target == null && child !is Guideline && child !is Barrier) {
                if (isolatedVerticalChildren == null) {
                    isolatedVerticalChildren = ArrayList()
                }
                isolatedVerticalChildren.add(child)
            }
        }
        val allDependencyLists = ArrayList<WidgetGroup>()
        if (true || layout.horizontalDimensionBehaviour == DimensionBehaviour.WRAP_CONTENT) {
            if (verticalGuidelines != null) {
                for (guideline in verticalGuidelines) {
                    findDependents(guideline, ConstraintWidget.HORIZONTAL, allDependencyLists, null)
                }
            }
            horizontalBarriers?.forEach { barrier ->
                val group = findDependents(barrier, ConstraintWidget.HORIZONTAL, allDependencyLists, null)
                group?.let {
                    barrier.addDependents(allDependencyLists, ConstraintWidget.HORIZONTAL, group)
                    group.cleanup(allDependencyLists)
                }
            }
            val left = layout.getAnchor(ConstraintAnchor.Type.LEFT)
            left?.dependents?.forEach { first ->
                findDependents(first.owner, ConstraintWidget.HORIZONTAL, allDependencyLists, null)
            }
            val right = layout.getAnchor(ConstraintAnchor.Type.RIGHT)
            right?.dependents?.forEach { first ->
                findDependents(first.owner, ConstraintWidget.HORIZONTAL, allDependencyLists, null)

            }
            val center = layout.getAnchor(ConstraintAnchor.Type.CENTER)
            center?.dependents?.forEach { first ->
                findDependents(first.owner, ConstraintWidget.HORIZONTAL, allDependencyLists, null)
            }
            isolatedHorizontalChildren?.forEach { widget ->
                findDependents(widget, ConstraintWidget.HORIZONTAL, allDependencyLists, null)
            }
        }
        if (true || layout.verticalDimensionBehaviour == DimensionBehaviour.WRAP_CONTENT) {
            if (horizontalGuidelines != null) {
                for (guideline in horizontalGuidelines) {
                    findDependents(guideline, ConstraintWidget.VERTICAL, allDependencyLists, null)
                }
            }
            if (verticalBarriers != null) {
                for (barrier in verticalBarriers) {
                    val group = findDependents(barrier, ConstraintWidget.VERTICAL, allDependencyLists, null)
                    group?.let {
                        barrier.addDependents(allDependencyLists, ConstraintWidget.VERTICAL, group)
                        group.cleanup(allDependencyLists)
                    }
                }
            }
            val top = layout.getAnchor(ConstraintAnchor.Type.TOP)
            top?.dependents?.forEach { first ->
                findDependents(first.owner, ConstraintWidget.VERTICAL, allDependencyLists, null)
            }
            val baseline = layout.getAnchor(ConstraintAnchor.Type.BASELINE)
            baseline?.dependents?.forEach { first ->
                findDependents(first.owner, ConstraintWidget.VERTICAL, allDependencyLists, null)
            }
            val bottom = layout.getAnchor(ConstraintAnchor.Type.BOTTOM)
            bottom?.dependents?.forEach { first ->
                findDependents(first.owner, ConstraintWidget.VERTICAL, allDependencyLists, null)
            }
            val center = layout.getAnchor(ConstraintAnchor.Type.CENTER)
            center?.dependents?.forEach { first ->
                findDependents(first.owner, ConstraintWidget.VERTICAL, allDependencyLists, null)
            }
            isolatedVerticalChildren?.forEach { widget ->
                findDependents(widget, ConstraintWidget.VERTICAL, allDependencyLists, null)
            }
        }
        // Now we may have to merge horizontal/vertical dependencies
        for (i in 0 until count) {
            val child = children[i]
            if (child.oppositeDimensionsTied()) {
                val horizontalGroup = findGroup(allDependencyLists, child.horizontalGroup)
                val verticalGroup = findGroup(allDependencyLists, child.verticalGroup)
                if (horizontalGroup != null && verticalGroup != null) {
                    if (DEBUG_GROUPING) {
                        println("Merging $horizontalGroup to $verticalGroup for $child")
                    }
                    horizontalGroup.moveTo(ConstraintWidget.HORIZONTAL, verticalGroup)
                    verticalGroup.orientation = ConstraintWidget.BOTH
                    allDependencyLists.remove(horizontalGroup)
                }
            }
            if (DEBUG_GROUPING) {
                println("Widget " + child + " => " + child.horizontalGroup + " : " + child.verticalGroup)
            }
        }
        if (allDependencyLists.size <= 1) {
            return false
        }
        if (DEBUG) {
            println("----------------------------------")
            println("-- Horizontal dependency lists:")
            println("----------------------------------")
            for (list in allDependencyLists) {
                if (list.orientation != ConstraintWidget.VERTICAL) {
                    println("list: $list")
                }
            }
            println("----------------------------------")
            println("-- Vertical dependency lists:")
            println("----------------------------------")
            for (list in allDependencyLists) {
                if (list.orientation != ConstraintWidget.HORIZONTAL) {
                    println("list: $list")
                }
            }
            println("----------------------------------")
        }
        var horizontalPick: WidgetGroup? = null
        var verticalPick: WidgetGroup? = null
        if (layout.horizontalDimensionBehaviour == DimensionBehaviour.WRAP_CONTENT) {
            var maxWrap = 0
            var picked: WidgetGroup? = null
            for (list in allDependencyLists) {
                if (list.orientation == ConstraintWidget.VERTICAL) {
                    continue
                }
                list.isAuthoritative = false
                val wrap = list.measureWrap(layout.system, ConstraintWidget.HORIZONTAL)
                if (wrap > maxWrap) {
                    picked = list
                    maxWrap = wrap
                }
                if (DEBUG) {
                    println("list: $list => $wrap")
                }
            }
            if (picked != null) {
                if (DEBUG) {
                    println("Horizontal MaxWrap : $maxWrap with group $picked")
                }
                layout.horizontalDimensionBehaviour = DimensionBehaviour.FIXED
                layout.width = maxWrap
                picked.isAuthoritative = true
                horizontalPick = picked
            }
        }
        if (layout.verticalDimensionBehaviour == DimensionBehaviour.WRAP_CONTENT) {
            var maxWrap = 0
            var picked: WidgetGroup? = null
            for (list in allDependencyLists) {
                if (list.orientation == ConstraintWidget.HORIZONTAL) {
                    continue
                }
                list.isAuthoritative = false
                val wrap = list.measureWrap(layout.system, ConstraintWidget.VERTICAL)
                if (wrap > maxWrap) {
                    picked = list
                    maxWrap = wrap
                }
                if (DEBUG) {
                    println("      $list => $wrap")
                }
            }
            if (picked != null) {
                if (DEBUG) {
                    println("Vertical MaxWrap : $maxWrap with group $picked")
                }
                layout.verticalDimensionBehaviour = DimensionBehaviour.FIXED
                layout.height = maxWrap
                picked.isAuthoritative = true
                verticalPick = picked
            }
        }
        return horizontalPick != null || verticalPick != null
    }

    private fun findGroup(horizontalDependencyLists: ArrayList<WidgetGroup>, groupId: Int): WidgetGroup? {
        val count = horizontalDependencyLists.size
        for (i in 0 until count) {
            val group = horizontalDependencyLists[i]
            if (groupId == group.id) {
                return group
            }
        }
        return null
    }

    @JvmStatic
    fun findDependents(constraintWidget: ConstraintWidget, orientation: Int, list: ArrayList<WidgetGroup>, group: WidgetGroup?): WidgetGroup? {
        var group = group
        var groupId = -1
        groupId = if (orientation == ConstraintWidget.HORIZONTAL) {
            constraintWidget.horizontalGroup
        } else {
            constraintWidget.verticalGroup
        }
        if (DEBUG_GROUPING) {
            println(
                "--- find " + (if (orientation == ConstraintWidget.HORIZONTAL) "Horiz" else "Vert") + " dependents of " + constraintWidget.debugName
                        + " group " + group + " widget group id " + groupId
            )
        }
        if (groupId != -1 && (group == null || groupId != group.id)) {
            // already in a group!
            if (DEBUG_GROUPING) {
                println("widget " + constraintWidget.debugName + " already in group " + groupId + " group: " + group)
            }
            for (i in list.indices) {
                val widgetGroup = list[i]
                if (widgetGroup.id == groupId) {
                    if (group != null) {
                        if (DEBUG_GROUPING) {
                            println("Move group $group to $widgetGroup")
                        }
                        group.moveTo(orientation, widgetGroup)
                        list.remove(group)
                    }
                    group = widgetGroup
                    break
                }
            }
        } else if (groupId != -1) {
            return group
        }
        if (group == null) {
            if (constraintWidget is HelperWidget) {
                groupId = constraintWidget.findGroupInDependents(orientation)
                if (groupId != -1) {
                    for (i in list.indices) {
                        val widgetGroup = list[i]
                        if (widgetGroup.id == groupId) {
                            group = widgetGroup
                            break
                        }
                    }
                }
            }
            if (group == null) {
                group = WidgetGroup(orientation)
            }
            if (DEBUG_GROUPING) {
                println("Create group " + group + " for widget " + constraintWidget.debugName)
            }
            list.add(group)
        }
        if (group.add(constraintWidget)) {
            if (constraintWidget is Guideline) {
                val guideline = constraintWidget
                guideline.anchor.findDependents(if (guideline.orientation == Guideline.HORIZONTAL) ConstraintWidget.VERTICAL else ConstraintWidget.HORIZONTAL, list, group)
            }
            if (orientation == ConstraintWidget.HORIZONTAL) {
                constraintWidget.horizontalGroup = group.id
                if (DEBUG_GROUPING) {
                    println("Widget " + constraintWidget.debugName + " H group is " + constraintWidget.horizontalGroup)
                }
                constraintWidget.mLeft?.findDependents(orientation, list, group)
                constraintWidget.mRight?.findDependents(orientation, list, group)
            } else {
                constraintWidget.verticalGroup = group.id
                if (DEBUG_GROUPING) {
                    println("Widget " + constraintWidget.debugName + " V group is " + constraintWidget.verticalGroup)
                }
                constraintWidget.mTop.findDependents(orientation, list, group)
                constraintWidget.mBaseline?.findDependents(orientation, list, group)
                constraintWidget.mBottom.findDependents(orientation, list, group)
            }
            constraintWidget.mCenter.findDependents(orientation, list, group)
        }
        return group
    }
}