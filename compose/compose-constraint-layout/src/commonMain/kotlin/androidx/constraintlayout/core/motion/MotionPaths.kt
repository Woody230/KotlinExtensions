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
import androidx.constraintlayout.core.motion.utils.Easing
import androidx.constraintlayout.core.motion.utils.Easing.Companion.getInterpolator
import androidx.constraintlayout.utility.Utils.degrees
import androidx.constraintlayout.utility.Utils.radians
import kotlin.math.*

/**
 * This is used to capture and play back path of the layout.
 * It is used to set the bounds of the view (view.layout(l, t, r, b))
 *
 * @suppress
 */
class MotionPaths : Comparable<MotionPaths> {
    var mKeyFrameEasing: Easing? = null
    var mDrawPath = 0
    var time = 0f
    var position = 0f
    var x = 0f
    var y = 0f
    var width = 0f
    var height = 0f
    var mPathRotate = Float.NaN
    var mProgress = Float.NaN
    var mPathMotionArc = MotionWidget.UNSET
    var mAnimateRelativeTo = MotionWidget.UNSET
    var mRelativeAngle = Float.NaN
    var mRelativeToController: Motion? = null
    var customAttributes = HashMap<String, CustomVariable>()
    var mMode = 0 // how was this point computed 1=perpendicular 2=deltaRelative
    var mAnimateCircleAngleTo // since angles loop there are 4 ways we can pic direction
            = 0

    constructor() {}

    /**
     * set up with Cartesian
     *
     * @param c
     * @param startTimePoint
     * @param endTimePoint
     */
    fun initCartesian(c: MotionKeyPosition, startTimePoint: MotionPaths, endTimePoint: MotionPaths) {
        val position = c.framePosition / 100f
        val point = this
        point.time = position
        mDrawPath = c.mDrawPath
        val scaleWidth = if (c.mPercentWidth.isNaN()) position else c.mPercentWidth
        val scaleHeight = if (c.mPercentHeight.isNaN()) position else c.mPercentHeight
        val scaleX = endTimePoint.width - startTimePoint.width
        val scaleY = endTimePoint.height - startTimePoint.height
        point.position = point.time
        val path = position // the position on the path
        val startCenterX = startTimePoint.x + startTimePoint.width / 2
        val startCenterY = startTimePoint.y + startTimePoint.height / 2
        val endCenterX = endTimePoint.x + endTimePoint.width / 2
        val endCenterY = endTimePoint.y + endTimePoint.height / 2
        val pathVectorX = endCenterX - startCenterX
        val pathVectorY = endCenterY - startCenterY
        point.x = (startTimePoint.x + pathVectorX * path - scaleX * scaleWidth / 2)
        point.y = (startTimePoint.y + pathVectorY * path - scaleY * scaleHeight / 2)
        point.width = (startTimePoint.width + scaleX * scaleWidth)
        point.height = (startTimePoint.height + scaleY * scaleHeight)
        val dxdx = if (c.mPercentX.isNaN()) position else c.mPercentX
        val dydx: Float = if (c.mAltPercentY.isNaN()) 0f else c.mAltPercentY
        val dydy = if (c.mPercentY.isNaN()) position else c.mPercentY
        val dxdy: Float = if (c.mAltPercentX.isNaN()) 0f else c.mAltPercentX
        point.mMode = CARTESIAN
        point.x = (startTimePoint.x + pathVectorX * dxdx + pathVectorY * dxdy - scaleX * scaleWidth / 2)
        point.y = (startTimePoint.y + pathVectorX * dydx + pathVectorY * dydy - scaleY * scaleHeight / 2)
        point.mKeyFrameEasing = getInterpolator(c.mTransitionEasing)
        point.mPathMotionArc = c.mPathMotionArc
    }

    /**
     * takes the new keyPosition
     *
     * @param c
     * @param startTimePoint
     * @param endTimePoint
     */
    constructor(parentWidth: Int, parentHeight: Int, c: MotionKeyPosition, startTimePoint: MotionPaths, endTimePoint: MotionPaths) {
        if (startTimePoint.mAnimateRelativeTo != MotionWidget.UNSET) {
            initPolar(parentWidth, parentHeight, c, startTimePoint, endTimePoint)
            return
        }
        when (c.mPositionType) {
            MotionKeyPosition.TYPE_SCREEN -> {
                initScreen(parentWidth, parentHeight, c, startTimePoint, endTimePoint)
                return
            }
            MotionKeyPosition.TYPE_PATH -> {
                initPath(c, startTimePoint, endTimePoint)
                return
            }
            MotionKeyPosition.TYPE_CARTESIAN -> {
                initCartesian(c, startTimePoint, endTimePoint)
                return
            }
            else -> {
                initCartesian(c, startTimePoint, endTimePoint)
                return
            }
        }
    }

    fun initPolar(parentWidth: Int, parentHeight: Int, c: MotionKeyPosition, s: MotionPaths, e: MotionPaths) {
        val position = c.framePosition / 100f
        time = position
        mDrawPath = c.mDrawPath
        mMode = c.mPositionType // mode and type have same numbering scheme
        val scaleWidth = if (c.mPercentWidth.isNaN()) position else c.mPercentWidth
        val scaleHeight = if (c.mPercentHeight.isNaN()) position else c.mPercentHeight
        val scaleX = e.width - s.width
        val scaleY = e.height - s.height
        this.position = time
        width = (s.width + scaleX * scaleWidth)
        height = (s.height + scaleY * scaleHeight)
        val startfactor = 1 - position
        val endfactor = position
        when (c.mPositionType) {
            MotionKeyPosition.TYPE_SCREEN -> {
                x = if (c.mPercentX.isNaN()) position * (e.x - s.x) + s.x else c.mPercentX * min(scaleHeight, scaleWidth)
                y = if (c.mPercentY.isNaN()) position * (e.y - s.y) + s.y else c.mPercentY
            }
            MotionKeyPosition.TYPE_PATH -> {
                x = (if (c.mPercentX.isNaN()) position else c.mPercentX) * (e.x - s.x) + s.x
                y = (if (c.mPercentY.isNaN()) position else c.mPercentY) * (e.y - s.y) + s.y
            }
            MotionKeyPosition.TYPE_CARTESIAN -> {
                x = (if (c.mPercentX.isNaN()) position else c.mPercentX) * (e.x - s.x) + s.x
                y = (if (c.mPercentY.isNaN()) position else c.mPercentY) * (e.y - s.y) + s.y
            }
            else -> {
                x = (if (c.mPercentX.isNaN()) position else c.mPercentX) * (e.x - s.x) + s.x
                y = (if (c.mPercentY.isNaN()) position else c.mPercentY) * (e.y - s.y) + s.y
            }
        }
        mAnimateRelativeTo = s.mAnimateRelativeTo
        mKeyFrameEasing = getInterpolator(c.mTransitionEasing)
        mPathMotionArc = c.mPathMotionArc
    }

    fun setupRelative(mc: Motion?, relative: MotionPaths) {
        val dx = (x + width / 2 - relative.x - relative.width / 2).toDouble()
        val dy = (y + height / 2 - relative.y - relative.height / 2).toDouble()
        mRelativeToController = mc
        x = hypot(dy, dx).toFloat()
        y = if (mRelativeAngle.isNaN()) {
            (atan2(dy, dx) + PI / 2).toFloat()
        } else {
            radians(mRelativeAngle.toDouble()).toFloat()
        }
    }

    fun initScreen(parentWidth: Int, parentHeight: Int, c: MotionKeyPosition, startTimePoint: MotionPaths, endTimePoint: MotionPaths) {
        var parentWidth = parentWidth
        var parentHeight = parentHeight
        val position = c.framePosition / 100f
        val point = this
        point.time = position
        mDrawPath = c.mDrawPath
        val scaleWidth = if (c.mPercentWidth.isNaN()) position else c.mPercentWidth
        val scaleHeight = if (c.mPercentHeight.isNaN()) position else c.mPercentHeight
        val scaleX = endTimePoint.width - startTimePoint.width
        val scaleY = endTimePoint.height - startTimePoint.height
        point.position = point.time
        val path = position // the position on the path
        val startCenterX = startTimePoint.x + startTimePoint.width / 2
        val startCenterY = startTimePoint.y + startTimePoint.height / 2
        val endCenterX = endTimePoint.x + endTimePoint.width / 2
        val endCenterY = endTimePoint.y + endTimePoint.height / 2
        val pathVectorX = endCenterX - startCenterX
        val pathVectorY = endCenterY - startCenterY
        point.x = (startTimePoint.x + pathVectorX * path - scaleX * scaleWidth / 2)
        point.y = (startTimePoint.y + pathVectorY * path - scaleY * scaleHeight / 2)
        point.width = (startTimePoint.width + scaleX * scaleWidth)
        point.height = (startTimePoint.height + scaleY * scaleHeight)
        point.mMode = SCREEN
        if (!c.mPercentX.isNaN()) {
            parentWidth -= point.width.toInt()
            point.x = (c.mPercentX * parentWidth)
        }
        if (!c.mPercentY.isNaN()) {
            parentHeight -= point.height.toInt()
            point.y = (c.mPercentY * parentHeight)
        }
        point.mAnimateRelativeTo = mAnimateRelativeTo
        point.mKeyFrameEasing = getInterpolator(c.mTransitionEasing)
        point.mPathMotionArc = c.mPathMotionArc
    }

    fun initPath(c: MotionKeyPosition, startTimePoint: MotionPaths, endTimePoint: MotionPaths) {
        val position = c.framePosition / 100f
        val point = this
        point.time = position
        mDrawPath = c.mDrawPath
        val scaleWidth = if (c.mPercentWidth.isNaN()) position else c.mPercentWidth
        val scaleHeight = if (c.mPercentHeight.isNaN()) position else c.mPercentHeight
        val scaleX = endTimePoint.width - startTimePoint.width
        val scaleY = endTimePoint.height - startTimePoint.height
        point.position = point.time
        val path = if (c.mPercentX.isNaN()) position else c.mPercentX // the position on the path
        val startCenterX = startTimePoint.x + startTimePoint.width / 2
        val startCenterY = startTimePoint.y + startTimePoint.height / 2
        val endCenterX = endTimePoint.x + endTimePoint.width / 2
        val endCenterY = endTimePoint.y + endTimePoint.height / 2
        val pathVectorX = endCenterX - startCenterX
        val pathVectorY = endCenterY - startCenterY
        point.x = (startTimePoint.x + pathVectorX * path - scaleX * scaleWidth / 2)
        point.y = (startTimePoint.y + pathVectorY * path - scaleY * scaleHeight / 2)
        point.width = (startTimePoint.width + scaleX * scaleWidth)
        point.height = (startTimePoint.height + scaleY * scaleHeight)
        val perpendicular: Float = if (c.mPercentY.isNaN()) 0f else c.mPercentY // the position on the path
        val perpendicularX = -pathVectorY
        val normalX = perpendicularX * perpendicular
        val normalY = pathVectorX * perpendicular
        point.mMode = PERPENDICULAR
        point.x = (startTimePoint.x + pathVectorX * path - scaleX * scaleWidth / 2)
        point.y = (startTimePoint.y + pathVectorY * path - scaleY * scaleHeight / 2)
        point.x += normalX
        point.y += normalY
        point.mAnimateRelativeTo = mAnimateRelativeTo
        point.mKeyFrameEasing = getInterpolator(c.mTransitionEasing)
        point.mPathMotionArc = c.mPathMotionArc
    }

    private fun diff(a: Float, b: Float): Boolean {
        return if (a.isNaN() || b.isNaN()) {
            a.isNaN() != b.isNaN()
        } else abs(a - b) > 0.000001f
    }

    fun different(points: MotionPaths, mask: BooleanArray, custom: Array<String>?, arcMode: Boolean) {
        var c = 0
        val diffx = diff(x, points.x)
        val diffy = diff(y, points.y)
        mask[c++] = mask[c] or diff(position, points.position)
        mask[c++] = mask[c] or (diffx or diffy or arcMode)
        mask[c++] = mask[c] or (diffx or diffy or arcMode)
        mask[c++] = mask[c] or diff(width, points.width)
        mask[c++] = mask[c] or diff(height, points.height)
    }

    fun getCenter(p: Double, toUse: IntArray, data: DoubleArray, point: FloatArray, offset: Int) {
        var v_x = x
        var v_y = y
        var v_width = width
        var v_height = height
        val translationX = 0f
        val translationY = 0f
        for (i in toUse.indices) {
            val value = data[i].toFloat()
            when (toUse[i]) {
                OFF_X -> v_x = value
                OFF_Y -> v_y = value
                OFF_WIDTH -> v_width = value
                OFF_HEIGHT -> v_height = value
            }
        }
        if (mRelativeToController != null) {
            val pos = FloatArray(2)
            val vel = FloatArray(2)
            mRelativeToController!!.getCenter(p, pos, vel)
            val rx = pos[0]
            val ry = pos[1]
            val radius = v_x
            val angle = v_y
            // TODO Debug angle
            v_x = (rx + radius * sin(angle.toDouble()) - v_width / 2).toFloat()
            v_y = (ry - radius * cos(angle.toDouble()) - v_height / 2).toFloat()
        }
        point[offset] = v_x + v_width / 2 + translationX
        point[offset + 1] = v_y + v_height / 2 + translationY
    }

    fun getCenter(p: Double, toUse: IntArray, data: DoubleArray, point: FloatArray, vdata: DoubleArray, velocity: FloatArray) {
        var v_x = x
        var v_y = y
        var v_width = width
        var v_height = height
        var dv_x = 0f
        var dv_y = 0f
        var dv_width = 0f
        var dv_height = 0f
        val translationX = 0f
        val translationY = 0f
        for (i in toUse.indices) {
            val value = data[i].toFloat()
            val dvalue = vdata[i].toFloat()
            when (toUse[i]) {
                OFF_X -> {
                    v_x = value
                    dv_x = dvalue
                }
                OFF_Y -> {
                    v_y = value
                    dv_y = dvalue
                }
                OFF_WIDTH -> {
                    v_width = value
                    dv_width = dvalue
                }
                OFF_HEIGHT -> {
                    v_height = value
                    dv_height = dvalue
                }
            }
        }
        var dpos_x = dv_x + dv_width / 2
        var dpos_y = dv_y + dv_height / 2
        if (mRelativeToController != null) {
            val pos = FloatArray(2)
            val vel = FloatArray(2)
            mRelativeToController!!.getCenter(p, pos, vel)
            val rx = pos[0]
            val ry = pos[1]
            val radius = v_x
            val angle = v_y
            val dradius = dv_x
            val dangle = dv_y
            val drx = vel[0]
            val dry = vel[1]
            // TODO Debug angle
            v_x = (rx + radius * sin(angle.toDouble()) - v_width / 2).toFloat()
            v_y = (ry - radius * cos(angle.toDouble()) - v_height / 2).toFloat()
            dpos_x = (drx + dradius * sin(angle.toDouble()) + cos(angle.toDouble()) * dangle).toFloat()
            dpos_y = (dry - dradius * cos(angle.toDouble()) + sin(angle.toDouble()) * dangle).toFloat()
        }
        point[0] = v_x + v_width / 2 + translationX
        point[1] = v_y + v_height / 2 + translationY
        velocity[0] = dpos_x
        velocity[1] = dpos_y
    }

    fun getCenterVelocity(p: Double, toUse: IntArray, data: DoubleArray, point: FloatArray, offset: Int) {
        var v_x = x
        var v_y = y
        var v_width = width
        var v_height = height
        val translationX = 0f
        val translationY = 0f
        for (i in toUse.indices) {
            val value = data[i].toFloat()
            when (toUse[i]) {
                OFF_X -> v_x = value
                OFF_Y -> v_y = value
                OFF_WIDTH -> v_width = value
                OFF_HEIGHT -> v_height = value
            }
        }
        if (mRelativeToController != null) {
            val pos = FloatArray(2)
            val vel = FloatArray(2)
            mRelativeToController!!.getCenter(p, pos, vel)
            val rx = pos[0]
            val ry = pos[1]
            val radius = v_x
            val angle = v_y
            // TODO Debug angle
            v_x = (rx + radius * sin(angle.toDouble()) - v_width / 2).toFloat()
            v_y = (ry - radius * cos(angle.toDouble()) - v_height / 2).toFloat()
        }
        point[offset] = v_x + v_width / 2 + translationX
        point[offset + 1] = v_y + v_height / 2 + translationY
    }

    fun getBounds(toUse: IntArray, data: DoubleArray, point: FloatArray, offset: Int) {
        var v_x = x
        var v_y = y
        var v_width = width
        var v_height = height
        val translationX = 0f
        val translationY = 0f
        for (i in toUse.indices) {
            val value = data[i].toFloat()
            when (toUse[i]) {
                OFF_X -> v_x = value
                OFF_Y -> v_y = value
                OFF_WIDTH -> v_width = value
                OFF_HEIGHT -> v_height = value
            }
        }
        point[offset] = v_width
        point[offset + 1] = v_height
    }

    var mTempValue = DoubleArray(18)
    var mTempDelta = DoubleArray(18)

    // Called on the start Time Point
    fun setView(position: Float, view: MotionWidget, toUse: IntArray, data: DoubleArray, slope: DoubleArray, cycle: DoubleArray?) {
        var v_x = x
        var v_y = y
        var v_width = width
        var v_height = height
        var dv_x = 0f
        var dv_y = 0f
        var dv_width = 0f
        var dv_height = 0f
        var delta_path = 0f
        var path_rotate = Float.NaN
        var mod: String
        if (toUse.size != 0 && mTempValue.size <= toUse[toUse.size - 1]) {
            val scratch_data_length = toUse[toUse.size - 1] + 1
            mTempValue = DoubleArray(scratch_data_length)
            mTempDelta = DoubleArray(scratch_data_length)
        }
        mTempValue.fill(Double.NaN)
        for (i in toUse.indices) {
            mTempValue[toUse[i]] = data[i]
            mTempDelta[toUse[i]] = slope[i]
        }
        for (i in mTempValue.indices) {
            if (mTempValue[i].isNaN() && (cycle == null || cycle[i] == 0.0)) {
                continue
            }
            val deltaCycle = cycle?.get(i) ?: 0.0
            val value = (if (mTempValue[i].isNaN()) deltaCycle else mTempValue[i] + deltaCycle).toFloat()
            val dvalue = mTempDelta[i].toFloat()
            when (i) {
                OFF_POSITION -> delta_path = value
                OFF_X -> {
                    v_x = value
                    dv_x = dvalue
                }
                OFF_Y -> {
                    v_y = value
                    dv_y = dvalue
                }
                OFF_WIDTH -> {
                    v_width = value
                    dv_width = dvalue
                }
                OFF_HEIGHT -> {
                    v_height = value
                    dv_height = dvalue
                }
                OFF_PATH_ROTATE -> path_rotate = value
            }
        }
        if (mRelativeToController != null) {
            val pos = FloatArray(2)
            val vel = FloatArray(2)
            mRelativeToController!!.getCenter(position.toDouble(), pos, vel)
            val rx = pos[0]
            val ry = pos[1]
            val radius = v_x
            val angle = v_y
            val dradius = dv_x
            val dangle = dv_y
            val drx = vel[0]
            val dry = vel[1]

            // TODO Debug angle
            val pos_x = (rx + radius * sin(angle.toDouble()) - v_width / 2).toFloat()
            val pos_y = (ry - radius * cos(angle.toDouble()) - v_height / 2).toFloat()
            val dpos_x = (drx + dradius * sin(angle.toDouble()) + radius * cos(angle.toDouble()) * dangle).toFloat()
            val dpos_y = (dry - dradius * cos(angle.toDouble()) + radius * sin(angle.toDouble()) * dangle).toFloat()
            dv_x = dpos_x
            dv_y = dpos_y
            v_x = pos_x
            v_y = pos_y
            if (slope.size >= 2) {
                slope[0] = dpos_x.toDouble()
                slope[1] = dpos_y.toDouble()
            }
            if (!path_rotate.isNaN()) {
                val rot = (path_rotate + degrees(atan2(dv_y.toDouble(), dv_x.toDouble()))).toFloat()
                view.rotationZ = rot
            }
        } else {
            if (!path_rotate.isNaN()) {
                var rot = 0f
                val dx = dv_x + dv_width / 2
                val dy = dv_y + dv_height / 2
                if (DEBUG) {
                    //Utils.log(TAG, "dv_x       =" + dv_x);
                    //Utils.log(TAG, "dv_y       =" + dv_y);
                    //Utils.log(TAG, "dv_width   =" + dv_width);
                    //Utils.log(TAG, "dv_height  =" + dv_height);
                }
                rot += (path_rotate + degrees(atan2(dy.toDouble(), dx.toDouble()))).toFloat()
                view.rotationZ = rot
                if (DEBUG) {
                    //Utils.log(TAG, "Rotated " + rot + "  = " + dx + "," + dy);
                }
            }
        }

        // Todo: develop a concept of Float layout in MotionWidget widget.layout(float ...)
        var l = (0.5f + v_x).toInt()
        var t = (0.5f + v_y).toInt()
        var r = (0.5f + v_x + v_width).toInt()
        var b = (0.5f + v_y + v_height).toInt()
        var i_width = r - l
        var i_height = b - t
        if (OLD_WAY) { // This way may produce more stable with and height but risk gaps
            l = v_x.toInt()
            t = v_y.toInt()
            i_width = v_width.toInt()
            i_height = v_height.toInt()
            r = l + i_width
            b = t + i_height
        }

        // MotionWidget must do Android View measure if layout changes
        view.layout(l, t, r, b)
        if (DEBUG) {
            if (toUse.size > 0) {
                //Utils.log(TAG, "setView " + mod);
            }
        }
    }

    fun getRect(toUse: IntArray, data: DoubleArray, path: FloatArray, offset: Int) {
        var offset = offset
        var v_x = x
        var v_y = y
        var v_width = width
        var v_height = height
        var delta_path = 0f
        val rotation = 0f
        val alpha = 0f
        val rotationX = 0f
        val rotationY = 0f
        val scaleX = 1f
        val scaleY = 1f
        val pivotX = Float.NaN
        val pivotY = Float.NaN
        val translationX = 0f
        val translationY = 0f
        var mod: String
        for (i in toUse.indices) {
            val value = data[i].toFloat()
            when (toUse[i]) {
                OFF_POSITION -> delta_path = value
                OFF_X -> v_x = value
                OFF_Y -> v_y = value
                OFF_WIDTH -> v_width = value
                OFF_HEIGHT -> v_height = value
            }
        }
        if (mRelativeToController != null) {
            val rx = mRelativeToController!!.centerX
            val ry = mRelativeToController!!.centerY
            val radius = v_x
            val angle = v_y
            // TODO Debug angle
            v_x = (rx + radius * sin(angle.toDouble()) - v_width / 2).toFloat()
            v_y = (ry - radius * cos(angle.toDouble()) - v_height / 2).toFloat()
        }
        var x1 = v_x
        var y1 = v_y
        var x2 = v_x + v_width
        var y2 = y1
        var x3 = x2
        var y3 = v_y + v_height
        var x4 = x1
        var y4 = y3
        var cx = x1 + v_width / 2
        var cy = y1 + v_height / 2
        if (!pivotX.isNaN()) {
            cx = x1 + (x2 - x1) * pivotX
        }
        if (!pivotY.isNaN()) {
            cy = y1 + (y3 - y1) * pivotY
        }
        if (scaleX != 1f) {
            val midx = (x1 + x2) / 2
            x1 = (x1 - midx) * scaleX + midx
            x2 = (x2 - midx) * scaleX + midx
            x3 = (x3 - midx) * scaleX + midx
            x4 = (x4 - midx) * scaleX + midx
        }
        if (scaleY != 1f) {
            val midy = (y1 + y3) / 2
            y1 = (y1 - midy) * scaleY + midy
            y2 = (y2 - midy) * scaleY + midy
            y3 = (y3 - midy) * scaleY + midy
            y4 = (y4 - midy) * scaleY + midy
        }
        if (rotation != 0f) {
            val sin = sin(radians(rotation.toDouble())).toFloat()
            val cos = cos(radians(rotation.toDouble())).toFloat()
            val tx1 = xRotate(sin, cos, cx, cy, x1, y1)
            val ty1 = yRotate(sin, cos, cx, cy, x1, y1)
            val tx2 = xRotate(sin, cos, cx, cy, x2, y2)
            val ty2 = yRotate(sin, cos, cx, cy, x2, y2)
            val tx3 = xRotate(sin, cos, cx, cy, x3, y3)
            val ty3 = yRotate(sin, cos, cx, cy, x3, y3)
            val tx4 = xRotate(sin, cos, cx, cy, x4, y4)
            val ty4 = yRotate(sin, cos, cx, cy, x4, y4)
            x1 = tx1
            y1 = ty1
            x2 = tx2
            y2 = ty2
            x3 = tx3
            y3 = ty3
            x4 = tx4
            y4 = ty4
        }
        x1 += translationX
        y1 += translationY
        x2 += translationX
        y2 += translationY
        x3 += translationX
        y3 += translationY
        x4 += translationX
        y4 += translationY
        path[offset++] = x1
        path[offset++] = y1
        path[offset++] = x2
        path[offset++] = y2
        path[offset++] = x3
        path[offset++] = y3
        path[offset++] = x4
        path[offset++] = y4
    }

    /**
     * mAnchorDpDt
     *
     * @param locationX
     * @param locationY
     * @param mAnchorDpDt
     * @param toUse
     * @param deltaData
     * @param data
     */
    fun setDpDt(locationX: Float, locationY: Float, mAnchorDpDt: FloatArray, toUse: IntArray, deltaData: DoubleArray, data: DoubleArray) {
        var d_x = 0f
        var d_y = 0f
        var d_width = 0f
        var d_height = 0f
        val deltaScaleX = 0f
        val deltaScaleY = 0f
        val mPathRotate = Float.NaN
        val deltaTranslationX = 0f
        val deltaTranslationY = 0f
        var mod = " dd = "
        for (i in toUse.indices) {
            val deltaV = deltaData[i].toFloat()
            val value = data[i].toFloat()
            if (DEBUG) {
                mod += " , D" + names[toUse[i]] + "/Dt= " + deltaV
            }
            when (toUse[i]) {
                OFF_POSITION -> {}
                OFF_X -> d_x = deltaV
                OFF_Y -> d_y = deltaV
                OFF_WIDTH -> d_width = deltaV
                OFF_HEIGHT -> d_height = deltaV
            }
        }
        if (DEBUG) {
            if (toUse.size > 0) {
                //Utils.log(TAG, "setDpDt " + mod);
            }
        }
        val deltaX = d_x - deltaScaleX * d_width / 2
        val deltaY = d_y - deltaScaleY * d_height / 2
        val deltaWidth = d_width * (1 + deltaScaleX)
        val deltaHeight = d_height * (1 + deltaScaleY)
        val deltaRight = deltaX + deltaWidth
        val deltaBottom = deltaY + deltaHeight
        if (DEBUG) {
            if (toUse.size > 0) {

                /*
                Utils.log(TAG, "D x /dt           =" + d_x);
                Utils.log(TAG, "D y /dt           =" + d_y);
                Utils.log(TAG, "D width /dt       =" + d_width);
                Utils.log(TAG, "D height /dt      =" + d_height);
                Utils.log(TAG, "D deltaScaleX /dt =" + deltaScaleX);
                Utils.log(TAG, "D deltaScaleY /dt =" + deltaScaleY);
                Utils.log(TAG, "D deltaX /dt      =" + deltaX);
                Utils.log(TAG, "D deltaY /dt      =" + deltaY);
                Utils.log(TAG, "D deltaWidth /dt  =" + deltaWidth);
                Utils.log(TAG, "D deltaHeight /dt =" + deltaHeight);
                Utils.log(TAG, "D deltaRight /dt  =" + deltaRight);
                Utils.log(TAG, "D deltaBottom /dt =" + deltaBottom);
                Utils.log(TAG, "locationX         =" + locationX);
                Utils.log(TAG, "locationY         =" + locationY);
                Utils.log(TAG, "deltaTranslationX =" + deltaTranslationX);
                Utils.log(TAG, "deltaTranslationX =" + deltaTranslationX);*/
            }
        }
        mAnchorDpDt[0] = deltaX * (1 - locationX) + deltaRight * locationX + deltaTranslationX
        mAnchorDpDt[1] = deltaY * (1 - locationY) + deltaBottom * locationY + deltaTranslationY
    }

    fun fillStandard(data: DoubleArray, toUse: IntArray) {
        val set = floatArrayOf(position, x, y, width, height, mPathRotate)
        var c = 0
        for (i in toUse.indices) {
            if (toUse[i] < set.size) {
                data[c++] = set[toUse[i]].toDouble()
            }
        }
    }

    fun hasCustomData(name: String): Boolean {
        return customAttributes.containsKey(name)
    }

    fun getCustomDataCount(name: String): Int {
        val a = customAttributes[name] ?: return 0
        return a.numberOfInterpolatedValues()
    }

    fun getCustomData(name: String, value: DoubleArray, offset: Int): Int {
        var offset = offset
        val a = customAttributes[name]
        return if (a == null) {
            0
        } else if (a.numberOfInterpolatedValues() == 1) {
            value[offset] = a.valueToInterpolate.toDouble()
            1
        } else {
            val N = a.numberOfInterpolatedValues()
            val f = FloatArray(N)
            a.getValuesToInterpolate(f)
            for (i in 0 until N) {
                value[offset++] = f[i].toDouble()
            }
            N
        }
    }

    fun setBounds(x: Float, y: Float, w: Float, h: Float) {
        this.x = x
        this.y = y
        width = w
        height = h
    }

    override fun compareTo(o: MotionPaths): Int {
        return position.compareTo(o.position)
    }

    fun applyParameters(c: MotionWidget) {
        val point = this
        point.mKeyFrameEasing = getInterpolator(c.motion.mTransitionEasing)
        point.mPathMotionArc = c.motion.mPathMotionArc
        point.mAnimateRelativeTo = c.motion.mAnimateRelativeTo
        point.mPathRotate = c.motion.mPathRotate
        point.mDrawPath = c.motion.mDrawPath
        point.mAnimateCircleAngleTo = c.motion.mAnimateCircleAngleTo
        point.mProgress = c.propertySet.mProgress
        point.mRelativeAngle = 0f // c.layout.circleAngle;
        val at = c.customAttributeNames
        for (s in at) {
            val attr = c.getCustomAttribute(s)
            if (attr != null && attr.isContinuous) {
                customAttributes[s] = attr
            }
        }
    }

    fun configureRelativeTo(toOrbit: Motion) {
        val p = toOrbit.getPos(mProgress.toDouble()) // get the position in the orbit
    }

    companion object {
        const val TAG = "MotionPaths"
        const val DEBUG = false
        const val OLD_WAY = false // the computes the positions the old way
        const val OFF_POSITION = 0
        const val OFF_X = 1
        const val OFF_Y = 2
        const val OFF_WIDTH = 3
        const val OFF_HEIGHT = 4
        const val OFF_PATH_ROTATE = 5

        // mode and type have same numbering scheme
        const val PERPENDICULAR = MotionKeyPosition.TYPE_PATH
        const val CARTESIAN = MotionKeyPosition.TYPE_CARTESIAN
        const val SCREEN = MotionKeyPosition.TYPE_SCREEN
        var names = arrayOf("position", "x", "y", "width", "height", "pathRotate")
        private fun xRotate(sin: Float, cos: Float, cx: Float, cy: Float, x: Float, y: Float): Float {
            var x = x
            var y = y
            x = x - cx
            y = y - cy
            return x * cos - y * sin + cx
        }

        private fun yRotate(sin: Float, cos: Float, cx: Float, cy: Float, x: Float, y: Float): Float {
            var x = x
            var y = y
            x = x - cx
            y = y - cy
            return x * sin + y * cos + cy
        }
    }
}