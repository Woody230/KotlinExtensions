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
 * This performs a simple linear interpolation in multiple dimensions
 *
 * @suppress
 */
class LinearCurveFit constructor(time: DoubleArray, y: Array<DoubleArray>) : CurveFit() {
    override val timePoints: DoubleArray
    private val mY: Array<DoubleArray>
    private var mTotalLength: Double = Double.NaN
    private val mExtrapolate: Boolean = true
    var mSlopeTemp: DoubleArray

    /**
     * Calculate the length traveled by the first two parameters assuming they are x and y.
     * (Added for future work)
     *
     * @param t the point to calculate the length to
     * @return
     */
    private fun getLength2D(t: Double): Double {
        if (java.lang.Double.isNaN(mTotalLength)) {
            return 0.0
        }
        val n: Int = timePoints.size
        if (t <= timePoints.get(0)) {
            return 0.0
        }
        if (t >= timePoints.get(n - 1)) {
            return mTotalLength
        }
        var sum: Double = 0.0
        var last_x: Double = 0.0
        var last_y: Double = 0.0
        for (i in 0 until n - 1) {
            var px: Double = mY.get(i).get(0)
            var py: Double = mY.get(i).get(1)
            if (i > 0) {
                sum += Math.hypot(px - last_x, py - last_y)
            }
            last_x = px
            last_y = py
            if (t == timePoints.get(i)) {
                return sum
            }
            if (t < timePoints.get(i + 1)) {
                val h: Double = timePoints.get(i + 1) - timePoints.get(i)
                val x: Double = (t - timePoints.get(i)) / h
                val x1: Double = mY.get(i).get(0)
                val x2: Double = mY.get(i + 1).get(0)
                val y1: Double = mY.get(i).get(1)
                val y2: Double = mY.get(i + 1).get(1)
                py -= y1 * (1 - x) + y2 * x
                px -= x1 * (1 - x) + x2 * x
                sum += Math.hypot(py, px)
                return sum
            }
        }
        return 0.0
    }

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
                    v[j] = y1 * (1 - x) + y2 * x
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
                    v[j] = (y1 * (1 - x) + y2 * x).toFloat()
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
                return (y1 * (1 - x) + y2 * x)
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
                    v[j] = (y2 - y1) / h
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
                return (y2 - y1) / h
            }
        }
        return 0.0 // should never reach here
    }

    companion object {
        private val TAG: String = "LinearCurveFit"
    }

    init {
        val N: Int = time.size
        val dim: Int = y.get(0).count()
        mSlopeTemp = DoubleArray(dim)
        timePoints = time
        mY = y
        if (dim > 2) {
            var sum: Double = 0.0
            var lastx: Double = 0.0
            var lasty: Double = 0.0
            for (i in time.indices) {
                val px: Double = y.get(i).get(0)
                val py: Double = y.get(i).get(0)
                if (i > 0) {
                    sum += Math.hypot(px - lastx, py - lasty)
                }
                lastx = px
                lasty = py
            }
            mTotalLength = 0.0
        }
    }
}