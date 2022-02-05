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

import androidx.constraintlayout.core.motion.MotionWidget
import androidx.constraintlayout.core.motion.utils.TypedValues.CycleType
import androidx.constraintlayout.core.motion.utils.TypedValues.AttributesType
import androidx.constraintlayout.utility.Utils.degrees
import kotlin.math.atan2

/**
 * Provide the engine for executing cycles.
 * KeyCycleOscillator
 *
 * @suppress
 */
abstract class KeyCycleOscillator() {
    var curveFit: CurveFit? = null
        private set
    private var mCycleOscillator: CycleOscillator? = null
    private var mType: String? = null
    private var mWaveShape = 0
    private var mWaveString: String? = null
    var mVariesBy = 0 // 0 = position, 2=path
    var mWavePoints = ArrayList<WavePoint>()

    private class CoreSpline(type: String) : KeyCycleOscillator() {
        var typeId: Int
        override fun setProperty(widget: MotionWidget, t: Float) {
            widget.setValue(typeId, get(t))
        }

        init {
            typeId = CycleType.Companion.getId(type)
        }
    }

    class PathRotateSet(type: String) : KeyCycleOscillator() {
        var typeId: Int
        override fun setProperty(widget: MotionWidget, t: Float) {
            widget.setValue(typeId, get(t))
        }

        fun setPathRotate(view: MotionWidget, t: Float, dx: Double, dy: Double) {
            view.rotationZ = get(t) + degrees(atan2(dy, dx)).toFloat()
        }

        init {
            typeId = CycleType.Companion.getId(type)
        }
    }

    fun variesByPath(): Boolean {
        return mVariesBy == 1
    }

    class WavePoint(var mPosition: Int, var mPeriod: Float, var mOffset: Float, var mPhase: Float, var mValue: Float)

    override fun toString(): String {
        var str = mType
        /*val df = DecimalFormat("##.##")
        for (wp: WavePoint in mWavePoints) {
            str += "[" + wp.mPosition + " , " + df.format(wp.mValue.toDouble()) + "] "
        }*/
        return (str) ?: ""
    }

    fun setType(type: String?) {
        mType = type
    }

    operator fun get(t: Float): Float {
        return mCycleOscillator!!.getValues(t).toFloat()
    }

    fun getSlope(position: Float): Float {
        return mCycleOscillator!!.getSlope(position).toFloat()
    }

    protected fun setCustom(custom: Any?) {}

    /**
     * sets a oscillator wave point
     *
     * @param framePosition the position
     * @param variesBy      only varies by path supported for now
     * @param period        the period of the wave
     * @param offset        the offset value
     * @param value         the adder
     * @param custom        The ConstraintAttribute used to set the value
     */
    fun setPoint(
        framePosition: Int,
        shape: Int,
        waveString: String?,
        variesBy: Int,
        period: Float,
        offset: Float,
        phase: Float,
        value: Float,
        custom: Any?
    ) {
        mWavePoints.add(WavePoint(framePosition, period, offset, phase, value))
        if (variesBy != -1) {
            mVariesBy = variesBy
        }
        mWaveShape = shape
        setCustom(custom)
        mWaveString = waveString
    }

    /**
     * sets a oscillator wave point
     *
     * @param framePosition the position
     * @param variesBy      only varies by path supported for now
     * @param period        the period of the wave
     * @param offset        the offset value
     * @param value         the adder
     */
    fun setPoint(
        framePosition: Int,
        shape: Int,
        waveString: String?,
        variesBy: Int,
        period: Float,
        offset: Float,
        phase: Float,
        value: Float
    ) {
        mWavePoints.add(WavePoint(framePosition, period, offset, phase, value))
        if (variesBy != -1) {
            mVariesBy = variesBy
        }
        mWaveShape = shape
        mWaveString = waveString
    }

    fun setup(pathLength: Float) {
        val count = mWavePoints.size
        if (count == 0) {
            return
        }
        mWavePoints.sortWith { lhs, rhs -> lhs.mPosition.compareTo(rhs.mPosition) }
        val time = DoubleArray(count)
        val values = Array(count) { DoubleArray(3) }
        mCycleOscillator = CycleOscillator(mWaveShape, mWaveString, mVariesBy, count)
        var i = 0
        for (wp: WavePoint in mWavePoints) {
            time[i] = wp.mPeriod * 1E-2
            values[i][0] = wp.mValue.toDouble()
            values[i][1] = wp.mOffset.toDouble()
            values[i][2] = wp.mPhase.toDouble()
            mCycleOscillator!!.setPoint(i, wp.mPosition, wp.mPeriod, wp.mOffset, wp.mPhase, wp.mValue)
            i++
        }
        mCycleOscillator!!.setup(pathLength)
        curveFit = CurveFit.Companion.get(CurveFit.Companion.SPLINE, time, values)
    }

    private object IntDoubleSort {
        fun sort(key: IntArray, value: FloatArray, low: Int, hi: Int) {
            var low = low
            var hi = hi
            val stack = IntArray(key.size + 10)
            var count = 0
            stack[count++] = hi
            stack[count++] = low
            while (count > 0) {
                low = stack[--count]
                hi = stack[--count]
                if (low < hi) {
                    val p = partition(key, value, low, hi)
                    stack[count++] = p - 1
                    stack[count++] = low
                    stack[count++] = hi
                    stack[count++] = p + 1
                }
            }
        }

        private fun partition(array: IntArray, value: FloatArray, low: Int, hi: Int): Int {
            val pivot = array[hi]
            var i = low
            for (j in low until hi) {
                if (array[j] <= pivot) {
                    swap(array, value, i, j)
                    i++
                }
            }
            swap(array, value, i, hi)
            return i
        }

        private fun swap(array: IntArray, value: FloatArray, a: Int, b: Int) {
            val tmp = array[a]
            array[a] = array[b]
            array[b] = tmp
            val tmpv = value[a]
            value[a] = value[b]
            value[b] = tmpv
        }
    }

    private object IntFloatFloatSort {
        fun sort(key: IntArray, value1: FloatArray, value2: FloatArray, low: Int, hi: Int) {
            var low = low
            var hi = hi
            val stack = IntArray(key.size + 10)
            var count = 0
            stack[count++] = hi
            stack[count++] = low
            while (count > 0) {
                low = stack[--count]
                hi = stack[--count]
                if (low < hi) {
                    val p = partition(key, value1, value2, low, hi)
                    stack[count++] = p - 1
                    stack[count++] = low
                    stack[count++] = hi
                    stack[count++] = p + 1
                }
            }
        }

        private fun partition(array: IntArray, value1: FloatArray, value2: FloatArray, low: Int, hi: Int): Int {
            val pivot = array[hi]
            var i = low
            for (j in low until hi) {
                if (array[j] <= pivot) {
                    swap(array, value1, value2, i, j)
                    i++
                }
            }
            swap(array, value1, value2, i, hi)
            return i
        }

        private fun swap(array: IntArray, value1: FloatArray, value2: FloatArray, a: Int, b: Int) {
            val tmp = array[a]
            array[a] = array[b]
            array[b] = tmp
            var tmpFloat = value1[a]
            value1[a] = value1[b]
            value1[b] = tmpFloat
            tmpFloat = value2[a]
            value2[a] = value2[b]
            value2[b] = tmpFloat
        }
    }

    internal class CycleOscillator(var mWaveShape: Int, customShape: String?, private val mVariesBy: Int, steps: Int) {
        var mOscillator = Oscillator()
        private val OFFST = 0
        private val PHASE = 1
        private val VALUE = 2
        var mValues: FloatArray
        var mPosition: DoubleArray
        var mPeriod: FloatArray
        var mOffset // offsets will be spline interpolated
                : FloatArray
        var mPhase // phase will be spline interpolated
                : FloatArray
        var mScale // scales will be spline interpolated
                : FloatArray
        var mCurveFit: CurveFit? = null
        var mSplineValueCache // for the return value of the curve fit
                : DoubleArray = doubleArrayOf()
        var mSplineSlopeCache // for the return value of the curve fit
                : DoubleArray = doubleArrayOf()
        var mPathLength = 0f
        fun getValues(time: Float): Double {
            if (mCurveFit != null) {
                mCurveFit!!.getPos(time.toDouble(), mSplineValueCache)
            } else { // only one value no need to interpolate
                mSplineValueCache[OFFST] = mOffset[0].toDouble()
                mSplineValueCache[PHASE] = mPhase[0].toDouble()
                mSplineValueCache[VALUE] = mValues[0].toDouble()
            }
            val offset = mSplineValueCache[OFFST]
            val phase = mSplineValueCache[PHASE]
            val waveValue = mOscillator.getValue(time.toDouble(), phase)
            return offset + waveValue * mSplineValueCache[VALUE]
        }

        val lastPhase: Double
            get() = mSplineValueCache.get(1)

        fun getSlope(time: Float): Double {
            if (mCurveFit != null) {
                mCurveFit!!.getSlope(time.toDouble(), mSplineSlopeCache)
                mCurveFit!!.getPos(time.toDouble(), mSplineValueCache)
            } else { // only one value no need to interpolate
                mSplineSlopeCache[OFFST] = 0.0
                mSplineSlopeCache[PHASE] = 0.0
                mSplineSlopeCache[VALUE] = 0.0
            }
            val waveValue = mOscillator.getValue(time.toDouble(), mSplineValueCache[PHASE])
            val waveSlope = mOscillator.getSlope(time.toDouble(), mSplineValueCache[PHASE], mSplineSlopeCache[PHASE])
            return mSplineSlopeCache[OFFST] + (waveValue * mSplineSlopeCache[VALUE]) + (waveSlope * mSplineValueCache[VALUE])
        }

        /**
         * @param index
         * @param framePosition
         * @param wavePeriod
         * @param offset
         * @param values
         */
        fun setPoint(index: Int, framePosition: Int, wavePeriod: Float, offset: Float, phase: Float, values: Float) {
            mPosition[index] = framePosition / 100.0
            mPeriod[index] = wavePeriod
            mOffset[index] = offset
            mPhase[index] = phase
            mValues[index] = values
        }

        fun setup(pathLength: Float) {
            mPathLength = pathLength
            val splineValues = Array(mPosition.size) { DoubleArray(3) }
            mSplineValueCache = DoubleArray(2 + mValues.size)
            mSplineSlopeCache = DoubleArray(2 + mValues.size)
            if (mPosition[0] > 0) {
                mOscillator.addPoint(0.0, mPeriod[0])
            }
            val last = mPosition.size - 1
            if (mPosition[last] < 1.0f) {
                mOscillator.addPoint(1.0, mPeriod[last])
            }
            for (i in splineValues.indices) {
                splineValues[i][OFFST] = mOffset[i].toDouble()
                splineValues[i][PHASE] = mPhase[i].toDouble()
                splineValues[i][VALUE] = mValues[i].toDouble()
                mOscillator.addPoint(mPosition[i], mPeriod[i])
            }

            // TODO: add mVariesBy and get total time and path length
            mOscillator.normalize()
            if (mPosition.size > 1) {
                mCurveFit = CurveFit.Companion.get(CurveFit.Companion.SPLINE, mPosition, splineValues)
            } else {
                mCurveFit = null
            }
        }

        companion object {
            val UNSET = -1 // -1 is typically used through out android to the UNSET value
            private val TAG = "CycleOscillator"
        }

        init {
            mOscillator.setType(mWaveShape, customShape)
            mValues = FloatArray(steps)
            mPosition = DoubleArray(steps)
            mPeriod = FloatArray(steps)
            mOffset = FloatArray(steps)
            mPhase = FloatArray(steps)
            mScale = FloatArray(steps)
        }
    }

    open fun setProperty(widget: MotionWidget, t: Float) {}

    companion object {
        private val TAG = "KeyCycleOscillator"
        @kotlin.jvm.JvmStatic
        fun makeWidgetCycle(attribute: String): KeyCycleOscillator {
            return if ((attribute == AttributesType.Companion.S_PATH_ROTATE)) {
                PathRotateSet(attribute)
            } else CoreSpline(attribute)
        }
    }
}