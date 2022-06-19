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

import androidx.constraintlayout.core.motion.key.MotionKeyPosition
import androidx.constraintlayout.core.motion.utils.ArcCurveFit
import androidx.constraintlayout.core.motion.utils.KeyCache
import androidx.constraintlayout.core.motion.utils.TypedValues
import org.junit.Assert
import kotlin.test.Test

class MotionKeyPositionTest {
    @Test
    fun testBasic() {
        Assert.assertEquals(2, (1 + 1).toLong())
    }

    @Test
    fun keyPosition1() {
        val mw1 = MotionWidget()
        val mw2 = MotionWidget()
        val res = MotionWidget()
        val cache = KeyCache()
        mw1.setBounds(0, 0, 30, 40)
        mw2.setBounds(400, 400, 430, 440)
        // mw1.motion.mPathMotionArc = MotionWidget.A
        val motion = Motion(mw1)
        motion.setPathMotionArc(ArcCurveFit.ARC_START_VERTICAL)
        motion.setStart(mw1)
        motion.setEnd(mw2)
        motion.setup(1000, 1000, 1f, 1000000)
        if (DEBUG) {
            var p = 0f
            while (p <= 1) {
                motion.interpolate(res, p, (1000000 + (p * 100).toInt()).toLong(), cache)
                println(res)
                p += 0.1f
            }
        }
        motion.interpolate(res, 0.5f, (1000000 + 1000).toLong(), cache)
        val left = (0.5 + 400 * (1 - Math.sqrt(0.5))).toInt()
        val top = (0.5 + 400 * Math.sqrt(0.5)).toInt()
        Assert.assertEquals(left.toLong(), res.left.toLong())
        Assert.assertEquals(147, res.right.toLong())
        Assert.assertEquals(top.toDouble(), res.top.toDouble(), 0.01)
        Assert.assertEquals((top + 40).toLong(), res.bottom.toLong())
    }

    @Test
    fun keyPosition2() {
        val mw1 = MotionWidget()
        val mw2 = MotionWidget()
        val res = MotionWidget()
        val cache = KeyCache()
        mw1.setBounds(0, 0, 30, 40)
        mw2.setBounds(400, 400, 430, 440)
        // mw1.motion.mPathMotionArc = MotionWidget.A
        val motion = Motion(mw1)
        motion.setPathMotionArc(ArcCurveFit.ARC_START_HORIZONTAL)
        motion.setStart(mw1)
        motion.setEnd(mw2)
        motion.setup(1000, 1000, 2f, 1000000)
        motion.interpolate(res, 0.5f, (1000000 + (0.5 * 100).toInt()).toLong(), cache)
        println("0.5 $res")
        if (DEBUG) {
            var p = 0f
            while (p <= 1) {
                motion.interpolate(res, p, (1000000 + (p * 100).toInt()).toLong(), cache)
                println("$res ,     $p")
                p += 0.01f
            }
        }
        motion.interpolate(res, 0.5f, (1000000 + 1000).toLong(), cache)
        Assert.assertEquals(283, res.left.toLong())
        Assert.assertEquals(313, res.right.toLong())
        Assert.assertEquals(117, res.top.toLong())
        Assert.assertEquals(157, res.bottom.toLong())
    }

    @Test
    fun keyPosition3() {
        val mw1 = MotionWidget()
        val mw2 = MotionWidget()
        val res = MotionWidget()
        val cache = KeyCache()
        mw1.setBounds(0, 0, 30, 40)
        mw2.setBounds(400, 400, 460, 480)
        val keyPosition = MotionKeyPosition()
        keyPosition.framePosition = 30
        keyPosition.setValue(TypedValues.PositionType.TYPE_PERCENT_X, 0.3f)
        keyPosition.setValue(TypedValues.PositionType.TYPE_PERCENT_Y, 0.3f)
        val keyPosition2 = MotionKeyPosition()
        keyPosition2.framePosition = 88
        keyPosition2.setValue(TypedValues.PositionType.TYPE_PERCENT_X, .9f)
        keyPosition2.setValue(TypedValues.PositionType.TYPE_PERCENT_Y, 0.5f)

        // mw1.motion.mPathMotionArc = MotionWidget.A
        val motion = Motion(mw1)
        //  motion.setPathMotionArc(ArcCurveFit.ARC_START_HORIZONTAL);
        motion.setStart(mw1)
        motion.setEnd(mw2)
        motion.addKey(keyPosition)
        motion.addKey(keyPosition2)
        motion.setup(1000, 1000, 2f, 1000000)
        motion.interpolate(res, 0.5f, (1000000 + (0.5 * 100).toInt()).toLong(), cache)
        println("0.5 $res")
        if (DEBUG) {
            var str = ""
            var p = 0f
            while (p <= 1) {
                motion.interpolate(res, p, (1000000 + (p * 100).toInt()).toLong(), cache)
                str += """
                    $res
                    
                    """.trimIndent()
                p += 0.01f
            }
            //Utils.socketSend(str)
        }
        motion.interpolate(res, 0f, (1000000 + 1000).toLong(), cache)
        Assert.assertEquals("0, 0, 30, 40", res.toString())
        motion.interpolate(res, 0.2f, (1000000 + 1000).toLong(), cache)
        Assert.assertEquals("80, 86, 116, 134", res.toString())
        motion.interpolate(res, 0.3f, (1000000 + 1000).toLong(), cache)
        Assert.assertEquals("120, 120, 159, 172", res.toString())
        motion.interpolate(res, 0.5f, (1000000 + 1000).toLong(), cache)
        Assert.assertEquals("204, 120, 249, 180", res.toString())
        motion.interpolate(res, 0.7f, (1000000 + 1000).toLong(), cache)
        Assert.assertEquals("289, 106, 339, 174", res.toString())
        motion.interpolate(res, 0.9f, (1000000 + 1000).toLong(), cache)
        Assert.assertEquals("367, 215, 424, 291", res.toString())
        motion.interpolate(res, 1f, (1000000 + 1000).toLong(), cache)
        Assert.assertEquals("400, 400, 460, 480", res.toString())
    }

    @Test
    fun keyPosition4() {
        val mw1 = MotionWidget()
        val mw2 = MotionWidget()
        val res = MotionWidget()
        val cache = KeyCache()
        val keyPosition = MotionKeyPosition()
        mw1.setBounds(0, 0, 30, 40)
        mw2.setBounds(400, 400, 460, 480)
        keyPosition.framePosition = 20
        keyPosition.setValue(TypedValues.PositionType.TYPE_PERCENT_X, 1f)
        keyPosition.setValue(TypedValues.PositionType.TYPE_PERCENT_Y, 0.5f)
        keyPosition.setValue(TypedValues.PositionType.TYPE_PERCENT_HEIGHT, 0.2f)
        keyPosition.setValue(TypedValues.PositionType.TYPE_PERCENT_WIDTH, 1f)
        // mw1.motion.mPathMotionArc = MotionWidget.A
        val motion = Motion(mw1)
        //  motion.setPathMotionArc(ArcCurveFit.ARC_START_HORIZONTAL);
        motion.setStart(mw1)
        motion.setEnd(mw2)
        motion.addKey(keyPosition)
        motion.setup(1000, 1000, 2f, 1000000)
        motion.interpolate(res, 0.5f, (1000000 + (0.5 * 100).toInt()).toLong(), cache)
        println("0.5 $res")
        if (DEBUG) {
            var p = 0f
            while (p <= 1) {
                motion.interpolate(res, p, (1000000 + (p * 100).toInt()).toLong(), cache)
                println("$res ,     $p")
                p += 0.01f
            }
        }
        motion.interpolate(res, 0.5f, (1000000 + 1000).toLong(), cache)
        Assert.assertEquals("400, 325, 460, 385", res.toString())
    }

    internal inner class Scene {
        var mw1 = MotionWidget()
        var mw2 = MotionWidget()
        var res = MotionWidget()
        var cache = KeyCache()
        var motion: Motion
        var progress = 0f
        fun setup() {
            motion.setStart(mw1)
            motion.setEnd(mw2)
            motion.setup(1000, 1000, 1f, 1000000)
        }

        fun sample(r: Runnable) {
            for (p in 0..10) {
                progress = p * 0.1f
                motion.interpolate(res, progress, (1000000 + (p * 100)).toLong(), cache)
                r.run()
            }
        }

        init {
            motion = Motion(mw1)
            mw1.setBounds(0, 0, 30, 40)
            mw2.setBounds(400, 400, 430, 440)
        }
    }

    @Test
    fun keyPosition3x() {
        val s: Scene = Scene()
        val cache = KeyCache()
        val frames = intArrayOf(25, 50, 75)
        val percentX = floatArrayOf(0.1f, 0.8f, 0.1f)
        val percentY = floatArrayOf(0.4f, 0.8f, 0.0f)
        for (i in frames.indices) {
            val keyPosition = MotionKeyPosition()
            keyPosition.framePosition = frames[i]
            keyPosition.setValue(TypedValues.PositionType.TYPE_PERCENT_X, percentX[i])
            keyPosition.setValue(TypedValues.PositionType.TYPE_PERCENT_Y, percentY[i])
            s.motion.addKey(keyPosition)
        }
        s.setup()
        s.motion.interpolate(s.res, 0.5f, (1000000 + (0.5 * 100).toInt()).toLong(), cache)
        println("0.5 " + s.res)
        if (DEBUG) {
            s.sample { println(s.progress.toString() + " ,     " + s.res) }
        }
        s.motion.interpolate(s.res, 0.5f, (1000000 + (0.5 * 100).toInt()).toLong(), cache)
        println("0.5 " + s.res)
        Assert.assertEquals("320, 320, 350, 360", s.res.toString())
    }

    companion object {
        private const val DEBUG = false
    }
}