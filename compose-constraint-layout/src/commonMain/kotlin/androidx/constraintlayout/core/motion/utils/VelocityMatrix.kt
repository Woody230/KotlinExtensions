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

import androidx.constraintlayout.utility.Utils.radians
import kotlin.math.cos
import kotlin.math.sin

/**
 * This is used to calculate the related velocity matrix for a post layout matrix
 *
 * @suppress
 */
class VelocityMatrix constructor() {
    var mDScaleX: Float = 0f
    var mDScaleY: Float = 0f
    var mDTranslateX: Float = 0f
    var mDTranslateY: Float = 0f
    var mDRotate: Float = 0f
    var mRotate: Float = 0f
    fun clear() {
        mDRotate = 0f
        mDTranslateY = mDRotate
        mDTranslateX = mDTranslateY
        mDScaleY = mDTranslateX
        mDScaleX = mDScaleY
    }

    fun setRotationVelocity(rot: SplineSet?, position: Float) {
        if (rot != null) {
            mDRotate = rot.getSlope(position)
            mRotate = rot.get(position)
        }
    }

    fun setTranslationVelocity(trans_x: SplineSet?, trans_y: SplineSet?, position: Float) {
        if (trans_x != null) {
            mDTranslateX = trans_x.getSlope(position)
        }
        if (trans_y != null) {
            mDTranslateY = trans_y.getSlope(position)
        }
    }

    fun setScaleVelocity(scale_x: SplineSet?, scale_y: SplineSet?, position: Float) {
        if (scale_x != null) {
            mDScaleX = scale_x.getSlope(position)
        }
        if (scale_y != null) {
            mDScaleY = scale_y.getSlope(position)
        }
    }

    fun setRotationVelocity(osc_r: KeyCycleOscillator?, position: Float) {
        if (osc_r != null) {
            mDRotate = osc_r.getSlope(position)
        }
    }

    fun setTranslationVelocity(osc_x: KeyCycleOscillator?, osc_y: KeyCycleOscillator?, position: Float) {
        if (osc_x != null) {
            mDTranslateX = osc_x.getSlope(position)
        }
        if (osc_y != null) {
            mDTranslateY = osc_y.getSlope(position)
        }
    }

    fun setScaleVelocity(osc_sx: KeyCycleOscillator?, osc_sy: KeyCycleOscillator?, position: Float) {
        if (osc_sx != null) {
            mDScaleX = osc_sx.getSlope(position)
        }
        if (osc_sy != null) {
            mDScaleY = osc_sy.getSlope(position)
        }
    }

    /**
     * Apply the transform a velocity vector
     *
     * @param locationX
     * @param locationY
     * @param width
     * @param height
     * @param mAnchorDpDt
     * @suppress
     */
    fun applyTransform(locationX: Float, locationY: Float, width: Int, height: Int, mAnchorDpDt: FloatArray) {
        var dx: Float = mAnchorDpDt.get(0)
        var dy: Float = mAnchorDpDt.get(1)
        val offx: Float = 2 * (locationX - 0.5f)
        val offy: Float = 2 * (locationY - 0.5f)
        dx += mDTranslateX
        dy += mDTranslateY
        dx += offx * mDScaleX
        dy += offy * mDScaleY
        val r: Float = radians(mRotate.toDouble()).toFloat()
        val dr: Float = radians(mDRotate.toDouble()).toFloat()
        dx += dr * (-width * offx * sin(r.toDouble()) - height * offy * cos(r.toDouble())).toFloat()
        dy += dr * (width * offx * cos(r.toDouble()) - height * offy * sin(r.toDouble())).toFloat()
        mAnchorDpDt[0] = dx
        mAnchorDpDt[1] = dy
    }

    companion object {
        private val TAG: String = "VelocityMatrix"
    }
}