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

import androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet.CustomVarSet
import androidx.constraintlayout.core.motion.utils.*
import androidx.constraintlayout.core.motion.utils.TypedValues.*

class MotionKeyTimeCycle : MotionKey() {
    private var mTransitionEasing: String? = null
    private var mCurveFit = -1
    private var mAlpha = Float.NaN
    private var mElevation = Float.NaN
    private var mRotation = Float.NaN
    private var mRotationX = Float.NaN
    private var mRotationY = Float.NaN
    private var mTransitionPathRotate = Float.NaN
    private var mScaleX = Float.NaN
    private var mScaleY = Float.NaN
    private var mTranslationX = Float.NaN
    private var mTranslationY = Float.NaN
    private var mTranslationZ = Float.NaN
    private var mProgress = Float.NaN
    private var mWaveShape = 0
    private var mCustomWaveShape: String? = null // TODO add support of custom wave shapes in KeyTimeCycle
    private var mWavePeriod = Float.NaN
    private var mWaveOffset = 0f
    fun addTimeValues(splines: HashMap<String, TimeCycleSplineSet?>) {
        for (s in splines.keys) {
            val splineSet = splines[s] ?: continue
            if (s.startsWith(MotionKey.Companion.CUSTOM)) {
                val cKey: String = s.substring(MotionKey.Companion.CUSTOM.length + 1)
                val cValue = mCustom!![cKey]
                if (cValue != null) {
                    (splineSet as CustomVarSet).setPoint(framePosition, cValue, mWavePeriod, mWaveShape, mWaveOffset)
                }
                continue
            }
            when (s) {
                AttributesType.S_ALPHA -> if (!java.lang.Float.isNaN(mAlpha)) {
                    splineSet.setPoint(framePosition, mAlpha, mWavePeriod, mWaveShape, mWaveOffset)
                }
                AttributesType.S_ROTATION_X -> if (!java.lang.Float.isNaN(mRotationX)) {
                    splineSet.setPoint(framePosition, mRotationX, mWavePeriod, mWaveShape, mWaveOffset)
                }
                AttributesType.S_ROTATION_Y -> if (!java.lang.Float.isNaN(mRotationY)) {
                    splineSet.setPoint(framePosition, mRotationY, mWavePeriod, mWaveShape, mWaveOffset)
                }
                AttributesType.S_ROTATION_Z -> if (!java.lang.Float.isNaN(mRotation)) {
                    splineSet.setPoint(framePosition, mRotation, mWavePeriod, mWaveShape, mWaveOffset)
                }
                AttributesType.S_PATH_ROTATE -> if (!java.lang.Float.isNaN(mTransitionPathRotate)) {
                    splineSet.setPoint(framePosition, mTransitionPathRotate, mWavePeriod, mWaveShape, mWaveOffset)
                }
                AttributesType.S_SCALE_X -> if (!java.lang.Float.isNaN(mScaleX)) {
                    splineSet.setPoint(framePosition, mScaleX, mWavePeriod, mWaveShape, mWaveOffset)
                }
                AttributesType.S_SCALE_Y -> if (!java.lang.Float.isNaN(mScaleY)) {
                    splineSet.setPoint(framePosition, mScaleY, mWavePeriod, mWaveShape, mWaveOffset)
                }
                AttributesType.S_TRANSLATION_X -> if (!java.lang.Float.isNaN(mTranslationX)) {
                    splineSet.setPoint(framePosition, mTranslationX, mWavePeriod, mWaveShape, mWaveOffset)
                }
                AttributesType.S_TRANSLATION_Y -> if (!java.lang.Float.isNaN(mTranslationY)) {
                    splineSet.setPoint(framePosition, mTranslationY, mWavePeriod, mWaveShape, mWaveOffset)
                }
                AttributesType.S_TRANSLATION_Z -> if (!java.lang.Float.isNaN(mTranslationZ)) {
                    splineSet.setPoint(framePosition, mTranslationZ, mWavePeriod, mWaveShape, mWaveOffset)
                }
                AttributesType.S_ELEVATION -> if (!java.lang.Float.isNaN(mTranslationZ)) {
                    splineSet.setPoint(framePosition, mTranslationZ, mWavePeriod, mWaveShape, mWaveOffset)
                }
                AttributesType.S_PROGRESS -> if (!java.lang.Float.isNaN(mProgress)) {
                    splineSet.setPoint(framePosition, mProgress, mWavePeriod, mWaveShape, mWaveOffset)
                }
                else -> Utils.loge("KeyTimeCycles", "UNKNOWN addValues \"$s\"")
            }
        }
    }

    override fun addValues(splines: HashMap<String, SplineSet?>) {}
    override fun setValue(type: Int, value: Int): Boolean {
        when (type) {
            TYPE_FRAME_POSITION -> framePosition = value
            CycleType.TYPE_WAVE_SHAPE -> mWaveShape = value
            else -> return super.setValue(type, value)
        }
        return true
    }

    override fun setValue(type: Int, value: Float): Boolean {
        when (type) {
            CycleType.TYPE_ALPHA -> mAlpha = value
            CycleType.TYPE_CURVE_FIT -> mCurveFit = toInt(value)
            CycleType.TYPE_ELEVATION -> mElevation = toFloat(value)
            CycleType.TYPE_PROGRESS -> mProgress = toFloat(value)
            CycleType.TYPE_ROTATION_Z -> mRotation = toFloat(value)
            CycleType.TYPE_ROTATION_X -> mRotationX = toFloat(value)
            CycleType.TYPE_ROTATION_Y -> mRotationY = toFloat(value)
            CycleType.TYPE_SCALE_X -> mScaleX = toFloat(value)
            CycleType.TYPE_SCALE_Y -> mScaleY = toFloat(value)
            CycleType.TYPE_PATH_ROTATE -> mTransitionPathRotate = toFloat(value)
            CycleType.TYPE_TRANSLATION_X -> mTranslationX = toFloat(value)
            CycleType.TYPE_TRANSLATION_Y -> mTranslationY = toFloat(value)
            CycleType.TYPE_TRANSLATION_Z -> mTranslationZ = toFloat(value)
            CycleType.TYPE_WAVE_PERIOD -> mWavePeriod = toFloat(value)
            CycleType.TYPE_WAVE_OFFSET -> mWaveOffset = toFloat(value)
            else -> return super.setValue(type, value)
        }
        return true
    }

    override fun setValue(type: Int, value: String): Boolean {
        when (type) {
            CycleType.TYPE_WAVE_SHAPE -> {
                mWaveShape = Oscillator.CUSTOM
                mCustomWaveShape = value
            }
            CycleType.TYPE_EASING -> mTransitionEasing = value
            else -> return super.setValue(type, value)
        }
        return true
    }

    override fun setValue(type: Int, value: Boolean): Boolean {
        return super.setValue(type, value)
    }

    override fun copy(src: MotionKey): MotionKeyTimeCycle {
        super.copy(src)
        val k = src as MotionKeyTimeCycle
        mTransitionEasing = k.mTransitionEasing
        mCurveFit = k.mCurveFit
        mWaveShape = k.mWaveShape
        mWavePeriod = k.mWavePeriod
        mWaveOffset = k.mWaveOffset
        mProgress = k.mProgress
        mAlpha = k.mAlpha
        mElevation = k.mElevation
        mRotation = k.mRotation
        mTransitionPathRotate = k.mTransitionPathRotate
        mRotationX = k.mRotationX
        mRotationY = k.mRotationY
        mScaleX = k.mScaleX
        mScaleY = k.mScaleY
        mTranslationX = k.mTranslationX
        mTranslationY = k.mTranslationY
        mTranslationZ = k.mTranslationZ
        return this
    }

    override fun getAttributeNames(attributes: HashSet<String>) {
        if (!java.lang.Float.isNaN(mAlpha)) {
            attributes.add(CycleType.S_ALPHA)
        }
        if (!java.lang.Float.isNaN(mElevation)) {
            attributes.add(CycleType.S_ELEVATION)
        }
        if (!java.lang.Float.isNaN(mRotation)) {
            attributes.add(CycleType.S_ROTATION_Z)
        }
        if (!java.lang.Float.isNaN(mRotationX)) {
            attributes.add(CycleType.S_ROTATION_X)
        }
        if (!java.lang.Float.isNaN(mRotationY)) {
            attributes.add(CycleType.S_ROTATION_Y)
        }
        if (!java.lang.Float.isNaN(mScaleX)) {
            attributes.add(CycleType.S_SCALE_X)
        }
        if (!java.lang.Float.isNaN(mScaleY)) {
            attributes.add(CycleType.S_SCALE_Y)
        }
        if (!java.lang.Float.isNaN(mTransitionPathRotate)) {
            attributes.add(CycleType.S_PATH_ROTATE)
        }
        if (!java.lang.Float.isNaN(mTranslationX)) {
            attributes.add(CycleType.S_TRANSLATION_X)
        }
        if (!java.lang.Float.isNaN(mTranslationY)) {
            attributes.add(CycleType.S_TRANSLATION_Y)
        }
        if (!java.lang.Float.isNaN(mTranslationZ)) {
            attributes.add(CycleType.S_TRANSLATION_Z)
        }
        if (mCustom!!.size > 0) {
            for (s in mCustom!!.keys) {
                attributes.add(S_CUSTOM + "," + s)
            }
        }
    }

    override fun clone(): MotionKey? {
        return MotionKeyTimeCycle().copy(this)
    }

    override fun getId(name: String): Int {
        return CycleType.getId(name)
    }

    companion object {
        const val NAME = "KeyTimeCycle"
        private const val TAG = NAME
        const val KEY_TYPE = 3
    }

    init {
        mType = KEY_TYPE
        mCustom = HashMap()
    }
}