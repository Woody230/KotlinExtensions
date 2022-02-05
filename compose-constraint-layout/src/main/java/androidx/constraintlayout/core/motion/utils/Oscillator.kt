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
package androidx.constraintlayout.core.motion.utils

/**
 * This generates variable frequency oscillation curves
 *
 * @suppress
 */
class Oscillator constructor() {
    var mPeriod: FloatArray = floatArrayOf()
    var mPosition: DoubleArray = doubleArrayOf()
    var mArea: DoubleArray = doubleArrayOf()
    var mCustomType: String? = null
    var mCustomCurve: MonotonicCurveFit? = null
    var mType: Int = 0
    var PI2: Double = Math.PI * 2
    private var mNormalized: Boolean = false
    public override fun toString(): String {
        return ""//"pos =" + Arrays.toString(mPosition) + " period=" + Arrays.toString(mPeriod)
    }

    fun setType(type: Int, customType: String?) {
        mType = type
        mCustomType = customType
        if (mCustomType != null) {
            mCustomCurve = MonotonicCurveFit.Companion.buildWave(customType)
        }
    }

    fun addPoint(position: Double, period: Float) {
        val len: Int = mPeriod.size + 1
        var j: Int = mPosition.binarySearch(position)
        if (j < 0) {
            j = -j - 1
        }
        mPosition = mPosition.copyOf(len)
        mPeriod = mPeriod.copyOf(len)
        mArea = DoubleArray(len)
        System.arraycopy(mPosition, j, mPosition, j + 1, len - j - 1)
        mPosition[j] = position
        mPeriod[j] = period
        mNormalized = false
    }

    /**
     * After adding point every thing must be normalized
     */
    fun normalize() {
        var totalArea: Double = 0.0
        var totalCount: Double = 0.0
        for (i in mPeriod.indices) {
            totalCount += mPeriod.get(i)
        }
        for (i in 1 until mPeriod.size) {
            val h: Float = (mPeriod.get(i - 1) + mPeriod.get(i)) / 2
            val w: Double = mPosition.get(i) - mPosition.get(i - 1)
            totalArea = totalArea + w * h
        }
        // scale periods to normalize it
        for (i in mPeriod.indices) {
            mPeriod[i] = (mPeriod[i] * totalCount / totalArea).toFloat()
        }
        mArea[0] = 0.0
        for (i in 1 until mPeriod.size) {
            val h: Float = (mPeriod.get(i - 1) + mPeriod.get(i)) / 2
            val w: Double = mPosition.get(i) - mPosition.get(i - 1)
            mArea[i] = mArea.get(i - 1) + w * h
        }
        mNormalized = true
    }

    fun getP(time: Double): Double {
        var time: Double = time
        if (time < 0) {
            time = 0.0
        } else if (time > 1) {
            time = 1.0
        }
        var index: Int = mPosition.binarySearch(time)
        var p: Double = 0.0
        if (index > 0) {
            p = 1.0
        } else if (index != 0) {
            index = -index - 1
            val t: Double = time
            val m: Double = (mPeriod.get(index) - mPeriod.get(index - 1)) / (mPosition.get(index) - mPosition.get(index - 1))
            p = (mArea.get(index - 1)
                    + ((mPeriod.get(index - 1) - m * mPosition.get(index - 1)) * (t - mPosition.get(index - 1))
                    ) + (m * (t * t - mPosition.get(index - 1) * mPosition.get(index - 1)) / 2))
        }
        return p
    }

    fun getValue(time: Double, phase: Double): Double {
        val angle: Double = phase + getP(time) // angle is / by 360
        when (mType) {
            SIN_WAVE -> return Math.sin(PI2 * (angle))
            SQUARE_WAVE -> return Math.signum(0.5 - angle % 1)
            TRIANGLE_WAVE -> return 1 - Math.abs(((angle) * 4 + 1) % 4 - 2)
            SAW_WAVE -> return ((angle * 2 + 1) % 2) - 1
            REVERSE_SAW_WAVE -> return (1 - ((angle * 2 + 1) % 2))
            COS_WAVE -> return Math.cos(PI2 * (phase + angle))
            BOUNCE -> {
                val x: Double = 1 - Math.abs(((angle) * 4) % 4 - 2)
                return 1 - x * x
            }
            CUSTOM -> return mCustomCurve!!.getPos(angle % 1, 0)
            else -> return Math.sin(PI2 * (angle))
        }
    }

    fun getDP(time: Double): Double {
        var time: Double = time
        if (time <= 0) {
            time = 0.00001
        } else if (time >= 1) {
            time = .999999
        }
        var index: Int = mPosition.binarySearch(time)
        var p: Double = 0.0
        if (index > 0) {
            return 0.0
        }
        if (index != 0) {
            index = -index - 1
            val t: Double = time
            val m: Double = (mPeriod.get(index) - mPeriod.get(index - 1)) / (mPosition.get(index) - mPosition.get(index - 1))
            p = m * t + (mPeriod.get(index - 1) - m * mPosition.get(index - 1))
        }
        return p
    }

    fun getSlope(time: Double, phase: Double, dphase: Double): Double {
        val angle: Double = phase + getP(time)
        val dangle_dtime: Double = getDP(time) + dphase
        when (mType) {
            SIN_WAVE -> return PI2 * dangle_dtime * Math.cos(PI2 * angle)
            SQUARE_WAVE -> return 0.0
            TRIANGLE_WAVE -> return 4 * dangle_dtime * Math.signum(((angle) * 4 + 3) % 4 - 2)
            SAW_WAVE -> return dangle_dtime * 2
            REVERSE_SAW_WAVE -> return -dangle_dtime * 2
            COS_WAVE -> return -PI2 * dangle_dtime * Math.sin(PI2 * angle)
            BOUNCE -> return 4 * dangle_dtime * (((angle) * 4 + 2) % 4 - 2)
            CUSTOM -> return mCustomCurve!!.getSlope(angle % 1, 0)
            else -> return PI2 * dangle_dtime * Math.cos(PI2 * angle)
        }
    }

    companion object {
        var TAG: String = "Oscillator"
        val SIN_WAVE: Int = 0 // theses must line up with attributes
        val SQUARE_WAVE: Int = 1
        val TRIANGLE_WAVE: Int = 2
        val SAW_WAVE: Int = 3
        val REVERSE_SAW_WAVE: Int = 4
        val COS_WAVE: Int = 5
        val BOUNCE: Int = 6
        val CUSTOM: Int = 7
    }
}