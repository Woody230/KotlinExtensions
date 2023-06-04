/*
 * Copyright (C) 2018 The Android Open Source Project
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
package androidx.constraintlayout.core.motion.key

import androidx.constraintlayout.core.motion.CustomVariable
import androidx.constraintlayout.core.motion.utils.SplineSet
import androidx.constraintlayout.core.motion.utils.TypedValues

/**
 * Base class in an element in a KeyFrame
 *
 * @suppress
 */
abstract class MotionKey : TypedValues {
    /**
     * Gets the current frame position
     *
     * @return
     */
    /**
     * sets the frame position
     *
     * @param pos
     */
    var framePosition = UNSET
    var mTargetId = UNSET
    var mTargetString: String? = null
    @JvmField
    var mType = 0
    @JvmField
    var mCustom: HashMap<String, CustomVariable>? = null
    abstract fun getAttributeNames(attributes: HashSet<String>)
    fun matches(constraintTag: String?): Boolean {
        return if (mTargetString == null || constraintTag == null) false else constraintTag.matches(Regex(mTargetString ?: ""))
    }

    /**
     * Defines method to add a a view to splines derived form this key frame.
     * The values are written to the spline
     *
     * @param splines splines to write values to
     * @suppress
     */
    abstract fun addValues(splines: HashMap<String, SplineSet>)

    /**
     * Return the float given a value. If the value is a "Float" object it is casted
     *
     * @param value
     * @return
     * @suppress
     */
    fun toFloat(value: Any): Float {
        return if (value is Float) value else value.toString().toFloat()
    }

    /**
     * Return the int version of an object if the value is an Integer object it is casted.
     *
     * @param value
     * @return
     * @suppress
     */
    fun toInt(value: Any): Int {
        return if (value is Int) value else value.toString().toInt()
    }

    /**
     * Return the boolean version this object if the object is a Boolean it is casted.
     *
     * @param value
     * @return
     * @suppress
     */
    fun toBoolean(value: Any): Boolean {
        return if (value is Boolean) value else value.toString().toBoolean()
    }

    /**
     * Key frame can specify the type of interpolation it wants on various attributes
     * For each string it set it to -1, CurveFit.LINEAR or  CurveFit.SPLINE
     *
     * @param interpolation
     */
    open fun setInterpolation(interpolation: HashMap<String?, Int?>) {}
    open fun copy(src: MotionKey): MotionKey {
        framePosition = src.framePosition
        mTargetId = src.mTargetId
        mTargetString = src.mTargetString
        mType = src.mType
        return this
    }

    abstract fun clone(): MotionKey?
    fun setViewId(id: Int): MotionKey {
        mTargetId = id
        return this
    }

    override fun setValue(type: Int, value: Int): Boolean {
        when (type) {
            TypedValues.TYPE_FRAME_POSITION -> {
                framePosition = value
                return true
            }
        }
        return false
    }

    override fun setValue(type: Int, value: Float): Boolean {
        return false
    }

    override fun setValue(type: Int, value: String?): Boolean {
        when (type) {
            TypedValues.TYPE_TARGET -> {
                mTargetString = value
                return true
            }
        }
        return false
    }

    override fun setValue(type: Int, value: Boolean): Boolean {
        return false
    }

    fun setCustomAttribute(name: String, type: Int, value: Float) {
        mCustom!![name] = CustomVariable(name, type, value)
    }

    fun setCustomAttribute(name: String, type: Int, value: Int) {
        mCustom!![name] = CustomVariable(name, type, value)
    }

    fun setCustomAttribute(name: String, type: Int, value: Boolean) {
        mCustom!![name] = CustomVariable(name, type, value)
    }

    fun setCustomAttribute(name: String, type: Int, value: String?) {
        mCustom!![name] = CustomVariable(name, type, value)
    }

    companion object {
        @JvmField
        var UNSET = -1
        const val ALPHA = "alpha"
        const val ELEVATION = "elevation"
        const val ROTATION = "rotationZ"
        const val ROTATION_X = "rotationX"
        const val TRANSITION_PATH_ROTATE = "transitionPathRotate"
        const val SCALE_X = "scaleX"
        const val SCALE_Y = "scaleY"
        const val TRANSLATION_X = "translationX"
        const val TRANSLATION_Y = "translationY"
        const val CUSTOM = "CUSTOM"
        const val VISIBILITY = "visibility"
    }
}