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

import androidx.constraintlayout.core.motion.key.MotionKeyAttributes
import androidx.constraintlayout.core.motion.utils.ArcCurveFit
import androidx.constraintlayout.core.motion.utils.KeyCache
import androidx.constraintlayout.core.motion.utils.TypedValues
import org.junit.Assert
import org.junit.Assert.assertEquals
import java.text.DecimalFormat
import kotlin.test.Test

class MotionCustomKeyAttributesTest {
    var df = DecimalFormat("0.0")

    internal inner class Scene {
        var mw1 = MotionWidget()
        var mw2 = MotionWidget()
        var res = MotionWidget()
        var cache = KeyCache()
        var motion: Motion
        var pos = 0f
        fun setup() {
            motion.setStart(mw1)
            motion.setEnd(mw2)
            motion.setup(1000, 1000, 1f, 1000000)
        }

        fun sample(r: Runnable) {
            for (p in 0..10) {
                pos = p * 0.1f
                motion.interpolate(res, pos, (1000000 + (p * 100)).toLong(), cache)
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

    @Test
    fun customFloat() {
        val s: Scene = Scene()
        s.mw1.setCustomAttribute("bob", TypedValues.Custom.TYPE_FLOAT, 0f)
        s.mw2.setCustomAttribute("bob", TypedValues.Custom.TYPE_FLOAT, 1f)
        val mka = MotionKeyAttributes()
        mka.framePosition = 50
        mka.setCustomAttribute("bob", TypedValues.Custom.TYPE_FLOAT, 2f)
        s.motion.addKey(mka)
        s.setup()
        if (DEBUG) {
            s.motion.interpolate(s.res, 0.5f, (1000000 + 1000).toLong(), s.cache)
            s.sample { println(df.format(s.pos.toDouble()) + " " + s.res.getCustomAttribute("bob")) }
        }
        s.motion.interpolate(s.res, 0.5f, (1000000 + 1000).toLong(), s.cache)
        Assert.assertEquals(2.0f, s.res.getCustomAttribute("bob")!!.floatValue, 0.001f)
    }

    @Test
    fun customColor1() {
        val s: Scene = Scene()
        s.mw1.setCustomAttribute("fish", TypedValues.Custom.TYPE_COLOR, -0x1000000)
        s.mw2.setCustomAttribute("fish", TypedValues.Custom.TYPE_COLOR, -0x1)
        val mka = MotionKeyAttributes()
        mka.framePosition = 50
        mka.setCustomAttribute("fish", TypedValues.Custom.TYPE_COLOR, -0x1000000)
        s.motion.addKey(mka)
        s.setup()
        if (DEBUG) {
            s.sample { println(df.format(s.pos.toDouble()) + "\t" + s.res.getCustomAttribute("fish")) }
        }
        s.motion.interpolate(s.res, 0.5f, (1000000 + 1000).toLong(), s.cache)
        assertEquals(-0x1000000, s.res.getCustomAttribute("fish")!!.integerValue)
    }

    @Test
    fun customColor2() {
        val s: Scene = Scene()
        s.mw1.setCustomAttribute("fish", TypedValues.Custom.TYPE_COLOR, -0x10000)
        s.mw2.setCustomAttribute("fish", TypedValues.Custom.TYPE_COLOR, -0xffff01)
        val mka = MotionKeyAttributes()
        mka.framePosition = 50
        mka.setCustomAttribute("fish", TypedValues.Custom.TYPE_COLOR, -0xff0100)
        s.motion.addKey(mka)
        s.setup()
        if (DEBUG) {
            s.sample { println(df.format(s.pos.toDouble()) + " " + s.res.getCustomAttribute("fish")) }
        }
        s.motion.interpolate(s.res, 0.5f, (1000000 + 1000).toLong(), s.cache)
        assertEquals(-0xff0100, s.res.getCustomAttribute("fish")!!.integerValue)
    }

    companion object {
        private const val DEBUG = true
    }
}