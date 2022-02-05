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
import androidx.constraintlayout.core.motion.CustomAttribute
import androidx.constraintlayout.core.motion.utils.KeyFrameArray.CustomArray
import androidx.constraintlayout.core.motion.CustomVariable
import androidx.constraintlayout.core.motion.utils.KeyFrameArray.CustomVar

/**
 * This engine allows manipulation of attributes by wave shapes oscillating in time
 *
 * @suppress
 */
abstract class TimeCycleSplineSet constructor() {
    var curveFit: CurveFit? = null
        protected set
    protected var mWaveShape: Int = 0
    protected var mTimePoints: IntArray = IntArray(10)
    protected var mValues: Array<FloatArray> = Array(10, { FloatArray(3) })
    protected var count: Int = 0
    protected var mType: String? = null
    protected open var mCache: FloatArray = FloatArray(3)
    protected var mContinue: Boolean = false
    protected var last_time: Long = 0
    protected var last_cycle: Float = Float.NaN
    public override fun toString(): String {
        var str: String? = mType ?: ""
        /*
        val df: DecimalFormat = DecimalFormat("##.##")
        for (i in 0 until count) {
            str += "[" + mTimePoints.get(i) + " , " + df.format(mValues.get(i)) + "] "
        }*/
        return (str)!!
    }

    fun setType(type: String?) {
        mType = type
    }

    /**
     * @param period cycles per second
     * @return
     */
    protected fun calcWave(period: Float): Float {
        val p: Float = period
        when (mWaveShape) {
            Oscillator.Companion.SIN_WAVE -> return Math.sin((p * VAL_2PI).toDouble()).toFloat()
            Oscillator.Companion.SQUARE_WAVE -> return Math.signum(p * VAL_2PI)
            Oscillator.Companion.TRIANGLE_WAVE -> return 1 - Math.abs(p)
            Oscillator.Companion.SAW_WAVE -> return ((p * 2 + 1) % 2) - 1
            Oscillator.Companion.REVERSE_SAW_WAVE -> return (1 - ((p * 2 + 1) % 2))
            Oscillator.Companion.COS_WAVE -> return Math.cos((p * VAL_2PI).toDouble()).toFloat()
            Oscillator.Companion.BOUNCE -> {
                val x: Float = 1 - Math.abs((p * 4) % 4 - 2)
                return 1 - x * x
            }
            else -> return Math.sin((p * VAL_2PI).toDouble()).toFloat()
        }
    }

    protected fun setStartTime(currentTime: Long) {
        last_time = currentTime
    }

    open fun setPoint(position: Int, value: Float, period: Float, shape: Int, offset: Float) {
        mTimePoints[count] = position
        mValues[count][CURVE_VALUE] = value
        mValues[count][CURVE_PERIOD] = period
        mValues[count][CURVE_OFFSET] = offset
        mWaveShape = Math.max(mWaveShape, shape) // the highest value shape is chosen
        count++
    }

    class CustomSet constructor(attribute: String, attrList: CustomArray) : TimeCycleSplineSet() {
        var mAttributeName: String
        var mConstraintAttributeList: CustomArray
        var mWaveProperties: KeyFrameArray.FloatArray = KeyFrameArray.FloatArray()
        var mTempValues: FloatArray = floatArrayOf()
        override var mCache: FloatArray = floatArrayOf()
        public override fun setup(curveType: Int) {
            val size: Int = mConstraintAttributeList.size()
            val dimensionality: Int = mConstraintAttributeList.valueAt(0)!!.numberOfInterpolatedValues()
            val time: DoubleArray = DoubleArray(size)
            mTempValues = FloatArray(dimensionality + 2)
            mCache = FloatArray(dimensionality)
            val values: Array<DoubleArray> = Array(size, { DoubleArray(dimensionality + 2) })
            for (i in 0 until size) {
                val key: Int = mConstraintAttributeList.keyAt(i)
                val ca: CustomAttribute? = mConstraintAttributeList.valueAt(i)
                val waveProp: FloatArray? = mWaveProperties.valueAt(i)
                time[i] = key * 1E-2
                ca!!.getValuesToInterpolate(mTempValues)
                for (k in mTempValues.indices) {
                    values.get(i)[k] = mTempValues.get(k).toDouble()
                }
                values.get(i)[dimensionality] = waveProp!!.get(0).toDouble()
                values.get(i)[dimensionality + 1] = waveProp.get(1).toDouble()
            }
            curveFit = CurveFit.Companion.get(curveType, time, values)
        }

        public override fun setPoint(position: Int, value: Float, period: Float, shape: Int, offset: Float) {
            throw RuntimeException("don't call for custom attribute call setPoint(pos, ConstraintAttribute,...)")
        }

        fun setPoint(position: Int, value: CustomAttribute?, period: Float, shape: Int, offset: Float) {
            mConstraintAttributeList.append(position, value)
            mWaveProperties.append(position, floatArrayOf(period, offset))
            mWaveShape = Math.max(mWaveShape, shape) // the highest value shape is chosen
        }

        fun setProperty(view: MotionWidget, t: Float, time: Long, cache: KeyCache): Boolean {
            curveFit!!.getPos(t.toDouble(), mTempValues)
            val period: Float = mTempValues.get(mTempValues.size - 2)
            val offset: Float = mTempValues.get(mTempValues.size - 1)
            val delta_time: Long = time - last_time
            if (java.lang.Float.isNaN(last_cycle)) { // it has not been set
                last_cycle = cache.getFloatValue(view, mAttributeName, 0) // check the cache
                if (java.lang.Float.isNaN(last_cycle)) {  // not in cache so set to 0 (start)
                    last_cycle = 0f
                }
            }
            last_cycle = ((last_cycle + delta_time * 1E-9 * period) % 1.0).toFloat()
            last_time = time
            val wave: Float = calcWave(last_cycle)
            mContinue = false
            for (i in mCache.indices) {
                mContinue = mContinue or (mTempValues.get(i) != 0f)
                mCache[i] = mTempValues.get(i) * wave + offset
            }
            view.setInterpolatedValue(mConstraintAttributeList.valueAt(0), mCache)
            if (period != 0.0f) {
                mContinue = true
            }
            return mContinue
        }

        init {
            mAttributeName = attribute.split(",".toRegex()).toTypedArray().get(1)
            mConstraintAttributeList = attrList
        }
    }

    open fun setup(curveType: Int) {
        if (count == 0) {
            System.err.println("Error no points added to " + mType)
            return
        }
        Sort.doubleQuickSort(mTimePoints, mValues, 0, count - 1)
        var unique: Int = 0
        for (i in 1 until mTimePoints.size) {
            if (mTimePoints.get(i) != mTimePoints.get(i - 1)) {
                unique++
            }
        }
        if (unique == 0) {
            unique = 1
        }
        val time: DoubleArray = DoubleArray(unique)
        val values: Array<DoubleArray> = Array(unique, { DoubleArray(3) })
        var k: Int = 0
        for (i in 0 until count) {
            if (i > 0 && mTimePoints.get(i) == mTimePoints.get(i - 1)) {
                continue
            }
            time[k] = mTimePoints.get(i) * 1E-2
            values.get(k)[0] = mValues.get(i).get(0).toDouble()
            values.get(k)[1] = mValues.get(i).get(1).toDouble()
            values.get(k)[2] = mValues.get(i).get(2).toDouble()
            k++
        }
        curveFit = CurveFit.Companion.get(curveType, time, values)
    }

    protected object Sort {
        fun doubleQuickSort(key: IntArray, value: Array<FloatArray>, low: Int, hi: Int) {
            var low: Int = low
            var hi: Int = hi
            val stack: IntArray = IntArray(key.size + 10)
            var count: Int = 0
            stack[count++] = hi
            stack[count++] = low
            while (count > 0) {
                low = stack.get(--count)
                hi = stack.get(--count)
                if (low < hi) {
                    val p: Int = partition(key, value, low, hi)
                    stack[count++] = p - 1
                    stack[count++] = low
                    stack[count++] = hi
                    stack[count++] = p + 1
                }
            }
        }

        private fun partition(array: IntArray, value: Array<FloatArray>, low: Int, hi: Int): Int {
            val pivot: Int = array.get(hi)
            var i: Int = low
            for (j in low until hi) {
                if (array.get(j) <= pivot) {
                    swap(array, value, i, j)
                    i++
                }
            }
            swap(array, value, i, hi)
            return i
        }

        private fun swap(array: IntArray, value: Array<FloatArray>, a: Int, b: Int) {
            val tmp: Int = array.get(a)
            array[a] = array.get(b)
            array[b] = tmp
            val tmpv: FloatArray = value.get(a)
            value[a] = value.get(b)
            value[b] = tmpv
        }
    }

    class CustomVarSet constructor(attribute: String, attrList: CustomVar) : TimeCycleSplineSet() {
        var mAttributeName: String
        var mConstraintAttributeList: CustomVar
        var mWaveProperties: KeyFrameArray.FloatArray = KeyFrameArray.FloatArray()
        var mTempValues: FloatArray = floatArrayOf()
        override var mCache: FloatArray = floatArrayOf()
        public override fun setup(curveType: Int) {
            val size: Int = mConstraintAttributeList.size()
            val dimensionality: Int = mConstraintAttributeList.valueAt(0)!!.numberOfInterpolatedValues()
            val time: DoubleArray = DoubleArray(size)
            mTempValues = FloatArray(dimensionality + 2)
            mCache = FloatArray(dimensionality)
            val values: Array<DoubleArray> = Array(size, { DoubleArray(dimensionality + 2) })
            for (i in 0 until size) {
                val key: Int = mConstraintAttributeList.keyAt(i)
                val ca: CustomVariable? = mConstraintAttributeList.valueAt(i)
                val waveProp: FloatArray? = mWaveProperties.valueAt(i)
                time[i] = key * 1E-2
                ca!!.getValuesToInterpolate(mTempValues)
                for (k in mTempValues.indices) {
                    values.get(i)[k] = mTempValues.get(k).toDouble()
                }
                values.get(i)[dimensionality] = waveProp!!.get(0).toDouble()
                values.get(i)[dimensionality + 1] = waveProp.get(1).toDouble()
            }
            curveFit = CurveFit.Companion.get(curveType, time, values)
        }

        public override fun setPoint(position: Int, value: Float, period: Float, shape: Int, offset: Float) {
            throw RuntimeException("don't call for custom attribute call setPoint(pos, ConstraintAttribute,...)")
        }

        fun setPoint(position: Int, value: CustomVariable?, period: Float, shape: Int, offset: Float) {
            mConstraintAttributeList.append(position, value)
            mWaveProperties.append(position, floatArrayOf(period, offset))
            mWaveShape = Math.max(mWaveShape, shape) // the highest value shape is chosen
        }

        fun setProperty(view: MotionWidget, t: Float, time: Long, cache: KeyCache): Boolean {
            curveFit!!.getPos(t.toDouble(), mTempValues)
            val period: Float = mTempValues.get(mTempValues.size - 2)
            val offset: Float = mTempValues.get(mTempValues.size - 1)
            val delta_time: Long = time - last_time
            if (java.lang.Float.isNaN(last_cycle)) { // it has not been set
                last_cycle = cache.getFloatValue(view, mAttributeName, 0) // check the cache
                if (java.lang.Float.isNaN(last_cycle)) {  // not in cache so set to 0 (start)
                    last_cycle = 0f
                }
            }
            last_cycle = ((last_cycle + delta_time * 1E-9 * period) % 1.0).toFloat()
            last_time = time
            val wave: Float = calcWave(last_cycle)
            mContinue = false
            for (i in mCache.indices) {
                mContinue = mContinue or (mTempValues.get(i) != 0f)
                mCache[i] = mTempValues.get(i) * wave + offset
            }
            mConstraintAttributeList.valueAt(0)!!.setInterpolatedValue(view, mCache)
            if (period != 0.0f) {
                mContinue = true
            }
            return mContinue
        }

        init {
            mAttributeName = attribute.split(",".toRegex()).toTypedArray().get(1)
            mConstraintAttributeList = attrList
        }
    }

    companion object {
        private val TAG: String = "SplineSet"
        protected val CURVE_VALUE: Int = 0
        protected val CURVE_PERIOD: Int = 1
        protected val CURVE_OFFSET: Int = 2
        protected var VAL_2PI: Float = (2 * Math.PI).toFloat()
    }
}