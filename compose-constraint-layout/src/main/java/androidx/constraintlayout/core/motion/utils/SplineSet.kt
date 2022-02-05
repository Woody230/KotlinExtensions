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
import androidx.constraintlayout.core.motion.utils.TypedValues.AttributesType
import androidx.constraintlayout.core.motion.CustomAttribute
import androidx.constraintlayout.core.motion.utils.KeyFrameArray.CustomArray
import androidx.constraintlayout.core.motion.CustomVariable
import androidx.constraintlayout.core.motion.utils.KeyFrameArray.CustomVar
import androidx.constraintlayout.core.state.WidgetFrame

/**
 * This engine allows manipulation of attributes by Curves
 *
 * @suppress
 */
abstract class SplineSet constructor() {
    var curveFit: CurveFit? = null
        protected set
    protected var mTimePoints: IntArray = IntArray(10)
    protected var mValues: FloatArray = FloatArray(10)
    private var count: Int = 0
    private var mType: String? = null
    open fun setProperty(widget: TypedValues, t: Float) {
        widget.setValue(AttributesType.Companion.getId(mType), get(t))
    }

    public override fun toString(): String {
        var str: String? = mType ?: ""
        /*
        val df: DecimalFormat = DecimalFormat("##.##")
        for (i in 0 until count) {
            str += "[" + mTimePoints[i] + " , " + df.format(mValues[i]) + "] "
        }*/
        return (str)!!
    }

    fun setType(type: String?) {
        mType = type
    }

    operator fun get(t: Float): Float {
        return curveFit!!.getPos(t.toDouble(), 0).toFloat()
    }

    fun getSlope(t: Float): Float {
        return curveFit!!.getSlope(t.toDouble(), 0).toFloat()
    }

    open fun setPoint(position: Int, value: Float) {
        if (mTimePoints.size < count + 1) {
            mTimePoints = mTimePoints.copyOf(mTimePoints.size * 2)
            mValues = mValues.copyOf(mValues.size * 2)
        }
        mTimePoints[count] = position
        mValues[count] = value
        count++
    }

    open fun setup(curveType: Int) {
        if (count == 0) {
            return
        }
        Sort.doubleQuickSort(mTimePoints, mValues, 0, count - 1)
        var unique: Int = 1
        for (i in 1 until count) {
            if (mTimePoints.get(i - 1) != mTimePoints[i]) {
                unique++
            }
        }
        val time: DoubleArray = DoubleArray(unique)
        val values: Array<DoubleArray> = Array(unique, { DoubleArray(1) })
        var k: Int = 0
        for (i in 0 until count) {
            if (i > 0 && mTimePoints[i] == mTimePoints.get(i - 1)) {
                continue
            }
            time[k] = mTimePoints[i] * 1E-2
            values[k][0] = mValues[i].toDouble()
            k++
        }
        curveFit = CurveFit.Companion.get(curveType, time, values)
    }

    private object Sort {
        fun doubleQuickSort(key: IntArray, value: FloatArray, low: Int, hi: Int) {
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

        private fun partition(array: IntArray, value: FloatArray, low: Int, hi: Int): Int {
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

        private fun swap(array: IntArray, value: FloatArray, a: Int, b: Int) {
            val tmp: Int = array[a]
            array[a] = array[b]
            array[b] = tmp
            val tmpv: Float = value[a]
            value[a] = value[b]
            value[b] = tmpv
        }
    }

    class CustomSet constructor(attribute: String, attrList: CustomArray) : SplineSet() {
        var mAttributeName: String
        var mConstraintAttributeList: CustomArray
        var mTempValues: FloatArray = floatArrayOf()
        public override fun setup(curveType: Int) {
            val size: Int = mConstraintAttributeList.size()
            val dimensionality: Int = mConstraintAttributeList.valueAt(0)!!.numberOfInterpolatedValues()
            val time: DoubleArray = DoubleArray(size)
            mTempValues = FloatArray(dimensionality)
            val values: Array<DoubleArray> = Array(size, { DoubleArray(dimensionality) })
            for (i in 0 until size) {
                val key: Int = mConstraintAttributeList.keyAt(i)
                val ca: CustomAttribute? = mConstraintAttributeList.valueAt(i)
                time[i] = key * 1E-2
                ca!!.getValuesToInterpolate(mTempValues)
                for (k in mTempValues.indices) {
                    values[i][k] = mTempValues[k].toDouble()
                }
            }
            curveFit = CurveFit.Companion.get(curveType, time, values)
        }

        public override fun setPoint(position: Int, value: Float) {
            throw RuntimeException("don't call for custom attribute call setPoint(pos, ConstraintAttribute)")
        }

        fun setPoint(position: Int, value: CustomAttribute?) {
            mConstraintAttributeList.append(position, value)
        }

        fun setProperty(view: WidgetFrame, t: Float) {
            curveFit!!.getPos(t.toDouble(), mTempValues)
            view.setCustomValue(mConstraintAttributeList.valueAt(0), mTempValues)
        }

        init {
            mAttributeName = attribute.split(",".toRegex()).toTypedArray().get(1)
            mConstraintAttributeList = attrList
        }
    }

    private class CoreSpline constructor(var type1: String, var start: Long) : SplineSet() {
        public override fun setProperty(widget: TypedValues, t: Float) {
            val id: Int = widget.getId(type1)
            widget.setValue(id, get(t))
        }
    }

    class CustomSpline constructor(attribute: String, attrList: CustomVar) : SplineSet() {
        var mAttributeName: String
        var mConstraintAttributeList: CustomVar
        var mTempValues: FloatArray = floatArrayOf()
        public override fun setup(curveType: Int) {
            val size: Int = mConstraintAttributeList.size()
            val dimensionality: Int = mConstraintAttributeList.valueAt(0)!!.numberOfInterpolatedValues()
            val time: DoubleArray = DoubleArray(size)
            mTempValues = FloatArray(dimensionality)
            val values: Array<DoubleArray> = Array(size, { DoubleArray(dimensionality) })
            for (i in 0 until size) {
                val key: Int = mConstraintAttributeList.keyAt(i)
                val ca: CustomVariable? = mConstraintAttributeList.valueAt(i)
                time[i] = key * 1E-2
                ca!!.getValuesToInterpolate(mTempValues)
                for (k in mTempValues.indices) {
                    values[i][k] = mTempValues[k].toDouble()
                }
            }
            curveFit = CurveFit.Companion.get(curveType, time, values)
        }

        public override fun setPoint(position: Int, value: Float) {
            throw RuntimeException("don't call for custom attribute call setPoint(pos, ConstraintAttribute)")
        }

        public override fun setProperty(widget: TypedValues, t: Float) {
            setProperty(widget as MotionWidget?, t)
        }

        fun setPoint(position: Int, value: CustomVariable?) {
            mConstraintAttributeList.append(position, value)
        }

        fun setProperty(view: MotionWidget?, t: Float) {
            curveFit!!.getPos(t.toDouble(), mTempValues)
            mConstraintAttributeList.valueAt(0)!!.setInterpolatedValue(view, mTempValues)
        }

        init {
            mAttributeName = attribute.split(",".toRegex()).toTypedArray().get(1)
            mConstraintAttributeList = attrList
        }
    }

    companion object {
        private val TAG: String = "SplineSet"
        fun makeCustomSpline(str: String, attrList: CustomArray): SplineSet {
            return CustomSet(str, attrList)
        }

        @kotlin.jvm.JvmStatic
        fun makeCustomSplineSet(str: String, attrList: CustomVar): SplineSet {
            return CustomSpline(str, attrList)
        }

        @kotlin.jvm.JvmStatic
        fun makeSpline(str: String, currentTime: Long): SplineSet {
            return CoreSpline(str, currentTime)
        }
    }
}