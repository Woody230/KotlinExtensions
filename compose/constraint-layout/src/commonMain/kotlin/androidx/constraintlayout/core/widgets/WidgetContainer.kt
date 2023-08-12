/*
 * Copyright (C) 2016 The Android Open Source Project
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
package androidx.constraintlayout.core.widgets

import androidx.constraintlayout.core.Cache

/**
 * A container of ConstraintWidget
 */
open class WidgetContainer : ConstraintWidget {
    /**
     * Access the children
     *
     * @return the array of children
     */
    var children: ArrayList<ConstraintWidget> = ArrayList()
    /*-----------------------------------------------------------------------*/ // Construction
    /*-----------------------------------------------------------------------*/
    /**
     * Default constructor
     */
    constructor() {}

    /**
     * Constructor
     *
     * @param x      x position
     * @param y      y position
     * @param width  width of the layout
     * @param height height of the layout
     */
    constructor(x: Int, y: Int, width: Int, height: Int) : super(x, y, width, height) {}

    /**
     * Constructor
     *
     * @param width  width of the layout
     * @param height height of the layout
     */
    constructor(width: Int, height: Int) : super(width, height) {}

    override fun reset() {
        children!!.clear()
        super.reset()
    }

    /**
     * Add a child widget
     *
     * @param widget to add
     */
    fun add(widget: ConstraintWidget) {
        children!!.add(widget)
        if (widget.parent != null) {
            val container = widget.parent as WidgetContainer?
            container!!.remove(widget)
        }
        widget.parent = this
    }

    /**
     * Add multiple child widgets.
     *
     * @param widgets to add
     */
    fun add(vararg widgets: ConstraintWidget) {
        val count = widgets.size
        for (i in 0 until count) {
            add(widgets[i])
        }
    }

    /**
     * Remove a child widget
     *
     * @param widget to remove
     */
    fun remove(widget: ConstraintWidget) {
        children!!.remove(widget)
        widget.reset()
    }

    /**
     * Return the top-level ConstraintWidgetContainer
     *
     * @return top-level ConstraintWidgetContainer
     */
    val rootConstraintContainer: ConstraintWidgetContainer?
        get() {
            var item: ConstraintWidget = this
            var parent = item.parent
            var container: ConstraintWidgetContainer? = null
            if (item is ConstraintWidgetContainer) {
                container = this as ConstraintWidgetContainer
            }
            while (parent != null) {
                item = parent
                parent = item.parent
                if (item is ConstraintWidgetContainer) {
                    container = item
                }
            }
            return container
        }
    /*-----------------------------------------------------------------------*/ // Overloaded methods from ConstraintWidget
    /*-----------------------------------------------------------------------*/
    /**
     * Set the offset of this widget relative to the root widget.
     * We then set the offset of our children as well.
     *
     * @param x horizontal offset
     * @param y vertical offset
     */
    override fun setOffset(x: Int, y: Int) {
        super.setOffset(x, y)
        val count = children!!.size
        for (i in 0 until count) {
            val widget = children!![i]
            widget.setOffset(rootX, rootY)
        }
    }

    /**
     * Function implemented by ConstraintWidgetContainer
     */
    open fun layout() {
        if (children == null) {
            return
        }
        val count = children!!.size
        for (i in 0 until count) {
            val widget = children!![i]
            if (widget is WidgetContainer) {
                widget.layout()
            }
        }
    }

    override fun resetSolverVariables(cache: Cache?) {
        super.resetSolverVariables(cache)
        val count = children!!.size
        for (i in 0 until count) {
            val widget = children!![i]
            widget.resetSolverVariables(cache)
        }
    }

    fun removeAllChildren() {
        children!!.clear()
    }
}