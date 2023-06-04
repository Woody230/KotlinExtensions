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
package androidx.constraintlayout.core.state

import androidx.constraintlayout.core.state.helpers.AlignHorizontallyReference
import androidx.constraintlayout.core.state.helpers.AlignVerticallyReference
import androidx.constraintlayout.core.state.helpers.BarrierReference
import androidx.constraintlayout.core.state.helpers.GuidelineReference
import androidx.constraintlayout.core.state.helpers.HorizontalChainReference
import androidx.constraintlayout.core.state.helpers.VerticalChainReference
import androidx.constraintlayout.core.widgets.ConstraintWidget
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer

/**
 * Represents a full state of a ConstraintLayout
 */
open class State {
    protected var mReferences = HashMap<Any?, Reference?>()
    protected var mHelperReferences = HashMap<Any?, HelperReference>()
    var mTags = HashMap<String, ArrayList<String>>()
    val mParent = ConstraintReference(this)

    enum class Constraint {
        LEFT_TO_LEFT, LEFT_TO_RIGHT, RIGHT_TO_LEFT, RIGHT_TO_RIGHT, START_TO_START, START_TO_END, END_TO_START, END_TO_END, TOP_TO_TOP, TOP_TO_BOTTOM, BOTTOM_TO_TOP, BOTTOM_TO_BOTTOM, BASELINE_TO_BASELINE, BASELINE_TO_TOP, BASELINE_TO_BOTTOM, CENTER_HORIZONTALLY, CENTER_VERTICALLY, CIRCULAR_CONSTRAINT
    }

    enum class Direction {
        LEFT, RIGHT, START, END, TOP, BOTTOM
    }

    enum class Helper {
        HORIZONTAL_CHAIN, VERTICAL_CHAIN, ALIGN_HORIZONTALLY, ALIGN_VERTICALLY, BARRIER, LAYER, FLOW
    }

    enum class Chain {
        SPREAD, SPREAD_INSIDE, PACKED
    }

    open fun reset() {
        mHelperReferences.clear()
        mTags.clear()
    }

    /**
     * Implements a conversion function for values, returning int.
     * This can be used in case values (e.g. margins) are represented
     * via an object, not directly an int.
     *
     * @param value the object to convert from
     * @return
     */
    open fun convertDimension(value: Any?): Int {
        if (value is Float) {
            return value.toInt()
        }
        return if (value is Int) {
            value
        } else 0
    }

    /**
     * Create a new reference given a key.
     *
     * @param key
     * @return
     */
    fun createConstraintReference(key: Any?): ConstraintReference {
        return ConstraintReference(this)
    }

    fun sameFixedWidth(width: Int): Boolean {
        return mParent.width.equalsFixedValue(width)
    }

    fun sameFixedHeight(height: Int): Boolean {
        return mParent.height.equalsFixedValue(height)
    }

    fun width(dimension: Dimension): State {
        return setWidth(dimension)
    }

    fun height(dimension: Dimension): State {
        return setHeight(dimension)
    }

    fun setWidth(dimension: Dimension): State {
        mParent.width = dimension
        return this
    }

    fun setHeight(dimension: Dimension): State {
        mParent.height = dimension
        return this
    }

    fun reference(key: Any?): Reference? {
        return mReferences[key]
    }

    fun constraints(key: Any?): ConstraintReference? {
        var reference = mReferences[key]
        if (reference == null) {
            reference = createConstraintReference(key)
            mReferences[key] = reference
            reference.key = key
        }
        return if (reference is ConstraintReference) {
            reference
        } else null
    }

    private var numHelpers = 0
    private fun createHelperKey(): String {
        return "__HELPER_KEY_" + numHelpers++ + "__"
    }

    fun helper(key: Any?, type: Helper): HelperReference {
        var key = key
        if (key == null) {
            key = createHelperKey()
        }
        var reference = mHelperReferences[key]
        if (reference == null) {
            reference = when (type) {
                Helper.HORIZONTAL_CHAIN -> {
                    HorizontalChainReference(this)
                }
                Helper.VERTICAL_CHAIN -> {
                    VerticalChainReference(this)
                }
                Helper.ALIGN_HORIZONTALLY -> {
                    AlignHorizontallyReference(this)
                }
                Helper.ALIGN_VERTICALLY -> {
                    AlignVerticallyReference(this)
                }
                Helper.BARRIER -> {
                    BarrierReference(this)
                }
                else -> {
                    HelperReference(this, type)
                }
            }
            reference.key = key
            mHelperReferences[key] = reference
        }
        return reference
    }

    fun horizontalGuideline(key: Any?): GuidelineReference {
        return guideline(key, ConstraintWidget.HORIZONTAL)
    }

    fun verticalGuideline(key: Any?): GuidelineReference {
        return guideline(key, ConstraintWidget.VERTICAL)
    }

    fun guideline(key: Any?, orientation: Int): GuidelineReference {
        val reference = constraints(key)
        if (reference!!.facade == null || reference.facade !is GuidelineReference) {
            val guidelineReference = GuidelineReference(this)
            guidelineReference.orientation = orientation
            guidelineReference.key = key
            reference.facade = guidelineReference
        }
        return reference.facade as GuidelineReference
    }

    fun barrier(key: Any?, direction: Direction?): BarrierReference {
        val reference = constraints(key)
        if (reference!!.facade == null || reference.facade !is BarrierReference) {
            val barrierReference = BarrierReference(this)
            barrierReference.setBarrierDirection(direction)
            reference.facade = barrierReference
        }
        return reference.facade as BarrierReference
    }

    fun verticalChain(): VerticalChainReference {
        return helper(null, Helper.VERTICAL_CHAIN) as VerticalChainReference
    }

    fun verticalChain(vararg references: Any): VerticalChainReference {
        val reference = helper(null, Helper.VERTICAL_CHAIN) as VerticalChainReference
        reference.add(*references)
        return reference
    }

    fun horizontalChain(): HorizontalChainReference {
        return helper(null, Helper.HORIZONTAL_CHAIN) as HorizontalChainReference
    }

    fun horizontalChain(vararg references: Any): HorizontalChainReference {
        val reference = helper(null, Helper.HORIZONTAL_CHAIN) as HorizontalChainReference
        reference.add(*references)
        return reference
    }

    fun centerHorizontally(vararg references: Any): AlignHorizontallyReference {
        val reference = helper(null, Helper.ALIGN_HORIZONTALLY) as AlignHorizontallyReference
        reference.add(*references)
        return reference
    }

    fun centerVertically(vararg references: Any): AlignVerticallyReference {
        val reference = helper(null, Helper.ALIGN_VERTICALLY) as AlignVerticallyReference
        reference.add(*references)
        return reference
    }

    fun directMapping() {
        for (key in mReferences.keys) {
            val ref = constraints(key) ?: continue
            ref.view = key
        }
    }

    fun map(key: Any?, view: Any?) {
        val ref: Reference? = constraints(key)
        if (ref is ConstraintReference) {
            ref.view = view
        }
    }

    fun setTag(key: String, tag: String) {
        val ref: Reference? = constraints(key)
        if (ref is ConstraintReference) {
            ref.tag = tag
            var list: ArrayList<String>? = null
            if (!mTags.containsKey(tag)) {
                list = ArrayList()
                mTags[tag] = list
            } else {
                list = mTags[tag]
            }
            list!!.add(key)
        }
    }

    fun getIdsForTag(tag: String): ArrayList<String>? {
        return if (mTags.containsKey(tag)) {
            mTags[tag]
        } else null
    }

    fun apply(container: ConstraintWidgetContainer) {
        container.removeAllChildren()
        mParent.width.apply(this, container, ConstraintWidget.HORIZONTAL)
        mParent.height.apply(this, container, ConstraintWidget.VERTICAL)
        for (key in mHelperReferences.keys) {
            val reference = mHelperReferences[key]
            val helperWidget = reference!!.helperWidget
            if (helperWidget != null) {
                var constraintReference = mReferences[key]
                if (constraintReference == null) {
                    constraintReference = constraints(key)
                }
                constraintReference!!.constraintWidget = helperWidget
            }
        }
        for (key in mReferences.keys) {
            val reference = mReferences[key]
            if (reference !== mParent && reference!!.facade is HelperReference) {
                val helperWidget = (reference.facade as HelperReference).helperWidget
                if (helperWidget != null) {
                    var constraintReference = mReferences[key]
                    if (constraintReference == null) {
                        constraintReference = constraints(key)
                    }
                    constraintReference!!.constraintWidget = helperWidget
                }
            }
        }
        for (key in mReferences.keys) {
            val reference = mReferences[key]
            if (reference !== mParent) {
                val widget = reference!!.constraintWidget ?: continue
                widget.debugName = reference.key.toString()
                widget.parent = null
                if (reference.facade is GuidelineReference) {
                    // we apply Guidelines first to correctly setup their ConstraintWidget.
                    reference.apply()
                }
                container.add(widget)
            } else {
                reference.constraintWidget = container
            }
        }
        for (key in mHelperReferences.keys) {
            val reference = mHelperReferences[key]
            val helperWidget = reference!!.helperWidget
            if (helperWidget != null) {
                for (keyRef in reference.mReferences) {
                    val constraintReference = mReferences[keyRef]
                    reference.helperWidget?.add(constraintReference!!.constraintWidget)
                }
                reference.apply()
            } else {
                reference.apply()
            }
        }
        for (key in mReferences.keys) {
            val reference = mReferences[key]
            if (reference !== mParent && reference!!.facade is HelperReference) {
                val helperReference = reference.facade as HelperReference
                val helperWidget = helperReference.helperWidget
                if (helperWidget != null) {
                    for (keyRef in helperReference.mReferences) {
                        val constraintReference = mReferences[keyRef]
                        if (constraintReference != null) {
                            helperWidget.add(constraintReference.constraintWidget)
                        } else if (keyRef is Reference) {
                            helperWidget.add(keyRef.constraintWidget)
                        } else {
                            println("couldn't find reference for $keyRef")
                        }
                    }
                    reference.apply()
                }
            }
        }
        for (key in mReferences.keys) {
            val reference = mReferences[key]
            reference!!.apply()
            val widget = reference.constraintWidget
            if (widget != null && key != null) {
                widget.stringId = key.toString()
            }
        }
    }

    companion object {
        const val UNKNOWN = -1
        const val CONSTRAINT_SPREAD = 0
        const val CONSTRAINT_WRAP = 1
        const val CONSTRAINT_RATIO = 2
        const val PARENT = 0
    }

    init {
        mReferences[PARENT] = mParent
    }
}