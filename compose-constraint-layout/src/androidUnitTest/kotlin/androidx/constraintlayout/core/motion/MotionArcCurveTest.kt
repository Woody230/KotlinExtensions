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
package androidx.constraintlayout.core.motion

import androidx.constraintlayout.core.motion.utils.ArcCurveFit
import androidx.constraintlayout.core.motion.utils.CurveFit.Companion.getArc
import org.junit.Assert
import kotlin.test.Test

class MotionArcCurveTest {
    @Test
    fun arcTest1() {
        val points = arrayOf(doubleArrayOf(0.0, 0.0), doubleArrayOf(1.0, 1.0), doubleArrayOf(2.0, 0.0))
        val time = doubleArrayOf(0.0, 5.0, 10.0)
        val mode = intArrayOf(
            ArcCurveFit.ARC_START_VERTICAL,
            ArcCurveFit.ARC_START_HORIZONTAL
        )
        val spline = getArc(mode, time, points)
        println("")
        for (i in time.indices) {
            Assert.assertEquals(points[i][0], spline.getPos(time[i], 0), 0.001)
            Assert.assertEquals(points[i][1], spline.getPos(time[i], 1), 0.001)
        }
        Assert.assertEquals(0.0, spline.getSlope(time[0] + 0.01, 0), 0.001)
        Assert.assertEquals(0.0, spline.getSlope(time[1] - 0.01, 1), 0.001)
        Assert.assertEquals(0.0, spline.getSlope(time[1] + 0.01, 1), 0.001)
        val dx = spline.getSlope((time[0] + time[1]) / 2, 0)
        val dy = spline.getSlope((time[0] + time[1]) / 2, 1)
        Assert.assertEquals(1.0, dx / dy, 0.001)
        val x = spline.getPos((time[0] + time[1]) / 2, 0)
        val y = spline.getPos((time[0] + time[1]) / 2, 1)
        Assert.assertEquals(1 - Math.sqrt(0.5), x, 0.001)
        Assert.assertEquals(Math.sqrt(0.5), y, 0.001)
    }
}