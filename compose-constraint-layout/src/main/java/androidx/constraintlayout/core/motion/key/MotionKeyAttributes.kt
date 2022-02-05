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
package androidx.constraintlayout.core.motion.key

import androidx.constraintlayout.core.motion.utils.SplineSet
import androidx.constraintlayout.core.motion.utils.SplineSet.CustomSpline
import androidx.constraintlayout.core.motion.utils.TypedValues.*
import androidx.constraintlayout.core.motion.utils.TypedValues.AttributesType.S_CUSTOM

class MotionKeyAttributes : MotionKey() {
    private var mTransitionEasing: String? = null
    var curveFit = -1
        private set
    private var mVisibility = 0
    private var mAlpha = Float.NaN
    private var mElevation = Float.NaN
    private var mRotation = Float.NaN
    private var mRotationX = Float.NaN
    private var mRotationY = Float.NaN
    private var mPivotX = Float.NaN
    private var mPivotY = Float.NaN
    private var mTransitionPathRotate = Float.NaN
    private var mScaleX = Float.NaN
    private var mScaleY = Float.NaN
    private var mTranslationX = Float.NaN
    private var mTranslationY = Float.NaN
    private var mTranslationZ = Float.NaN
    private var mProgress = Float.NaN
    override fun getAttributeNames(attributes: HashSet<String>) {
        if (!java.lang.Float.isNaN(mAlpha)) {
            attributes.add(AttributesType.S_ALPHA)
        }
        if (!java.lang.Float.isNaN(mElevation)) {
            attributes.add(AttributesType.S_ELEVATION)
        }
        if (!java.lang.Float.isNaN(mRotation)) {
            attributes.add(AttributesType.S_ROTATION_Z)
        }
        if (!java.lang.Float.isNaN(mRotationX)) {
            attributes.add(AttributesType.S_ROTATION_X)
        }
        if (!java.lang.Float.isNaN(mRotationY)) {
            attributes.add(AttributesType.S_ROTATION_Y)
        }
        if (!java.lang.Float.isNaN(mPivotX)) {
            attributes.add(AttributesType.S_PIVOT_X)
        }
        if (!java.lang.Float.isNaN(mPivotY)) {
            attributes.add(AttributesType.S_PIVOT_Y)
        }
        if (!java.lang.Float.isNaN(mTranslationX)) {
            attributes.add(AttributesType.S_TRANSLATION_X)
        }
        if (!java.lang.Float.isNaN(mTranslationY)) {
            attributes.add(AttributesType.S_TRANSLATION_Y)
        }
        if (!java.lang.Float.isNaN(mTranslationZ)) {
            attributes.add(AttributesType.S_TRANSLATION_Z)
        }
        if (!java.lang.Float.isNaN(mTransitionPathRotate)) {
            attributes.add(AttributesType.S_PATH_ROTATE)
        }
        if (!java.lang.Float.isNaN(mScaleX)) {
            attributes.add(AttributesType.S_SCALE_X)
        }
        if (!java.lang.Float.isNaN(mScaleY)) {
            attributes.add(AttributesType.S_SCALE_Y)
        }
        if (!java.lang.Float.isNaN(mProgress)) {
            attributes.add(AttributesType.S_PROGRESS)
        }
        if (mCustom!!.size > 0) {
            for (s in mCustom!!.keys) {
                attributes.add(S_CUSTOM + "," + s)
            }
        }
    }

    override fun addValues(splines: HashMap<String, SplineSet?>) {
        for (s in splines.keys) {
            val splineSet = splines[s] ?: continue
            // TODO support custom
            if (s.startsWith(AttributesType.S_CUSTOM)) {
                val cKey = s.substring(AttributesType.S_CUSTOM.length + 1)
                val cValue = mCustom!![cKey]
                if (cValue != null) {
                    (splineSet as CustomSpline).setPoint(framePosition, cValue)
                }
                continue
            }
            when (s) {
                AttributesType.S_ALPHA -> if (!mAlpha.isNaN()) {
                    splineSet.setPoint(framePosition, mAlpha)
                }
                AttributesType.S_ELEVATION -> if (!mElevation.isNaN()) {
                    splineSet.setPoint(framePosition, mElevation)
                }
                AttributesType.S_ROTATION_Z -> if (!mRotation.isNaN()) {
                    splineSet.setPoint(framePosition, mRotation)
                }
                AttributesType.S_ROTATION_X -> if (!mRotationX.isNaN()) {
                    splineSet.setPoint(framePosition, mRotationX)
                }
                AttributesType.S_ROTATION_Y -> if (!mRotationY.isNaN()) {
                    splineSet.setPoint(framePosition, mRotationY)
                }
                AttributesType.S_PIVOT_X -> if (!mRotationX.isNaN()) {
                    splineSet.setPoint(framePosition, mPivotX)
                }
                AttributesType.S_PIVOT_Y -> if (!mRotationY.isNaN()) {
                    splineSet.setPoint(framePosition, mPivotY)
                }
                AttributesType.S_PATH_ROTATE -> if (!mTransitionPathRotate.isNaN()) {
                    splineSet.setPoint(framePosition, mTransitionPathRotate)
                }
                AttributesType.S_SCALE_X -> if (!mScaleX.isNaN()) {
                    splineSet.setPoint(framePosition, mScaleX)
                }
                AttributesType.S_SCALE_Y -> if (!mScaleY.isNaN()) {
                    splineSet.setPoint(framePosition, mScaleY)
                }
                AttributesType.S_TRANSLATION_X -> if (!mTranslationX.isNaN()) {
                    splineSet.setPoint(framePosition, mTranslationX)
                }
                AttributesType.S_TRANSLATION_Y -> if (!mTranslationY.isNaN()) {
                    splineSet.setPoint(framePosition, mTranslationY)
                }
                AttributesType.S_TRANSLATION_Z -> if (!mTranslationZ.isNaN()) {
                    splineSet.setPoint(framePosition, mTranslationZ)
                }
                AttributesType.S_PROGRESS -> if (!mProgress.isNaN()) {
                    splineSet.setPoint(framePosition, mProgress)
                }
                else -> System.err.println("not supported by KeyAttributes $s")
            }
        }
    }

    override fun clone(): MotionKey? {
        return null
    }

    override fun setValue(type: Int, value: Int): Boolean {
        when (type) {
            AttributesType.TYPE_VISIBILITY -> mVisibility = value
            AttributesType.TYPE_CURVE_FIT -> curveFit = value
            TYPE_FRAME_POSITION -> framePosition = value
            else -> if (!setValue(type, value)) {
                return super.setValue(type, value)
            }
        }
        return true
    }

    override fun setValue(type: Int, value: Float): Boolean {
        when (type) {
            AttributesType.TYPE_ALPHA -> mAlpha = value
            AttributesType.TYPE_TRANSLATION_X -> mTranslationX = value
            AttributesType.TYPE_TRANSLATION_Y -> mTranslationY = value
            AttributesType.TYPE_TRANSLATION_Z -> mTranslationZ = value
            AttributesType.TYPE_ELEVATION -> mElevation = value
            AttributesType.TYPE_ROTATION_X -> mRotationX = value
            AttributesType.TYPE_ROTATION_Y -> mRotationY = value
            AttributesType.TYPE_ROTATION_Z -> mRotation = value
            AttributesType.TYPE_SCALE_X -> mScaleX = value
            AttributesType.TYPE_SCALE_Y -> mScaleY = value
            AttributesType.TYPE_PIVOT_X -> mPivotX = value
            AttributesType.TYPE_PIVOT_Y -> mPivotY = value
            AttributesType.TYPE_PROGRESS -> mProgress = value
            AttributesType.TYPE_PATH_ROTATE -> mTransitionPathRotate = value
            TYPE_FRAME_POSITION -> mTransitionPathRotate = value
            else -> return super.setValue(type, value)
        }
        return true
    }

    override fun setInterpolation(interpolation: HashMap<String?, Int?>) {
        if (!java.lang.Float.isNaN(mAlpha)) {
            interpolation[AttributesType.S_ALPHA] = curveFit
        }
        if (!java.lang.Float.isNaN(mElevation)) {
            interpolation[AttributesType.S_ELEVATION] = curveFit
        }
        if (!java.lang.Float.isNaN(mRotation)) {
            interpolation[AttributesType.S_ROTATION_Z] = curveFit
        }
        if (!java.lang.Float.isNaN(mRotationX)) {
            interpolation[AttributesType.S_ROTATION_X] = curveFit
        }
        if (!java.lang.Float.isNaN(mRotationY)) {
            interpolation[AttributesType.S_ROTATION_Y] = curveFit
        }
        if (!java.lang.Float.isNaN(mPivotX)) {
            interpolation[AttributesType.S_PIVOT_X] = curveFit
        }
        if (!java.lang.Float.isNaN(mPivotY)) {
            interpolation[AttributesType.S_PIVOT_Y] = curveFit
        }
        if (!java.lang.Float.isNaN(mTranslationX)) {
            interpolation[AttributesType.S_TRANSLATION_X] = curveFit
        }
        if (!java.lang.Float.isNaN(mTranslationY)) {
            interpolation[AttributesType.S_TRANSLATION_Y] = curveFit
        }
        if (!java.lang.Float.isNaN(mTranslationZ)) {
            interpolation[AttributesType.S_TRANSLATION_Z] = curveFit
        }
        if (!java.lang.Float.isNaN(mTransitionPathRotate)) {
            interpolation[AttributesType.S_PATH_ROTATE] = curveFit
        }
        if (!java.lang.Float.isNaN(mScaleX)) {
            interpolation[AttributesType.S_SCALE_X] = curveFit
        }
        if (!java.lang.Float.isNaN(mScaleY)) {
            interpolation[AttributesType.S_SCALE_Y] = curveFit
        }
        if (!java.lang.Float.isNaN(mProgress)) {
            interpolation[AttributesType.S_PROGRESS] = curveFit
        }
        if (mCustom!!.size > 0) {
            for (s in mCustom!!.keys) {
                interpolation[AttributesType.S_CUSTOM + "," + s] = curveFit
            }
        }
    }

    override fun setValue(type: Int, value: String): Boolean {
        when (type) {
            AttributesType.TYPE_EASING -> mTransitionEasing = value
            TYPE_TARGET -> mTargetString = value
            else -> return super.setValue(type, value)
        }
        return true
    }

    override fun getId(name: String): Int {
        return AttributesType.getId(name)
    }

    fun printAttributes() {
        val nameSet = HashSet<String>()
        getAttributeNames(nameSet)
        println(" ------------- $framePosition -------------")
        val names = nameSet.toTypedArray()
        for (i in names.indices) {
            val id = AttributesType.getId(names[i])
            println(names[i] + ":" + getFloatValue(id))
        }
    }

    private fun getFloatValue(id: Int): Float {
        return when (id) {
            AttributesType.TYPE_ALPHA -> mAlpha
            AttributesType.TYPE_TRANSLATION_X -> mTranslationX
            AttributesType.TYPE_TRANSLATION_Y -> mTranslationY
            AttributesType.TYPE_TRANSLATION_Z -> mTranslationZ
            AttributesType.TYPE_ELEVATION -> mElevation
            AttributesType.TYPE_ROTATION_X -> mRotationX
            AttributesType.TYPE_ROTATION_Y -> mRotationY
            AttributesType.TYPE_ROTATION_Z -> mRotation
            AttributesType.TYPE_SCALE_X -> mScaleX
            AttributesType.TYPE_SCALE_Y -> mScaleY
            AttributesType.TYPE_PIVOT_X -> mPivotX
            AttributesType.TYPE_PIVOT_Y -> mPivotY
            AttributesType.TYPE_PROGRESS -> mProgress
            AttributesType.TYPE_PATH_ROTATE -> mTransitionPathRotate
            TYPE_FRAME_POSITION -> framePosition.toFloat()
            else -> Float.NaN
        }
    }

    companion object {
        const val NAME = "KeyAttribute"
        private const val TAG = "KeyAttributes"
        private const val DEBUG = false
        const val KEY_TYPE = 1
    }

    init {
        mType = KEY_TYPE
        mCustom = HashMap()
    }
}