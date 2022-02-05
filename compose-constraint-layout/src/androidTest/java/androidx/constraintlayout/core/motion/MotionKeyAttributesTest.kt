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

import androidx.constraintlayout.core.motion.utils.TypedValues.AttributesType.Companion.TYPE_ROTATION_Z
import androidx.constraintlayout.core.motion.utils.TypedValues.AttributesType.Companion.TYPE_ROTATION_X
import androidx.constraintlayout.core.motion.utils.TypedValues.AttributesType.Companion.TYPE_ROTATION_Y
import androidx.constraintlayout.core.motion.utils.TypedValues.AttributesType.Companion.TYPE_TRANSLATION_X
import androidx.constraintlayout.core.motion.utils.TypedValues.AttributesType.Companion.TYPE_TRANSLATION_Y
import androidx.constraintlayout.core.motion.utils.TypedValues.AttributesType.Companion.TYPE_TRANSLATION_Z
import androidx.constraintlayout.core.motion.utils.TypedValues.AttributesType.Companion.TYPE_SCALE_X
import androidx.constraintlayout.core.motion.utils.TypedValues.AttributesType.Companion.TYPE_SCALE_Y

import androidx.constraintlayout.core.motion.key.MotionKeyAttributes
import androidx.constraintlayout.core.motion.utils.*
import org.junit.Assert
import kotlin.test.Test

class MotionKeyAttributesTest {
    @Test
    fun basic() {
        Assert.assertEquals(2, (1 + 1).toLong())
    }

    @Test
    fun attributes() {
        val mw1 = MotionWidget()
        val mw2 = MotionWidget()
        val res = MotionWidget()
        val cache = KeyCache()
        mw1.setBounds(0, 0, 30, 40)
        mw2.setBounds(400, 400, 430, 440)
        mw1.rotationZ = 0f
        mw2.rotationZ = 360f
        // mw1.motion.mPathMotionArc = MotionWidget.A
        val motion = Motion(mw1)
        motion.setPathMotionArc(ArcCurveFit.ARC_START_VERTICAL)
        motion.setStart(mw1)
        motion.setEnd(mw2)
        motion.setup(1000, 1000, 1f, 1000000)
        if (DEBUG) {
            for (p in 0..10) {
                motion.interpolate(res, p * 0.1f, (1000000 + (p * 100)).toLong(), cache)
                println((p * 0.1f).toString() + " " + res.rotationZ)
            }
        }
        motion.interpolate(res, 0.5f, (1000000 + 1000).toLong(), cache)
        Assert.assertEquals(180.0, res.rotationZ.toDouble(), 0.001)
    }

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
            for (p in 0..10) {
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

    fun basicRange(type: Int, start: Float, end: Float): Scene {
        val s: Scene = Scene()
        s.mw1.setValue(type, start)
        s.mw2.setValue(type, end)
        s.setup()
        if (DEBUG) {
            s.sample { println(s.res.rotationZ) }
        }
        s.motion.interpolate(s.res, 0.5f, (1000000 + 1000).toLong(), s.cache)
        return s
    }

    @Test
    fun checkRotationZ() {
        val s = basicRange(TYPE_ROTATION_Z, 0f, 360f)
        Assert.assertEquals(180.0, s.res.rotationZ.toDouble(), 0.001)
    }

    @Test
    fun checkRotationX() {
        val s = basicRange(TYPE_ROTATION_X, 0f, 100f)
        Assert.assertEquals(50.0, s.res.rotationX.toDouble(), 0.001)
    }

    @Test
    fun checkRotationY() {
        val s = basicRange(TYPE_ROTATION_Y, 0f, 50f)
        Assert.assertEquals(25.0, s.res.rotationY.toDouble(), 0.001)
    }

    @Test
    fun checkTranslateX() {
        val s = basicRange(TYPE_TRANSLATION_X, 0f, 30f)
        Assert.assertEquals(15.0, s.res.translationX.toDouble(), 0.001)
    }

    @Test
    fun checkTranslateY() {
        val s = basicRange(TYPE_TRANSLATION_Y, 0f, 40f)
        Assert.assertEquals(20.0, s.res.translationY.toDouble(), 0.001)
    }

    @Test
    fun checkTranslateZ() {
        val s = basicRange(TYPE_TRANSLATION_Z, 0f, 18f)
        Assert.assertEquals(9.0, s.res.translationZ.toDouble(), 0.001)
    }

    @Test
    fun checkScaleX() {
        val s = basicRange(TYPE_SCALE_X, 1f, 19f)
        Assert.assertEquals(10.0, s.res.scaleX.toDouble(), 0.001)
    }

    @Test
    fun checkScaleY() {
        val s = basicRange(TYPE_SCALE_Y, 1f, 3f)
        Assert.assertEquals(2.0, s.res.scaleY.toDouble(), 0.001)
    }

    @Test
    fun attributesRotateX() {
        val s: Scene = Scene()
        s.mw1.rotationX = -10f
        s.mw2.rotationX = 10f
        s.setup()
        if (DEBUG) {
            s.sample { println(s.res.rotationX) }
        }
        s.motion.interpolate(s.res, 0.5f, (1000000 + 1000).toLong(), s.cache)
        Assert.assertEquals(0.0, s.res.rotationX.toDouble(), 0.001)
    }

    @Test
    fun attributesRotateY() {
        val s: Scene = Scene()
        s.mw1.rotationY = -10f
        s.mw2.rotationY = 10f
        s.setup()
        if (DEBUG) {
            s.sample { println(s.res.rotationY) }
        }
        s.motion.interpolate(s.res, 0.5f, (1000000 + 1000).toLong(), s.cache)
        Assert.assertEquals(0.0, s.res.rotationY.toDouble(), 0.001)
    }

    @Test
    fun attributesRotateZ() {
        val s: Scene = Scene()
        s.mw1.rotationZ = -10f
        s.mw2.rotationZ = 10f
        s.setup()
        if (DEBUG) {
            s.sample { println(s.res.rotationZ) }
        }
        s.motion.interpolate(s.res, 0.5f, (1000000 + 1000).toLong(), s.cache)
        Assert.assertEquals(0.0, s.res.rotationZ.toDouble(), 0.001)
    }

    @Test
    fun attributesTranslateX() {
        val s: Scene = Scene()
        s.mw1.translationX = -10f
        s.mw2.translationX = 40f
        s.setup()
        if (DEBUG) {
            s.sample { println(s.res.translationX) }
        }
        s.motion.interpolate(s.res, 0.5f, (1000000 + 1000).toLong(), s.cache)
        Assert.assertEquals(15.0, s.res.translationX.toDouble(), 0.001)
    }

    @Test
    fun attributesTranslateY() {
        val s: Scene = Scene()
        s.mw1.translationY = -10f
        s.mw2.translationY = 40f
        s.setup()
        if (DEBUG) {
            s.sample { println(s.res.translationY) }
        }
        s.motion.interpolate(s.res, 0.5f, (1000000 + 1000).toLong(), s.cache)
        Assert.assertEquals(15.0, s.res.translationY.toDouble(), 0.001)
    }

    @Test
    fun attributesTranslateZ() {
        val s: Scene = Scene()
        s.mw1.translationZ = -10f
        s.mw2.translationZ = 40f
        s.setup()
        if (DEBUG) {
            s.sample { println(s.res.translationZ) }
        }
        s.motion.interpolate(s.res, 0.5f, (1000000 + 1000).toLong(), s.cache)
        Assert.assertEquals(15.0, s.res.translationZ.toDouble(), 0.001)
    }

    @Test
    fun attributesScaleX() {
        val s: Scene = Scene()
        s.mw1.scaleX = -10f
        s.mw2.scaleX = 40f
        s.setup()
        if (DEBUG) {
            s.sample { println(s.res.scaleX) }
        }
        s.motion.interpolate(s.res, 0.5f, (1000000 + 1000).toLong(), s.cache)
        Assert.assertEquals(15.0, s.res.scaleX.toDouble(), 0.001)
    }

    @Test
    fun attributesScaleY() {
        val s: Scene = Scene()
        s.mw1.scaleY = -10f
        s.mw2.scaleY = 40f
        s.setup()
        if (DEBUG) {
            s.sample { println(s.res.scaleY) }
        }
        s.motion.interpolate(s.res, 0.5f, (1000000 + 1000).toLong(), s.cache)
        Assert.assertEquals(15.0, s.res.scaleY.toDouble(), 0.001)
    }

    @Test
    fun attributesPivotX() {
        val s: Scene = Scene()
        s.mw1.pivotX = -10f
        s.mw2.pivotX = 40f
        s.setup()
        if (DEBUG) {
            s.sample { println(s.res.pivotX) }
        }
        s.motion.interpolate(s.res, 0.5f, (1000000 + 1000).toLong(), s.cache)
        Assert.assertEquals(15.0, s.res.pivotX.toDouble(), 0.001)
    }

    @Test
    fun attributesPivotY() {
        val s: Scene = Scene()
        s.mw1.pivotY = -10f
        s.mw2.pivotY = 40f
        s.setup()
        if (DEBUG) {
            s.sample { println(s.res.pivotY) }
        }
        s.motion.interpolate(s.res, 0.5f, (1000000 + 1000).toLong(), s.cache)
        Assert.assertEquals(15.0, s.res.pivotY.toDouble(), 0.001)
    }

    @Test
    fun keyFrameRotateX() {
        val s: Scene = Scene()
        s.mw1.rotationX = -10f
        s.mw2.rotationX = 10f
        val attribute = MotionKeyAttributes()
        attribute.setValue(TYPE_ROTATION_X, 23f)
        attribute.framePosition = 50
        s.motion.addKey(attribute)
        s.setup()
        if (DEBUG) {
            s.sample { println(s.res.rotationX) }
        }
        s.motion.interpolate(s.res, 0.5f, (1000000 + 1000).toLong(), s.cache)
        Assert.assertEquals(23.0, s.res.rotationX.toDouble(), 0.001)
    }

    @Test
    fun keyFrameRotateY() {
        val s: Scene = Scene()
        s.mw1.rotationY = -10f
        s.mw2.rotationY = 10f
        val attribute = MotionKeyAttributes()
        attribute.setValue(TYPE_ROTATION_Y, 23f)
        attribute.framePosition = 50
        s.motion.addKey(attribute)
        s.setup()
        if (DEBUG) {
            s.sample { println(s.res.rotationY) }
        }
        s.motion.interpolate(s.res, 0.5f, (1000000 + 1000).toLong(), s.cache)
        Assert.assertEquals(23.0, s.res.rotationY.toDouble(), 0.001)
    }

    @Test
    fun keyFrameRotateZ() {
        val s: Scene = Scene()
        s.mw1.rotationZ = -10f
        s.mw2.rotationZ = 10f
        val attribute = MotionKeyAttributes()
        attribute.setValue(TYPE_ROTATION_Z, 23f)
        attribute.framePosition = 50
        s.motion.addKey(attribute)
        s.setup()
        if (DEBUG) {
            s.sample { println(s.res.rotationZ) }
        }
        s.motion.interpolate(s.res, 0.5f, (1000000 + 1000).toLong(), s.cache)
        Assert.assertEquals(23.0, s.res.rotationZ.toDouble(), 0.001)
    }

    @Test
    fun keyFrameTranslationX() {
        val s: Scene = Scene()
        s.mw1.translationX = -10f
        s.mw2.translationX = 10f
        val attribute = MotionKeyAttributes()
        attribute.setValue(TYPE_TRANSLATION_X, 23f)
        attribute.framePosition = 50
        s.motion.addKey(attribute)
        s.setup()
        if (DEBUG) {
            s.sample { println(s.res.translationX) }
        }
        s.motion.interpolate(s.res, 0.5f, (1000000 + 1000).toLong(), s.cache)
        Assert.assertEquals(23.0, s.res.translationX.toDouble(), 0.001)
    }

    @Test
    fun keyFrameTranslationY() {
        val s: Scene = Scene()
        s.mw1.translationY = -10f
        s.mw2.translationY = 10f
        val attribute = MotionKeyAttributes()
        attribute.setValue(TYPE_TRANSLATION_Y, 23f)
        attribute.framePosition = 50
        s.motion.addKey(attribute)
        s.setup()
        if (DEBUG) {
            s.sample { println(s.res.translationY) }
        }
        s.motion.interpolate(s.res, 0.5f, (1000000 + 1000).toLong(), s.cache)
        Assert.assertEquals(23.0, s.res.translationY.toDouble(), 0.001)
    }

    @Test
    fun keyFrameTranslationZ() {
        val s: Scene = Scene()
        s.mw1.translationZ = -10f
        s.mw2.translationZ = 10f
        val attribute = MotionKeyAttributes()
        attribute.setValue(TYPE_TRANSLATION_Z, 23f)
        attribute.framePosition = 50
        s.motion.addKey(attribute)
        s.setup()
        if (DEBUG) {
            s.sample { println(s.res.translationZ) }
        }
        s.motion.interpolate(s.res, 0.5f, (1000000 + 1000).toLong(), s.cache)
        Assert.assertEquals(23.0, s.res.translationZ.toDouble(), 0.001)
    }

    @Test
    fun keyFrameScaleX() {
        val s: Scene = Scene()
        s.mw1.scaleX = -10f
        s.mw2.scaleX = 10f
        val attribute = MotionKeyAttributes()
        attribute.setValue(TYPE_SCALE_X, 23f)
        attribute.framePosition = 50
        s.motion.addKey(attribute)
        s.setup()
        if (DEBUG) {
            s.sample { println(s.res.scaleX) }
        }
        s.motion.interpolate(s.res, 0.5f, (1000000 + 1000).toLong(), s.cache)
        Assert.assertEquals(23.0, s.res.scaleX.toDouble(), 0.001)
    }

    @Test
    fun keyFrameScaleY() {
        val s: Scene = Scene()
        s.mw1.scaleY = -10f
        s.mw2.scaleY = 10f
        val attribute = MotionKeyAttributes()
        attribute.setValue(TYPE_SCALE_Y, 23f)
        attribute.framePosition = 50
        s.motion.addKey(attribute)
        s.setup()
        if (DEBUG) {
            s.sample { println(s.res.scaleY) }
        }
        s.motion.interpolate(s.res, 0.5f, (1000000 + 1000).toLong(), s.cache)
        Assert.assertEquals(23.0, s.res.scaleY.toDouble(), 0.001)
    }

    @Test
    fun keyFrameNoAttrRotateX() {
        val s: Scene = Scene()
        val attribute = MotionKeyAttributes()
        attribute.setValue(TYPE_ROTATION_X, 23f)
        attribute.framePosition = 50
        s.motion.addKey(attribute)
        s.setup()
        if (DEBUG) {
            s.sample { println(s.res.rotationX) }
        }
        s.motion.interpolate(s.res, 0.5f, (1000000 + 1000).toLong(), s.cache)
        Assert.assertEquals(23.0, s.res.rotationX.toDouble(), 0.001)
    }

    @Test
    fun keyFrameNoAttrRotateY() {
        val s: Scene = Scene()
        val attribute = MotionKeyAttributes()
        attribute.setValue(TYPE_ROTATION_Y, 23f)
        attribute.framePosition = 50
        s.motion.addKey(attribute)
        s.setup()
        if (DEBUG) {
            s.sample { println(s.res.rotationY) }
        }
        s.motion.interpolate(s.res, 0.5f, (1000000 + 1000).toLong(), s.cache)
        Assert.assertEquals(23.0, s.res.rotationY.toDouble(), 0.001)
    }

    @Test
    fun keyFrameNoAttrRotateZ() {
        val s: Scene = Scene()
        val attribute = MotionKeyAttributes()
        attribute.setValue(TYPE_ROTATION_Z, 23f)
        attribute.framePosition = 50
        s.motion.addKey(attribute)
        s.setup()
        if (DEBUG) {
            s.sample { println(s.res.rotationZ) }
        }
        s.motion.interpolate(s.res, 0.5f, (1000000 + 1000).toLong(), s.cache)
        Assert.assertEquals(23.0, s.res.rotationZ.toDouble(), 0.001)
    }

    @Test
    fun keyFrameNoAttrTranslationX() {
        val s: Scene = Scene()
        val attribute = MotionKeyAttributes()
        attribute.setValue(TYPE_TRANSLATION_X, 23f)
        attribute.framePosition = 50
        s.motion.addKey(attribute)
        s.setup()
        if (DEBUG) {
            s.sample { println(s.res.translationX) }
        }
        s.motion.interpolate(s.res, 0.5f, (1000000 + 1000).toLong(), s.cache)
        Assert.assertEquals(23.0, s.res.translationX.toDouble(), 0.001)
    }

    @Test
    fun keyFrameNoAttrTranslationY() {
        val s: Scene = Scene()
        val attribute = MotionKeyAttributes()
        attribute.setValue(TYPE_TRANSLATION_Y, 23f)
        attribute.framePosition = 50
        s.motion.addKey(attribute)
        s.setup()
        if (DEBUG) {
            s.sample { println(s.res.translationY) }
        }
        s.motion.interpolate(s.res, 0.5f, (1000000 + 1000).toLong(), s.cache)
        Assert.assertEquals(23.0, s.res.translationY.toDouble(), 0.001)
    }

    @Test
    fun keyFrameNoAttrTranslationZ() {
        val s: Scene = Scene()
        val attribute = MotionKeyAttributes()
        attribute.setValue(TYPE_TRANSLATION_Z, 23f)
        attribute.framePosition = 50
        s.motion.addKey(attribute)
        s.setup()
        if (DEBUG) {
            s.sample { println(s.res.translationZ) }
        }
        s.motion.interpolate(s.res, 0.5f, (1000000 + 1000).toLong(), s.cache)
        Assert.assertEquals(23.0, s.res.translationZ.toDouble(), 0.001)
    }

    @Test
    fun keyFrameNoAttrScaleX() {
        val s: Scene = Scene()
        val attribute = MotionKeyAttributes()
        attribute.setValue(TYPE_SCALE_X, 23f)
        attribute.framePosition = 50
        s.motion.addKey(attribute)
        s.setup()
        if (DEBUG) {
            s.sample { println(s.res.scaleX) }
        }
        s.motion.interpolate(s.res, 0.5f, (1000000 + 1000).toLong(), s.cache)
        Assert.assertEquals(23.0, s.res.scaleX.toDouble(), 0.001)
    }

    @Test
    fun keyFrameNoAttrScaleY() {
        val s: Scene = Scene()
        val attribute = MotionKeyAttributes()
        attribute.setValue(TYPE_SCALE_Y, 23f)
        attribute.framePosition = 50
        s.motion.addKey(attribute)
        s.setup()
        if (DEBUG) {
            s.sample { println(s.res.scaleY) }
        }
        s.motion.interpolate(s.res, 0.5f, (1000000 + 1000).toLong(), s.cache)
        Assert.assertEquals(23.0, s.res.scaleY.toDouble(), 0.001)
    }

    companion object {
        private const val DEBUG = false
    }
}