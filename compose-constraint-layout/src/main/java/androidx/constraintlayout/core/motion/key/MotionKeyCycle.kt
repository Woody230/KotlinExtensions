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

import androidx.constraintlayout.core.motion.utils.TypedValues.AttributesType
import androidx.constraintlayout.core.motion.utils.TypedValues.CycleType
import androidx.constraintlayout.core.motion.utils.*
import androidx.constraintlayout.core.motion.utils.TypedValues.Companion.S_CUSTOM

class MotionKeyCycle : MotionKey() {
    private var mTransitionEasing: String? = null
    private var mCurveFit = 0
    private var mWaveShape = -1
    private var mCustomWaveShape: String? = null
    private var mWavePeriod = Float.NaN
    private var mWaveOffset = 0f
    private var mWavePhase = 0f
    private var mProgress = Float.NaN
    private var mAlpha = Float.NaN
    private var mElevation = Float.NaN
    private var mRotation = Float.NaN
    private var mTransitionPathRotate = Float.NaN
    private var mRotationX = Float.NaN
    private var mRotationY = Float.NaN
    private var mScaleX = Float.NaN
    private var mScaleY = Float.NaN
    private var mTranslationX = Float.NaN
    private var mTranslationY = Float.NaN
    private var mTranslationZ = Float.NaN
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

    override fun addValues(splines: HashMap<String, SplineSet>) {}
    override fun setValue(type: Int, value: Int): Boolean {
        return when (type) {
            CycleType.TYPE_CURVE_FIT -> {
                mCurveFit = value
                true
            }
            CycleType.TYPE_WAVE_SHAPE -> {
                mWaveShape = value
                true
            }
            else -> {
                val ret = setValue(type, value.toFloat())
                if (ret) {
                    true
                } else super.setValue(type, value)
            }
        }
    }

    override fun setValue(type: Int, value: String?): Boolean {
        return when (type) {
            CycleType.TYPE_EASING -> {
                mTransitionEasing = value
                true
            }
            CycleType.TYPE_CUSTOM_WAVE_SHAPE -> {
                mCustomWaveShape = value
                true
            }
            else -> super.setValue(type, value)
        }
    }

    override fun setValue(type: Int, value: Float): Boolean {
        when (type) {
            CycleType.TYPE_ALPHA -> mAlpha = value
            CycleType.TYPE_TRANSLATION_X -> mTranslationX = value
            CycleType.TYPE_TRANSLATION_Y -> mTranslationY = value
            CycleType.TYPE_TRANSLATION_Z -> mTranslationZ = value
            CycleType.TYPE_ELEVATION -> mElevation = value
            CycleType.TYPE_ROTATION_X -> mRotationX = value
            CycleType.TYPE_ROTATION_Y -> mRotationY = value
            CycleType.TYPE_ROTATION_Z -> mRotation = value
            CycleType.TYPE_SCALE_X -> mScaleX = value
            CycleType.TYPE_SCALE_Y -> mScaleY = value
            CycleType.TYPE_PROGRESS -> mProgress = value
            CycleType.TYPE_PATH_ROTATE -> mTransitionPathRotate = value
            CycleType.TYPE_WAVE_PERIOD -> mWavePeriod = value
            CycleType.TYPE_WAVE_OFFSET -> mWaveOffset = value
            CycleType.TYPE_WAVE_PHASE -> mWavePhase = value
            else -> return super.setValue(type, value)
        }
        return true
    }

    fun getValue(key: String?): Float {
        return when (key) {
            CycleType.S_ALPHA -> mAlpha
            CycleType.S_ELEVATION -> mElevation
            CycleType.S_ROTATION_Z -> mRotation
            CycleType.S_ROTATION_X -> mRotationX
            CycleType.S_ROTATION_Y -> mRotationY
            CycleType.S_PATH_ROTATE -> mTransitionPathRotate
            CycleType.S_SCALE_X -> mScaleX
            CycleType.S_SCALE_Y -> mScaleY
            CycleType.S_TRANSLATION_X -> mTranslationX
            CycleType.S_TRANSLATION_Y -> mTranslationY
            CycleType.S_TRANSLATION_Z -> mTranslationZ
            CycleType.S_WAVE_OFFSET -> mWaveOffset
            CycleType.S_WAVE_PHASE -> mWavePhase
            CycleType.S_PROGRESS -> mProgress
            else -> Float.NaN
        }
    }

    override fun clone(): MotionKey? {
        return null
    }

    override fun getId(name: String?): Int {
        when (name) {
            CycleType.S_CURVE_FIT -> return CycleType.TYPE_CURVE_FIT
            CycleType.S_VISIBILITY -> return CycleType.TYPE_VISIBILITY
            CycleType.S_ALPHA -> return CycleType.TYPE_ALPHA
            CycleType.S_TRANSLATION_X -> return CycleType.TYPE_TRANSLATION_X
            CycleType.S_TRANSLATION_Y -> return CycleType.TYPE_TRANSLATION_Y
            CycleType.S_TRANSLATION_Z -> return CycleType.TYPE_TRANSLATION_Z
            CycleType.S_ROTATION_X -> return CycleType.TYPE_ROTATION_X
            CycleType.S_ROTATION_Y -> return CycleType.TYPE_ROTATION_Y
            CycleType.S_ROTATION_Z -> return CycleType.TYPE_ROTATION_Z
            CycleType.S_SCALE_X -> return CycleType.TYPE_SCALE_X
            CycleType.S_SCALE_Y -> return CycleType.TYPE_SCALE_Y
            CycleType.S_PIVOT_X -> return CycleType.TYPE_PIVOT_X
            CycleType.S_PIVOT_Y -> return CycleType.TYPE_PIVOT_Y
            CycleType.S_PROGRESS -> return CycleType.TYPE_PROGRESS
            CycleType.S_PATH_ROTATE -> return CycleType.TYPE_PATH_ROTATE
            CycleType.S_EASING -> return CycleType.TYPE_EASING
            CycleType.S_WAVE_PERIOD -> return CycleType.TYPE_WAVE_PERIOD
            CycleType.S_WAVE_SHAPE -> return CycleType.TYPE_WAVE_SHAPE
            CycleType.S_WAVE_PHASE -> return CycleType.TYPE_WAVE_PHASE
            CycleType.S_WAVE_OFFSET -> return CycleType.TYPE_WAVE_OFFSET
            CycleType.S_CUSTOM_WAVE_SHAPE -> return CycleType.TYPE_CUSTOM_WAVE_SHAPE
        }
        return -1
    }

    fun addCycleValues(oscSet: HashMap<String, KeyCycleOscillator?>) {
        for (key in oscSet.keys) {
            if (key.startsWith(S_CUSTOM)) {
                val customKey = key.substring(S_CUSTOM.length + 1)
                val cValue = mCustom!![customKey]
                if (cValue == null || cValue.type != TypedValues.Custom.TYPE_FLOAT) {
                    continue
                }
                val osc = oscSet[key] ?: continue
                osc.setPoint(framePosition, mWaveShape, mCustomWaveShape, -1, mWavePeriod, mWaveOffset, mWavePhase, cValue.valueToInterpolate, cValue)
                continue
            }
            val value = getValue(key)
            if (java.lang.Float.isNaN(value)) {
                continue
            }
            val osc = oscSet[key] ?: continue
            osc.setPoint(framePosition, mWaveShape, mCustomWaveShape, -1, mWavePeriod, mWaveOffset, mWavePhase, value)
        }
    }

    fun dump() {
        println(
            "MotionKeyCycle{" +
                    "mWaveShape=" + mWaveShape +
                    ", mWavePeriod=" + mWavePeriod +
                    ", mWaveOffset=" + mWaveOffset +
                    ", mWavePhase=" + mWavePhase +
                    ", mRotation=" + mRotation +
                    '}'
        )
    }

    /*
    fun printAttributes() {
        val nameSet = HashSet<String>()
        getAttributeNames(nameSet)
        Utils.log(" ------------- framePosition -------------")
        Utils.log(
            "MotionKeyCycle{" +
                    "Shape=" + mWaveShape +
                    ", Period=" + mWavePeriod +
                    ", Offset=" + mWaveOffset +
                    ", Phase=" + mWavePhase +
                    '}'
        )
        val names = nameSet.toTypedArray()
        for (i in names.indices) {
            val id = AttributesType.getId(names[i])
            Utils.log(names[i] + ":" + getValue(names[i]))
        }
    }*/

    companion object {
        private const val TAG = "KeyCycle"
        const val NAME = "KeyCycle"
        const val WAVE_PERIOD = "wavePeriod"
        const val WAVE_OFFSET = "waveOffset"
        const val WAVE_PHASE = "wavePhase"
        const val WAVE_SHAPE = "waveShape"
        val SHAPE_SIN_WAVE = Oscillator.SIN_WAVE
        val SHAPE_SQUARE_WAVE = Oscillator.SQUARE_WAVE
        val SHAPE_TRIANGLE_WAVE = Oscillator.TRIANGLE_WAVE
        val SHAPE_SAW_WAVE = Oscillator.SAW_WAVE
        val SHAPE_REVERSE_SAW_WAVE = Oscillator.REVERSE_SAW_WAVE
        val SHAPE_COS_WAVE = Oscillator.COS_WAVE
        val SHAPE_BOUNCE = Oscillator.BOUNCE
        const val KEY_TYPE = 4
    }

    init {
        mType = KEY_TYPE
        mCustom = HashMap()
    }
}