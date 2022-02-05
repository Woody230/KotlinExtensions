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
 * This class translates a series of floating point values into a continuous
 * curve for use in an easing function including quantize functions
 * it is used with the "spline(0,0.3,0.3,0.5,...0.9,1)" it should start at 0 and end at one 1
 */
class StepCurve internal constructor(configString: String) : Easing() {
    var mCurveFit: MonotonicCurveFit
    public override fun getDiff(x: Double): Double {
        return mCurveFit.getSlope(x, 0)
    }

    public override fun get(x: Double): Double {
        return mCurveFit.getPos(x, 0)
    }

    companion object {
        private val DEBUG: Boolean = false
        private fun genSpline(str: String): MonotonicCurveFit {
            val wave: String = str
            val sp: Array<String> = wave.split("\\s+".toRegex()).toTypedArray()
            val values: DoubleArray = DoubleArray(sp.size)
            for (i in values.indices) {
                values[i] = sp.get(i).toDouble()
            }
            return genSpline(values)
        }

        private fun genSpline(values: DoubleArray): MonotonicCurveFit {
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
            /*
            if (DEBUG) {
                var t: String? = "t "
                var v: String? = "v "
                val df: DecimalFormat = DecimalFormat("#.00")
                for (i in time.indices) {
                    t += df.format(time.get(i)) + " "
                    v += df.format(points.get(i).get(0)) + " "
                }
                println(t)
                println(v)
            }*/
            val ms: MonotonicCurveFit = MonotonicCurveFit(time, points)
            println(" 0 " + ms.getPos(0.0, 0))
            println(" 1 " + ms.getPos(1.0, 0))
            return ms
        }
    }

    init {
        // done this way for efficiency
        str = configString
        val values: DoubleArray = DoubleArray(str.length / 2)
        var start: Int = configString.indexOf('(') + 1
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
        mCurveFit = genSpline(values.copyOf(count))
    }
}