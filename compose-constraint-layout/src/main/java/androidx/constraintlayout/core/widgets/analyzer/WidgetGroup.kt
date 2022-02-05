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

import androidx.constraintlayout.core.widgets.ConstraintWidget
import androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour
import androidx.constraintlayout.core.LinearSystem
import androidx.constraintlayout.core.widgets.Chain
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer

/**
 * Represents a group of widget for the grouping mechanism.
 */
class WidgetGroup(orientation: Int) {
    var widgets = ArrayList<ConstraintWidget>()
    var id = -1
    var isAuthoritative = false
    var orientation = ConstraintWidget.HORIZONTAL
    var results: ArrayList<MeasureResult>? = null
    private var moveTo = -1
    fun add(widget: ConstraintWidget): Boolean {
        if (widgets.contains(widget)) {
            return false
        }
        widgets.add(widget)
        return true
    }

    private val orientationString: String
        private get() {
            if (orientation == ConstraintWidget.HORIZONTAL) {
                return "Horizontal"
            } else if (orientation == ConstraintWidget.VERTICAL) {
                return "Vertical"
            } else if (orientation == ConstraintWidget.BOTH) {
                return "Both"
            }
            return "Unknown"
        }

    override fun toString(): String {
        var ret = "$orientationString [$id] <"
        for (widget in widgets) {
            ret += " " + widget.debugName
        }
        ret += " >"
        return ret
    }

    fun moveTo(orientation: Int, widgetGroup: WidgetGroup) {
        if (DEBUG) {
            println("Move all widgets (" + this + ") from " + id + " to " + widgetGroup.id + "(" + widgetGroup + ")")
        }
        for (widget in widgets) {
            widgetGroup.add(widget)
            if (orientation == ConstraintWidget.HORIZONTAL) {
                widget.horizontalGroup = widgetGroup.id
            } else {
                widget.verticalGroup = widgetGroup.id
            }
        }
        moveTo = widgetGroup.id
    }

    fun clear() {
        widgets.clear()
    }

    private fun measureWrap(orientation: Int, widget: ConstraintWidget): Int {
        val behaviour = widget.getDimensionBehaviour(orientation)
        if (behaviour == DimensionBehaviour.WRAP_CONTENT || behaviour == DimensionBehaviour.MATCH_PARENT || behaviour == DimensionBehaviour.FIXED) {
            val dimension: Int
            dimension = if (orientation == ConstraintWidget.HORIZONTAL) {
                widget.width
            } else {
                widget.height
            }
            return dimension
        }
        return -1
    }

    fun measureWrap(system: LinearSystem, orientation: Int): Int {
        val count = widgets.size
        return if (count == 0) {
            0
        } else solverMeasure(system, widgets, orientation)
        // TODO: add direct wrap computation for simpler cases instead of calling the solver
    }

    private fun solverMeasure(system: LinearSystem, widgets: ArrayList<ConstraintWidget>, orientation: Int): Int {
        val container = widgets[0].parent as ConstraintWidgetContainer
        system.reset()
        val prevDebug = LinearSystem.FULL_DEBUG
        container.addToSolver(system, false)
        for (i in widgets.indices) {
            val widget = widgets[i]
            widget.addToSolver(system, false)
        }
        if (orientation == ConstraintWidget.HORIZONTAL) {
            if (container.mHorizontalChainsSize > 0) {
                Chain.applyChainConstraints(container, system, widgets, ConstraintWidget.HORIZONTAL)
            }
        }
        if (orientation == ConstraintWidget.VERTICAL) {
            if (container.mVerticalChainsSize > 0) {
                Chain.applyChainConstraints(container, system, widgets, ConstraintWidget.VERTICAL)
            }
        }
        try {
            system.minimize()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // save results
        results = ArrayList()
        for (i in widgets.indices) {
            val widget = widgets[i]
            val result: MeasureResult = MeasureResult(widget, system, orientation)
            results!!.add(result)
        }
        return if (orientation == ConstraintWidget.HORIZONTAL) {
            val left = container.mLeft?.let { system.getObjectVariableValue(it) } ?: 0
            val right = container.mRight?.let { system.getObjectVariableValue(it) } ?: 0
            system.reset()
            right - left
        } else {
            val top = system.getObjectVariableValue(container.mTop)
            val bottom = system.getObjectVariableValue(container.mBottom)
            system.reset()
            bottom - top
        }
    }

    fun apply() {
        if (results == null) {
            return
        }
        if (!isAuthoritative) {
            return
        }
        for (i in results!!.indices) {
            val result = results!![i]
            result.apply()
        }
    }

    fun intersectWith(group: WidgetGroup): Boolean {
        for (i in widgets.indices) {
            val widget = widgets[i]
            if (group.contains(widget)) {
                return true
            }
        }
        return false
    }

    private operator fun contains(widget: ConstraintWidget): Boolean {
        return widgets.contains(widget)
    }

    fun size(): Int {
        return widgets.size
    }

    fun cleanup(dependencyLists: ArrayList<WidgetGroup>) {
        val count = widgets.size
        if (moveTo != -1 && count > 0) {
            for (i in dependencyLists.indices) {
                val group = dependencyLists[i]
                if (moveTo == group.id) {
                    moveTo(orientation, group)
                }
            }
        }
        if (count == 0) {
            dependencyLists.remove(this)
            return
        }
    }

    inner class MeasureResult(widget: ConstraintWidget, system: LinearSystem, orientation: Int) {
        // TODO weak reference
        //var widgetRef: WeakReference<ConstraintWidget>
        val widget = widget
        var left: Int
        var top: Int
        var right: Int
        var bottom: Int
        var baseline: Int
        var orientation: Int
        fun apply() {
            //val widget = widgetRef.get()
            widget?.setFinalFrame(left, top, right, bottom, baseline, orientation)
        }

        init {
            //widgetRef = WeakReference(widget)
            left = widget.mLeft?.let { system.getObjectVariableValue(it) } ?: 0
            top = system.getObjectVariableValue(widget.mTop)
            right = widget.mRight?.let { system.getObjectVariableValue(it) } ?: 0
            bottom = system.getObjectVariableValue(widget.mBottom)
            baseline = widget.mBaseline?.let { system.getObjectVariableValue(it) } ?: 0
            this.orientation = orientation
        }
    }

    companion object {
        private const val DEBUG = false
        var count = 0
    }

    init {
        id = count++
        this.orientation = orientation
    }
}