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

import kotlin.math.hypot

/**
 * This performs a spline interpolation in multiple dimensions
 *
 * @suppress
 */
class MonotonicCurveFit constructor(time: DoubleArray, y: Array<DoubleArray>) : CurveFit() {
    override val timePoints: DoubleArray
    private val mY: Array<DoubleArray>
    private val mTangent: Array<DoubleArray>
    private val mExtrapolate: Boolean = true
    var mSlopeTemp: DoubleArray
    public override fun getPos(t: Double, v: DoubleArray) {
        val n: Int = timePoints.size
        val dim: Int = mY.get(0).count()
        if (mExtrapolate) {
            if (t <= timePoints.get(0)) {
                getSlope(timePoints.get(0), mSlopeTemp)
                for (j in 0 until dim) {
                    v[j] = mY.get(0)[j] + (t - timePoints.get(0)) * mSlopeTemp[j]
                }
                return
            }
            if (t >= timePoints.get(n - 1)) {
                getSlope(timePoints.get(n - 1), mSlopeTemp)
                for (j in 0 until dim) {
                    v[j] = mY.get(n - 1)[j] + (t - timePoints.get(n - 1)) * mSlopeTemp[j]
                }
                return
            }
        } else {
            if (t <= timePoints.get(0)) {
                for (j in 0 until dim) {
                    v[j] = mY.get(0)[j]
                }
                return
            }
            if (t >= timePoints.get(n - 1)) {
                for (j in 0 until dim) {
                    v[j] = mY.get(n - 1)[j]
                }
                return
            }
        }
        for (i in 0 until n - 1) {
            if (t == timePoints.get(i)) {
                for (j in 0 until dim) {
                    v[j] = mY.get(i)[j]
                }
            }
            if (t < timePoints.get(i + 1)) {
                val h: Double = timePoints.get(i + 1) - timePoints.get(i)
                val x: Double = (t - timePoints.get(i)) / h
                for (j in 0 until dim) {
                    val y1: Double = mY.get(i)[j]
                    val y2: Double = mY.get(i + 1)[j]
                    val t1: Double = mTangent.get(i)[j]
                    val t2: Double = mTangent.get(i + 1)[j]
                    v[j] = interpolate(h, x, y1, y2, t1, t2)
                }
                return
            }
        }
    }

    public override fun getPos(t: Double, v: FloatArray) {
        val n: Int = timePoints.size
        val dim: Int = mY.get(0).count()
        if (mExtrapolate) {
            if (t <= timePoints.get(0)) {
                getSlope(timePoints.get(0), mSlopeTemp)
                for (j in 0 until dim) {
                    v[j] = (mY.get(0)[j] + (t - timePoints.get(0)) * mSlopeTemp[j]).toFloat()
                }
                return
            }
            if (t >= timePoints.get(n - 1)) {
                getSlope(timePoints.get(n - 1), mSlopeTemp)
                for (j in 0 until dim) {
                    v[j] = (mY.get(n - 1)[j] + (t - timePoints.get(n - 1)) * mSlopeTemp[j]).toFloat()
                }
                return
            }
        } else {
            if (t <= timePoints.get(0)) {
                for (j in 0 until dim) {
                    v[j] = mY.get(0)[j].toFloat()
                }
                return
            }
            if (t >= timePoints.get(n - 1)) {
                for (j in 0 until dim) {
                    v[j] = mY.get(n - 1)[j].toFloat()
                }
                return
            }
        }
        for (i in 0 until n - 1) {
            if (t == timePoints.get(i)) {
                for (j in 0 until dim) {
                    v[j] = mY.get(i)[j].toFloat()
                }
            }
            if (t < timePoints.get(i + 1)) {
                val h: Double = timePoints.get(i + 1) - timePoints.get(i)
                val x: Double = (t - timePoints.get(i)) / h
                for (j in 0 until dim) {
                    val y1: Double = mY.get(i)[j]
                    val y2: Double = mY.get(i + 1)[j]
                    val t1: Double = mTangent.get(i)[j]
                    val t2: Double = mTangent.get(i + 1)[j]
                    v[j] = interpolate(h, x, y1, y2, t1, t2).toFloat()
                }
                return
            }
        }
    }

    public override fun getPos(t: Double, j: Int): Double {
        val n: Int = timePoints.size
        if (mExtrapolate) {
            if (t <= timePoints.get(0)) {
                return mY.get(0)[j] + (t - timePoints.get(0)) * getSlope(timePoints.get(0), j)
            }
            if (t >= timePoints.get(n - 1)) {
                return mY.get(n - 1)[j] + (t - timePoints.get(n - 1)) * getSlope(timePoints.get(n - 1), j)
            }
        } else {
            if (t <= timePoints.get(0)) {
                return mY.get(0)[j]
            }
            if (t >= timePoints.get(n - 1)) {
                return mY.get(n - 1)[j]
            }
        }
        for (i in 0 until n - 1) {
            if (t == timePoints.get(i)) {
                return mY.get(i)[j]
            }
            if (t < timePoints.get(i + 1)) {
                val h: Double = timePoints.get(i + 1) - timePoints.get(i)
                val x: Double = (t - timePoints.get(i)) / h
                val y1: Double = mY.get(i)[j]
                val y2: Double = mY.get(i + 1)[j]
                val t1: Double = mTangent.get(i)[j]
                val t2: Double = mTangent.get(i + 1)[j]
                return interpolate(h, x, y1, y2, t1, t2)
            }
        }
        return 0.0 // should never reach here
    }

    public override fun getSlope(t: Double, v: DoubleArray) {
        var t: Double = t
        val n: Int = timePoints.size
        val dim: Int = mY.get(0).count()
        if (t <= timePoints.get(0)) {
            t = timePoints.get(0)
        } else if (t >= timePoints.get(n - 1)) {
            t = timePoints.get(n - 1)
        }
        for (i in 0 until n - 1) {
            if (t <= timePoints.get(i + 1)) {
                val h: Double = timePoints.get(i + 1) - timePoints.get(i)
                val x: Double = (t - timePoints.get(i)) / h
                for (j in 0 until dim) {
                    val y1: Double = mY.get(i)[j]
                    val y2: Double = mY.get(i + 1)[j]
                    val t1: Double = mTangent.get(i)[j]
                    val t2: Double = mTangent.get(i + 1)[j]
                    v[j] = diff(h, x, y1, y2, t1, t2) / h
                }
                break
            }
        }
        return
    }

    public override fun getSlope(t: Double, j: Int): Double {
        var t: Double = t
        val n: Int = timePoints.size
        if (t < timePoints.get(0)) {
            t = timePoints.get(0)
        } else if (t >= timePoints.get(n - 1)) {
            t = timePoints.get(n - 1)
        }
        for (i in 0 until n - 1) {
            if (t <= timePoints.get(i + 1)) {
                val h: Double = timePoints.get(i + 1) - timePoints.get(i)
                val x: Double = (t - timePoints.get(i)) / h
                val y1: Double = mY.get(i)[j]
                val y2: Double = mY.get(i + 1)[j]
                val t1: Double = mTangent.get(i)[j]
                val t2: Double = mTangent.get(i + 1)[j]
                return diff(h, x, y1, y2, t1, t2) / h
            }
        }
        return 0.0 // should never reach here
    }

    companion object {
        private val TAG: String = "MonotonicCurveFit"

        /**
         * Cubic Hermite spline
         *
         * @return
         */
        private fun interpolate(h: Double, x: Double, y1: Double, y2: Double, t1: Double, t2: Double): Double {
            val x2: Double = x * x
            val x3: Double = x2 * x
            return ((((-2 * x3 * y2) + (3 * x2 * y2) + (2 * x3 * y1) - 3 * x2 * y1) + y1
                    + (h * t2 * x3) + (h * t1 * x3)) - (h * t2 * x2) - (2 * h * t1 * x2)
                    + h * t1 * x)
        }

        /**
         * Cubic Hermite spline slope differentiated
         *
         * @return
         */
        private fun diff(h: Double, x: Double, y1: Double, y2: Double, t1: Double, t2: Double): Double {
            val x2: Double = x * x
            return (((-6 * x2 * y2) + (6 * x * y2) + (6 * x2 * y1) - 6 * x * y1) + (3 * h * t2 * x2) + (
                    3 * h * t1 * x2)) - (2 * h * t2 * x) - (4 * h * t1 * x) + h * t1
        }

        /**
         * This builds a monotonic spline to be used as a wave function
         *
         * @param configString
         * @return
         */
        fun buildWave(configString: String?): MonotonicCurveFit {
            // done this way for efficiency
            val str: String? = configString
            val values: DoubleArray = DoubleArray(str!!.length / 2)
            var start: Int = configString!!.indexOf('(') + 1
            var off1: Int = configString.indexOf(',', start)
            var count: Int = 0
            while (off1 != -1) {
                val tmp: String = configString.substring(start, off1).trim({ it <= ' ' })
                values[count++] = tmp.toDouble()
                off1 = configString.indexOf(',', off1 + 1.also({ start = it }))
            }
            off1 = configString.indexOf(')', start)
            val tmp: String = configString.substring(start, off1).trim({ it <= ' ' })
            values[count++] = tmp.toDouble()
            return buildWave(values.copyOf(count))
        }

        private fun buildWave(values: DoubleArray): MonotonicCurveFit {
            val length: Int = values.size * 3 - 2
            val len: Int = values.size - 1
            val gap: Double = 1.0 / len
            val points: Array<DoubleArray> = Array(length, { DoubleArray(1) })
            val time: DoubleArray = DoubleArray(length)
            for (i in values.indices) {
                val v: Double = values.get(i)
                points.get(i + len)[0] = v
                time[i + len] = i * gap
                if (i > 0) {
                    points.get(i + len * 2)[0] = v + 1
                    time[i + len * 2] = i * gap + 1
                    points.get(i - 1)[0] = v - 1 - gap
                    time[i - 1] = i * gap + -1 - gap
                }
            }
            return MonotonicCurveFit(time, points)
        }
    }

    init {
        val N: Int = time.size
        val dim: Int = y.get(0).count()
        mSlopeTemp = DoubleArray(dim)
        val slope: Array<DoubleArray> = Array(N - 1, { DoubleArray(dim) }) // could optimize this out
        val tangent: Array<DoubleArray> = Array(N, { DoubleArray(dim) })
        for (j in 0 until dim) {
            for (i in 0 until N - 1) {
                val dt: Double = time.get(i + 1) - time.get(i)
                slope.get(i)[j] = (y.get(i + 1)[j] - y.get(i)[j]) / dt
                if (i == 0) {
                    tangent.get(i)[j] = slope.get(i)[j]
                } else {
                    tangent.get(i)[j] = (slope.get(i - 1)[j] + slope.get(i)[j]) * 0.5f
                }
            }
            tangent.get(N - 1)[j] = slope.get(N - 2)[j]
        }
        for (i in 0 until N - 1) {
            for (j in 0 until dim) {
                if (slope.get(i)[j] == 0.0) {
                    tangent.get(i)[j] = 0.0
                    tangent.get(i + 1)[j] = 0.0
                } else {
                    val a: Double = tangent.get(i)[j] / slope.get(i)[j]
                    val b: Double = tangent.get(i + 1)[j] / slope.get(i)[j]
                    val h: Double = hypot(a, b)
                    if (h > 9.0) {
                        val t: Double = 3.0 / h
                        tangent.get(i)[j] = t * a * slope.get(i)[j]
                        tangent.get(i + 1)[j] = t * b * slope.get(i)[j]
                    }
                }
            }
        }
        timePoints = time
        mY = y
        mTangent = tangent
    }
}