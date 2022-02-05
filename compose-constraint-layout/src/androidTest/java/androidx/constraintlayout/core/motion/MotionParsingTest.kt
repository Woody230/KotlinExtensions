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

import androidx.constraintlayout.core.motion.parse.KeyParser.parseAttributes
import androidx.constraintlayout.core.motion.key.MotionKeyAttributes
import androidx.constraintlayout.core.motion.utils.*
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class MotionParsingTest {
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

    var str = """
           {frame:22,
           target:'widget1',
           easing:'easeIn',
           curveFit:'spline',
           progress:0.3,
           alpha:0.2,
           elevation:0.7,
           rotationZ:23,
           rotationX:25.0,
           rotationY:27.0,
           pivotX:15,
           pivotY:17,
           pivotTarget:'32',
           pathRotate:23,
           scaleX:0.5,
           scaleY:0.7,
           translationX:5,
           translationY:7,
           translationZ:11,
           }
           """.trimIndent()

    @Test
    fun ParseKeAttributes() {
        val mka = MotionKeyAttributes()
        parseAttributes(str).applyDelta(mka)
        assertEquals(22, mka.framePosition)
        val attrs = HashSet<String>()
        mka.getAttributeNames(attrs)
        val split = str.replace("\n", "").split("[,:\\{\\}]".toRegex()).toTypedArray()
        val expectlist = ArrayList<String>()
        val exclude = HashSet(Arrays.asList("curveFit", "easing", "frame", "target", "pivotTarget"))
        var i = 1
        var j = 0
        while (i < split.size) {
            println(i.toString() + " " + split[i])
            if (!exclude.contains(split[i])) {
                expectlist.add(split[i])
            }
            i += 2
            j++
        }
        val expect = expectlist.toTypedArray()
        val result = attrs.toTypedArray()
        Arrays.sort(result)
        Arrays.sort(expect)
        Assert.assertEquals(Arrays.toString(expect), Arrays.toString(result))
    }

    companion object {
        private const val DEBUG = false
    }
}