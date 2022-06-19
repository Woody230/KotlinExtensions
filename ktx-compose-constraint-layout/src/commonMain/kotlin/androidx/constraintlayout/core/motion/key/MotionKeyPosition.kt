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
package androidx.constraintlayout.core.motion.key

import androidx.constraintlayout.core.motion.MotionWidget
import androidx.constraintlayout.core.motion.utils.FloatRect
import androidx.constraintlayout.core.motion.utils.SplineSet
import androidx.constraintlayout.core.motion.utils.TypedValues.Companion.TYPE_FRAME_POSITION
import androidx.constraintlayout.core.motion.utils.TypedValues.PositionType
import kotlin.jvm.JvmField
import kotlin.math.abs
import kotlin.math.hypot

class MotionKeyPosition : MotionKey() {
    @JvmField
    var mCurveFit: Int = MotionKey.Companion.UNSET
    @JvmField
    var mTransitionEasing: String? = null
    @JvmField
    var mPathMotionArc: Int = MotionKey.Companion.UNSET // -1 means not set
    @JvmField
    var mDrawPath = 0
    @JvmField
    var mPercentWidth = Float.NaN
    @JvmField
    var mPercentHeight = Float.NaN
    @JvmField
    var mPercentX = Float.NaN
    @JvmField
    var mPercentY = Float.NaN
    @JvmField
    var mAltPercentX = Float.NaN
    @JvmField
    var mAltPercentY = Float.NaN
    @JvmField
    var mPositionType = TYPE_CARTESIAN
    var positionX = Float.NaN
        private set
    var positionY = Float.NaN
        private set

    // TODO this needs the views dimensions to be accurate
    private fun calcScreenPosition(layoutWidth: Int, layoutHeight: Int) {
        val viewWidth = 0
        val viewHeight = 0
        positionX = (layoutWidth - viewWidth) * mPercentX + viewWidth / 2
        positionY = (layoutHeight - viewHeight) * mPercentX + viewHeight / 2
    }

    private fun calcPathPosition(
        start_x: Float, start_y: Float,
        end_x: Float, end_y: Float
    ) {
        val pathVectorX = end_x - start_x
        val pathVectorY = end_y - start_y
        val perpendicularX = -pathVectorY
        positionX = start_x + pathVectorX * mPercentX + perpendicularX * mPercentY
        positionY = start_y + pathVectorY * mPercentX + pathVectorX * mPercentY
    }

    private fun calcCartesianPosition(
        start_x: Float, start_y: Float,
        end_x: Float, end_y: Float
    ) {
        val pathVectorX = end_x - start_x
        val pathVectorY = end_y - start_y
        val dxdx: Float = if (mPercentX.isNaN()) 0f else mPercentX
        val dydx: Float = if (mAltPercentY.isNaN()) 0f else mAltPercentY
        val dydy: Float = if (mPercentY.isNaN()) 0f else mPercentY
        val dxdy: Float = if (mAltPercentX.isNaN()) 0f else mAltPercentX
        positionX = (start_x + pathVectorX * dxdx + pathVectorY * dxdy)
        positionY = (start_y + pathVectorX * dydx + pathVectorY * dydy)
    }

    fun positionAttributes(view: MotionWidget, start: FloatRect, end: FloatRect, x: Float, y: Float, attribute: Array<String?>, value: FloatArray) {
        when (mPositionType) {
            TYPE_PATH -> {
                positionPathAttributes(start, end, x, y, attribute, value)
                return
            }
            TYPE_SCREEN -> {
                positionScreenAttributes(view, start, end, x, y, attribute, value)
                return
            }
            TYPE_CARTESIAN -> {
                positionCartAttributes(start, end, x, y, attribute, value)
                return
            }
            else -> {
                positionCartAttributes(start, end, x, y, attribute, value)
                return
            }
        }
    }

    fun positionPathAttributes(start: FloatRect, end: FloatRect, x: Float, y: Float, attribute: Array<String?>, value: FloatArray) {
        val startCenterX = start.centerX()
        val startCenterY = start.centerY()
        val endCenterX = end.centerX()
        val endCenterY = end.centerY()
        val pathVectorX = endCenterX - startCenterX
        val pathVectorY = endCenterY - startCenterY
        val distance = hypot(pathVectorX.toDouble(), pathVectorY.toDouble()).toFloat()
        if (distance < 0.0001) {
            println("distance ~ 0")
            value[0] = 0f
            value[1] = 0f
            return
        }
        val dx = pathVectorX / distance
        val dy = pathVectorY / distance
        val perpendicular = (dx * (y - startCenterY) - (x - startCenterX) * dy) / distance
        val dist = (dx * (x - startCenterX) + dy * (y - startCenterY)) / distance
        if (attribute[0] != null) {
            if (PositionType.S_PERCENT_X == attribute[0]) {
                value[0] = dist
                value[1] = perpendicular
            }
        } else {
            attribute[0] = PositionType.S_PERCENT_X
            attribute[1] = PositionType.S_PERCENT_Y
            value[0] = dist
            value[1] = perpendicular
        }
    }

    fun positionScreenAttributes(view: MotionWidget, start: FloatRect, end: FloatRect, x: Float, y: Float, attribute: Array<String?>, value: FloatArray) {
        val startCenterX = start.centerX()
        val startCenterY = start.centerY()
        val endCenterX = end.centerX()
        val endCenterY = end.centerY()
        val pathVectorX = endCenterX - startCenterX
        val pathVectorY = endCenterY - startCenterY
        val viewGroup = view.parent as MotionWidget
        val width = viewGroup.width
        val height = viewGroup.height
        if (attribute[0] != null) { // they are saying what to use
            if (PositionType.S_PERCENT_X == attribute[0]) {
                value[0] = x / width
                value[1] = y / height
            } else {
                value[1] = x / width
                value[0] = y / height
            }
        } else { // we will use what we want to
            attribute[0] = PositionType.S_PERCENT_X
            value[0] = x / width
            attribute[1] = PositionType.S_PERCENT_Y
            value[1] = y / height
        }
    }

    fun positionCartAttributes(start: FloatRect, end: FloatRect, x: Float, y: Float, attribute: Array<String?>, value: FloatArray) {
        val startCenterX = start.centerX()
        val startCenterY = start.centerY()
        val endCenterX = end.centerX()
        val endCenterY = end.centerY()
        val pathVectorX = endCenterX - startCenterX
        val pathVectorY = endCenterY - startCenterY
        if (attribute[0] != null) { // they are saying what to use
            if (PositionType.S_PERCENT_X == attribute[0]) {
                value[0] = (x - startCenterX) / pathVectorX
                value[1] = (y - startCenterY) / pathVectorY
            } else {
                value[1] = (x - startCenterX) / pathVectorX
                value[0] = (y - startCenterY) / pathVectorY
            }
        } else { // we will use what we want to
            attribute[0] = PositionType.S_PERCENT_X
            value[0] = (x - startCenterX) / pathVectorX
            attribute[1] = PositionType.S_PERCENT_Y
            value[1] = (y - startCenterY) / pathVectorY
        }
    }

    fun intersects(layoutWidth: Int, layoutHeight: Int, start: FloatRect, end: FloatRect, x: Float, y: Float): Boolean {
        calcPosition(layoutWidth, layoutHeight, start.centerX(), start.centerY(), end.centerX(), end.centerY())
        return if (abs(x - positionX) < SELECTION_SLOPE
            && abs(y - positionY) < SELECTION_SLOPE
        ) {
            true
        } else false
    }

    override fun copy(src: MotionKey): MotionKey {
        super.copy(src)
        val k = src as MotionKeyPosition
        mTransitionEasing = k.mTransitionEasing
        mPathMotionArc = k.mPathMotionArc
        mDrawPath = k.mDrawPath
        mPercentWidth = k.mPercentWidth
        mPercentHeight = Float.NaN
        mPercentX = k.mPercentX
        mPercentY = k.mPercentY
        mAltPercentX = k.mAltPercentX
        mAltPercentY = k.mAltPercentY
        positionX = k.positionX
        positionY = k.positionY
        return this
    }

    override fun clone(): MotionKey? {
        return MotionKeyPosition().copy(this)
    }

    fun calcPosition(layoutWidth: Int, layoutHeight: Int, start_x: Float, start_y: Float, end_x: Float, end_y: Float) {
        when (mPositionType) {
            TYPE_SCREEN -> {
                calcScreenPosition(layoutWidth, layoutHeight)
                return
            }
            TYPE_PATH -> {
                calcPathPosition(start_x, start_y, end_x, end_y)
                return
            }
            TYPE_CARTESIAN -> {
                calcCartesianPosition(start_x, start_y, end_x, end_y)
                return
            }
            else -> {
                calcCartesianPosition(start_x, start_y, end_x, end_y)
                return
            }
        }
    }

    override fun getAttributeNames(attributes: HashSet<String>) {}
    override fun addValues(splines: HashMap<String, SplineSet>) {}
    override fun setValue(type: Int, value: Int): Boolean {
        when (type) {
            PositionType.TYPE_POSITION_TYPE -> mPositionType = value
            TYPE_FRAME_POSITION -> framePosition = value
            PositionType.TYPE_CURVE_FIT -> mCurveFit = value
            else -> return super.setValue(type, value)
        }
        return true
    }

    override fun setValue(type: Int, value: Float): Boolean {
        when (type) {
            PositionType.TYPE_PERCENT_WIDTH -> mPercentWidth = value
            PositionType.TYPE_PERCENT_HEIGHT -> mPercentHeight = value
            PositionType.TYPE_SIZE_PERCENT -> {
                mPercentWidth = value
                mPercentHeight = mPercentWidth
            }
            PositionType.TYPE_PERCENT_X -> mPercentX = value
            PositionType.TYPE_PERCENT_Y -> mPercentY = value
            else -> return super.setValue(type, value)
        }
        return true
    }

    override fun setValue(type: Int, value: String?): Boolean {
        mTransitionEasing = when (type) {
            PositionType.TYPE_TRANSITION_EASING -> value
            else -> return super.setValue(type, value)
        }
        return true
    }

    override fun getId(name: String?): Int {
        return PositionType.getId(name)
    }

    companion object {
        const val NAME = "KeyPosition"
        protected const val SELECTION_SLOPE = 20f
        const val TYPE_SCREEN = 2
        const val TYPE_PATH = 1
        const val TYPE_CARTESIAN = 0
        const val KEY_TYPE = 2
    }

    init {
        mType = KEY_TYPE
    }
}