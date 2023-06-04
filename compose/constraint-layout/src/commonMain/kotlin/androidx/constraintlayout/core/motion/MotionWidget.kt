/*
 * Copyright (C) 2021 The Android Open Source Project
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
package androidx.constraintlayout.core.motion

import androidx.constraintlayout.core.motion.utils.TypedValues
import androidx.constraintlayout.core.motion.utils.TypedValues.AttributesType
import androidx.constraintlayout.core.motion.utils.TypedValues.MotionType
import androidx.constraintlayout.core.state.WidgetFrame
import androidx.constraintlayout.core.widgets.ConstraintWidget

class MotionWidget : TypedValues {
    var widgetFrame: WidgetFrame? = WidgetFrame()
    var motion = Motion()
    var propertySet = PropertySet()
    private var mProgress = 0f
    var mTransitionPathRotate = 0f

    /**
     * @suppress
     */
    class Motion {
        var mAnimateRelativeTo = UNSET
        var mAnimateCircleAngleTo = 0
        var mTransitionEasing: String? = null
        var mPathMotionArc = UNSET
        var mDrawPath = 0
        var mMotionStagger = Float.NaN
        var mPolarRelativeTo = UNSET
        var mPathRotate = Float.NaN
        var mQuantizeMotionPhase = Float.NaN
        var mQuantizeMotionSteps = UNSET
        var mQuantizeInterpolatorString: String? = null
        var mQuantizeInterpolatorType = INTERPOLATOR_UNDEFINED // undefined
        var mQuantizeInterpolatorID = -1

        companion object {
            private const val INTERPOLATOR_REFERENCE_ID = -2
            private const val SPLINE_STRING = -1
            private const val INTERPOLATOR_UNDEFINED = -3
        }
    }

    class PropertySet {
        var visibility = VISIBLE
        var mVisibilityMode = VISIBILITY_MODE_NORMAL
        var alpha = 1f
        var mProgress = Float.NaN
    }

    constructor() {}

    val parent: MotionWidget?
        get() = null

    fun findViewById(mTransformPivotTarget: Int): MotionWidget? {
        return null
    }

    fun layout(l: Int, t: Int, r: Int, b: Int) {
        setBounds(l, t, r, b)
    }

    override fun toString(): String {
        return widgetFrame?.left.toString() + ", " + widgetFrame?.top + ", " + widgetFrame!!.right + ", " + widgetFrame!!.bottom
    }

    fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {
        if (widgetFrame == null) {
            widgetFrame = WidgetFrame(null as ConstraintWidget?)
        }
        widgetFrame?.top = top
        widgetFrame?.left = left
        widgetFrame?.right = right
        widgetFrame?.bottom = bottom
    }

    constructor(f: WidgetFrame?) {
        widgetFrame = f
    }

    override fun setValue(id: Int, value: Int): Boolean {
        return setValueAttributes(id, value.toFloat())
    }

    override fun setValue(id: Int, value: Float): Boolean {
        val set = setValueAttributes(id, value)
        return if (set) {
            true
        } else setValueMotion(id, value)
    }

    override fun setValue(id: Int, value: String?): Boolean {
        return setValueMotion(id, value)
    }

    override fun setValue(id: Int, value: Boolean): Boolean {
        return false
    }

    fun setValueMotion(id: Int, value: Int): Boolean {
        when (id) {
            MotionType.TYPE_ANIMATE_RELATIVE_TO -> motion.mAnimateRelativeTo = value
            MotionType.TYPE_ANIMATE_CIRCLEANGLE_TO -> motion.mAnimateCircleAngleTo = value
            MotionType.TYPE_PATHMOTION_ARC -> motion.mPathMotionArc = value
            MotionType.TYPE_DRAW_PATH -> motion.mDrawPath = value
            MotionType.TYPE_POLAR_RELATIVETO -> motion.mPolarRelativeTo = value
            MotionType.TYPE_QUANTIZE_MOTIONSTEPS -> motion.mQuantizeMotionSteps = value
            MotionType.TYPE_QUANTIZE_INTERPOLATOR_TYPE -> motion.mQuantizeInterpolatorType = value
            MotionType.TYPE_QUANTIZE_INTERPOLATOR_ID -> motion.mQuantizeInterpolatorID = value
            else -> return false
        }
        return true
    }

    fun setValueMotion(id: Int, value: String?): Boolean {
        when (id) {
            MotionType.TYPE_EASING -> motion.mTransitionEasing = value
            MotionType.TYPE_QUANTIZE_INTERPOLATOR -> motion.mQuantizeInterpolatorString = value
            else -> return false
        }
        return true
    }

    fun setValueMotion(id: Int, value: Float): Boolean {
        when (id) {
            MotionType.TYPE_STAGGER -> motion.mMotionStagger = value
            MotionType.TYPE_PATH_ROTATE -> motion.mPathRotate = value
            MotionType.TYPE_QUANTIZE_MOTION_PHASE -> motion.mQuantizeMotionPhase = value
            else -> return false
        }
        return true
    }

    /**
     * Sets the attributes
     *
     * @param id
     * @param value
     */
    fun setValueAttributes(id: Int, value: Float): Boolean {
        when (id) {
            AttributesType.TYPE_ALPHA -> widgetFrame!!.alpha = value
            AttributesType.TYPE_TRANSLATION_X -> widgetFrame!!.translationX = value
            AttributesType.TYPE_TRANSLATION_Y -> widgetFrame!!.translationY = value
            AttributesType.TYPE_TRANSLATION_Z -> widgetFrame!!.translationZ = value
            AttributesType.TYPE_ROTATION_X -> widgetFrame!!.rotationX = value
            AttributesType.TYPE_ROTATION_Y -> widgetFrame!!.rotationY = value
            AttributesType.TYPE_ROTATION_Z -> widgetFrame!!.rotationZ = value
            AttributesType.TYPE_SCALE_X -> widgetFrame!!.scaleX = value
            AttributesType.TYPE_SCALE_Y -> widgetFrame!!.scaleY = value
            AttributesType.TYPE_PIVOT_X -> widgetFrame!!.pivotX = value
            AttributesType.TYPE_PIVOT_Y -> widgetFrame!!.pivotY = value
            AttributesType.TYPE_PROGRESS -> mProgress = value
            AttributesType.TYPE_PATH_ROTATE -> mTransitionPathRotate = value
            else -> return false
        }
        return true
    }

    /**
     * Sets the attributes
     *
     * @param id
     */
    fun getValueAttributes(id: Int): Float {
        return when (id) {
            AttributesType.TYPE_ALPHA -> widgetFrame!!.alpha
            AttributesType.TYPE_TRANSLATION_X -> widgetFrame!!.translationX
            AttributesType.TYPE_TRANSLATION_Y -> widgetFrame!!.translationY
            AttributesType.TYPE_TRANSLATION_Z -> widgetFrame!!.translationZ
            AttributesType.TYPE_ROTATION_X -> widgetFrame!!.rotationX
            AttributesType.TYPE_ROTATION_Y -> widgetFrame!!.rotationY
            AttributesType.TYPE_ROTATION_Z -> widgetFrame!!.rotationZ
            AttributesType.TYPE_SCALE_X -> widgetFrame!!.scaleX
            AttributesType.TYPE_SCALE_Y -> widgetFrame!!.scaleY
            AttributesType.TYPE_PIVOT_X -> widgetFrame!!.pivotX
            AttributesType.TYPE_PIVOT_Y -> widgetFrame!!.pivotY
            AttributesType.TYPE_PROGRESS -> mProgress
            AttributesType.TYPE_PATH_ROTATE -> mTransitionPathRotate
            else -> Float.NaN
        }
    }

    override fun getId(name: String?): Int {
        val ret = AttributesType.getId(name)
        return if (ret != -1) {
            ret
        } else MotionType.getId(name)
    }

    val top: Int
        get() = widgetFrame!!.top
    val left: Int
        get() = widgetFrame!!.left
    val bottom: Int
        get() = widgetFrame!!.bottom
    val right: Int
        get() = widgetFrame!!.right
    var rotationX: Float
        get() = widgetFrame!!.rotationX
        set(rotationX) {
            widgetFrame!!.rotationX = rotationX
        }
    var rotationY: Float
        get() = widgetFrame!!.rotationY
        set(rotationY) {
            widgetFrame!!.rotationY = rotationY
        }
    var rotationZ: Float
        get() = widgetFrame!!.rotationZ
        set(rotationZ) {
            widgetFrame!!.rotationZ = rotationZ
        }
    var translationX: Float
        get() = widgetFrame!!.translationX
        set(translationX) {
            widgetFrame!!.translationX = translationX
        }
    var translationY: Float
        get() = widgetFrame!!.translationY
        set(translationY) {
            widgetFrame!!.translationY = translationY
        }
    var translationZ: Float
        get() = widgetFrame!!.translationZ
        set(tz) {
            widgetFrame!!.translationZ = tz
        }
    var scaleX: Float
        get() = widgetFrame!!.scaleX
        set(scaleX) {
            widgetFrame!!.scaleX = scaleX
        }
    var scaleY: Float
        get() = widgetFrame!!.scaleY
        set(scaleY) {
            widgetFrame!!.scaleY = scaleY
        }
    var visibility: Int
        get() = propertySet.visibility
        set(visibility) {
            propertySet.visibility = visibility
        }
    var pivotX: Float
        get() = widgetFrame!!.pivotX
        set(px) {
            widgetFrame!!.pivotX = px
        }
    var pivotY: Float
        get() = widgetFrame!!.pivotY
        set(py) {
            widgetFrame!!.pivotY = py
        }
    val alpha: Float
        get() = propertySet.alpha
    val width: Int
        get() = widgetFrame!!.right - widgetFrame!!.left
    val height: Int
        get() = widgetFrame!!.bottom - widgetFrame!!.top
    val customAttributeNames: Set<String>
        get() = widgetFrame!!.customAttributeNames

    fun setCustomAttribute(name: String?, type: Int, value: Float) {
        widgetFrame!!.setCustomAttribute(name!!, type, value)
    }

    fun setCustomAttribute(name: String?, type: Int, value: Int) {
        widgetFrame!!.setCustomAttribute(name!!, type, value)
    }

    fun setCustomAttribute(name: String?, type: Int, value: Boolean) {
        widgetFrame!!.setCustomAttribute(name!!, type, value)
    }

    fun setCustomAttribute(name: String?, type: Int, value: String?) {
        widgetFrame!!.setCustomAttribute(name!!, type, value)
    }

    fun getCustomAttribute(name: String?): CustomVariable? {
        return widgetFrame!!.getCustomAttribute(name!!)
    }

    fun setInterpolatedValue(attribute: CustomAttribute, mCache: FloatArray) {
        widgetFrame!!.setCustomAttribute(attribute.mName, TypedValues.Custom.TYPE_FLOAT, mCache[0])
    }

    companion object {
        const val VISIBILITY_MODE_NORMAL = 0
        const val VISIBILITY_MODE_IGNORE = 1
        private const val INTERNAL_MATCH_PARENT = -1
        private const val INTERNAL_WRAP_CONTENT = -2
        const val INVISIBLE = 0
        const val VISIBLE = 4
        private const val INTERNAL_MATCH_CONSTRAINT = -3
        private const val INTERNAL_WRAP_CONTENT_CONSTRAINED = -4
        const val ROTATE_NONE = 0
        const val ROTATE_PORTRATE_OF_RIGHT = 1
        const val ROTATE_PORTRATE_OF_LEFT = 2
        const val ROTATE_RIGHT_OF_PORTRATE = 3
        const val ROTATE_LEFT_OF_PORTRATE = 4
        const val UNSET = -1
        const val MATCH_CONSTRAINT = 0
        const val PARENT_ID = 0
        const val FILL_PARENT = -1
        const val MATCH_PARENT = -1
        const val WRAP_CONTENT = -2
        const val GONE_UNSET = Int.MIN_VALUE
        const val MATCH_CONSTRAINT_WRAP = ConstraintWidget.MATCH_CONSTRAINT_WRAP
    }
}