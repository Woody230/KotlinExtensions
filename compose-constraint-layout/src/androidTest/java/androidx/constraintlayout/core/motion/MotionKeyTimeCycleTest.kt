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

import androidx.constraintlayout.core.motion.key.MotionKeyTimeCycle
import androidx.constraintlayout.core.motion.utils.*
import org.junit.Assert
import kotlin.test.Test

class MotionKeyTimeCycleTest {
    inner class Scene {
        var mw1 = MotionWidget()
        var mw2 = MotionWidget()
        var res = MotionWidget()
        var cache = KeyCache()
        var motion: Motion
        fun setup() {
            motion.setStart(mw1)
            motion.setEnd(mw2)
            motion.setup(1000, 1000, 1f, 1000000)
        }

        fun sample(r: Runnable) {
            for (p in 0..SAMPLES) {
                motion.interpolate(res, p * 0.1f, (1000000 + (p * 100)).toLong(), cache)
                r.run()
            }
        }

        init {
            motion = Motion(mw1)
            mw1.setBounds(0, 0, 30, 40)
            mw2.setBounds(400, 400, 430, 440)
            motion.setPathMotionArc(ArcCurveFit.ARC_START_VERTICAL)
        }
    }

    fun cycleBuilder(s: Scene, type: Int) {
        val amp = floatArrayOf(0f, 50f, 0f)
        val pos = intArrayOf(0, 50, 100)
        val period = floatArrayOf(0f, 2f, 0f)
        for (i in amp.indices) {
            val cycle = MotionKeyTimeCycle()
            cycle.setValue(type, amp[i])
            cycle.setValue(TypedValues.CycleType.TYPE_WAVE_PERIOD, period[i])
            cycle.framePosition = pos[i]
            s.motion.addKey(cycle)
        }
    }

    fun basicRunThrough(type: Int): Scene {
        val s: Scene = Scene()
        cycleBuilder(s, type)
        s.setup()
        if (DEBUG) {
            s.sample { println(s.res.getValueAttributes(type)) }
        }
        s.motion.interpolate(s.res, 0.5f, (1000000 + 1000).toLong(), s.cache)
        return s
    }

    @Test
    fun keyCycleRotationX() {
        val s = basicRunThrough(TypedValues.CycleType.TYPE_ROTATION_X)
        Assert.assertEquals(0.0, s.res.rotationX.toDouble(), 0.0001)
    }

    @Test
    fun keyCycleRotationY() {
        val s = basicRunThrough(TypedValues.CycleType.TYPE_ROTATION_Y)
        Assert.assertEquals(0.0, s.res.rotationY.toDouble(), 0.0001)
    }

    @Test
    fun keyCycleRotationZ() {
        val s = basicRunThrough(TypedValues.CycleType.TYPE_ROTATION_Z)
        Assert.assertEquals(0.0, s.res.rotationZ.toDouble(), 0.0001)
    }

    @Test
    fun keyCycleTranslationX() {
        val s = basicRunThrough(TypedValues.CycleType.TYPE_TRANSLATION_X)
        Assert.assertEquals(0.0, s.res.translationX.toDouble(), 0.0001)
    }

    @Test
    fun keyCycleTranslationY() {
        val s = basicRunThrough(TypedValues.CycleType.TYPE_TRANSLATION_Y)
        Assert.assertEquals(0.0, s.res.translationY.toDouble(), 0.0001)
    }

    @Test
    fun keyCycleTranslationZ() {
        val s = basicRunThrough(TypedValues.CycleType.TYPE_TRANSLATION_Z)
        Assert.assertEquals(0.0, s.res.translationZ.toDouble(), 0.0001)
    }

    @Test
    fun keyCycleScaleX() {
        val s = basicRunThrough(TypedValues.CycleType.TYPE_SCALE_X)
        Assert.assertEquals(0.0, s.res.scaleX.toDouble(), 0.0001)
    }

    @Test
    fun keyCycleScaleY() {
        val s = basicRunThrough(TypedValues.CycleType.TYPE_SCALE_Y)
        Assert.assertEquals(0.0, s.res.scaleY.toDouble(), 0.0001)
    }

    companion object {
        private const val DEBUG = true
        private const val SAMPLES = 30
    }
}