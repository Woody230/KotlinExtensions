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
package androidx.constraintlayout.core.state

import androidx.constraintlayout.core.motion.Motion
import androidx.constraintlayout.core.motion.utils.TypedValues
import androidx.constraintlayout.core.motion.utils.TypedBundle
import androidx.constraintlayout.core.motion.utils.TypedValues.TransitionType
import androidx.constraintlayout.core.motion.utils.TypedValues.PositionType
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer
import androidx.constraintlayout.core.widgets.ConstraintWidget
import androidx.constraintlayout.core.motion.MotionWidget
import androidx.constraintlayout.core.motion.key.MotionKeyPosition
import androidx.constraintlayout.core.motion.key.MotionKeyAttributes
import androidx.constraintlayout.core.motion.key.MotionKeyCycle
import androidx.constraintlayout.core.motion.utils.Easing
import androidx.constraintlayout.core.motion.utils.KeyCache
import kotlinx.datetime.Clock

class Transition : TypedValues {
    var keyPositions = HashMap<Int, HashMap<String?, KeyPosition>>()
    private val state = HashMap<String?, WidgetState>()
    var mBundle = TypedBundle()

    // Interpolation
    private val mDefaultInterpolator = 0
    private var mDefaultInterpolatorString: String? = null
    private var mEasing: Easing? = null
    val autoTransition = 0
    private val mDuration = 400
    private var mStagger = 0.0f
    fun findPreviousPosition(target: String?, frameNumber: Int): KeyPosition? {
        var frameNumber = frameNumber
        while (frameNumber >= 0) {
            val map = keyPositions[frameNumber]
            if (map != null) {
                val keyPosition = map[target]
                if (keyPosition != null) {
                    return keyPosition
                }
            }
            frameNumber--
        }
        return null
    }

    fun findNextPosition(target: String?, frameNumber: Int): KeyPosition? {
        var frameNumber = frameNumber
        while (frameNumber <= 100) {
            val map = keyPositions[frameNumber]
            if (map != null) {
                val keyPosition = map[target]
                if (keyPosition != null) {
                    return keyPosition
                }
            }
            frameNumber++
        }
        return null
    }

    fun getNumberKeyPositions(frame: WidgetFrame): Int {
        var numKeyPositions = 0
        var frameNumber = 0
        while (frameNumber <= 100) {
            val map = keyPositions[frameNumber]
            if (map != null) {
                val keyPosition = map[frame.widget!!.stringId]
                if (keyPosition != null) {
                    numKeyPositions++
                }
            }
            frameNumber++
        }
        return numKeyPositions
    }

    fun getMotion(id: String?): Motion {
        return getWidgetState(id, null, 0).motionControl
    }

    fun fillKeyPositions(frame: WidgetFrame, x: FloatArray, y: FloatArray, pos: FloatArray) {
        var numKeyPositions = 0
        var frameNumber = 0
        while (frameNumber <= 100) {
            val map = keyPositions[frameNumber]
            if (map != null) {
                val keyPosition = map[frame.widget!!.stringId]
                if (keyPosition != null) {
                    x[numKeyPositions] = keyPosition.x
                    y[numKeyPositions] = keyPosition.y
                    pos[numKeyPositions] = keyPosition.frame.toFloat()
                    numKeyPositions++
                }
            }
            frameNumber++
        }
    }

    fun hasPositionKeyframes(): Boolean {
        return keyPositions.size > 0
    }

    fun setTransitionProperties(bundle: TypedBundle) {
        bundle.applyDelta(mBundle)
        bundle.applyDelta(this)
    }

    override fun setValue(id: Int, value: Int): Boolean {
        return false
    }

    override fun setValue(id: Int, value: Float): Boolean {
        if (id == TransitionType.TYPE_STAGGERED) {
            mStagger = value
        }
        return false
    }

    override fun setValue(id: Int, value: String?): Boolean {
        if (id == TransitionType.TYPE_INTERPOLATOR) {
            mEasing = Easing.getInterpolator(value.also { mDefaultInterpolatorString = it })
        }
        return false
    }

    override fun setValue(id: Int, value: Boolean): Boolean {
        return false
    }

    override fun getId(name: String?): Int {
        return 0
    }

    val isEmpty: Boolean
        get() = state.isEmpty()

    fun clear() {
        state.clear()
    }

    operator fun contains(key: String?): Boolean {
        return state.containsKey(key)
    }

    fun addKeyPosition(target: String?, bundle: TypedBundle) {
        getWidgetState(target, null, 0).setKeyPosition(bundle)
    }

    fun addKeyAttribute(target: String?, bundle: TypedBundle) {
        getWidgetState(target, null, 0).setKeyAttribute(bundle)
    }

    fun addKeyCycle(target: String?, bundle: TypedBundle) {
        getWidgetState(target, null, 0).setKeyCycle(bundle)
    }

    fun addKeyPosition(target: String?, frame: Int, type: Int, x: Float, y: Float) {
        val bundle = TypedBundle()
        bundle.add(PositionType.TYPE_POSITION_TYPE, 2)
        bundle.add(TypedValues.TYPE_FRAME_POSITION, frame)
        bundle.add(PositionType.TYPE_PERCENT_X, x)
        bundle.add(PositionType.TYPE_PERCENT_Y, y)
        getWidgetState(target, null, 0).setKeyPosition(bundle)
        val keyPosition = KeyPosition(target, frame, type, x, y)
        var map = keyPositions[frame]
        if (map == null) {
            map = HashMap()
            keyPositions[frame] = map
        }
        map[target] = keyPosition
    }

    fun addCustomFloat(state: Int, widgetId: String?, property: String?, value: Float) {
        val widgetState = getWidgetState(widgetId, null, state)
        val frame = widgetState.getFrame(state)
        frame.addCustomFloat(property!!, value)
    }

    fun addCustomColor(state: Int, widgetId: String?, property: String?, color: Int) {
        val widgetState = getWidgetState(widgetId, null, state)
        val frame = widgetState.getFrame(state)
        frame.addCustomColor(property!!, color)
    }

    fun updateFrom(container: ConstraintWidgetContainer, state: Int) {
        val children = container.children
        val count = children.size
        for (i in 0 until count) {
            val child = children[i]
            val widgetState = getWidgetState(child.stringId, null, state)
            widgetState.update(child, state)
        }
    }

    fun interpolate(parentWidth: Int, parentHeight: Int, progress: Float) {
        var progress = progress
        if (mEasing != null) {
            progress = mEasing!![progress.toDouble()].toFloat()
        }
        for (key in state.keys) {
            val widget = state[key]
            widget!!.interpolate(parentWidth, parentHeight, progress, this)
        }
    }

    fun getStart(id: String?): WidgetFrame? {
        val widgetState = state[id] ?: return null
        return widgetState.start
    }

    fun getEnd(id: String?): WidgetFrame? {
        val widgetState = state[id] ?: return null
        return widgetState.end
    }

    fun getInterpolated(id: String?): WidgetFrame? {
        val widgetState = state[id] ?: return null
        return widgetState.interpolated
    }

    fun getPath(id: String?): FloatArray {
        val widgetState = state[id]
        val duration = 1000
        val frames = duration / 16
        val mPoints = FloatArray(frames * 2)
        widgetState!!.motionControl.buildPath(mPoints, frames)
        return mPoints
    }

    fun getKeyFrames(id: String?, rectangles: FloatArray?, pathMode: IntArray?, position: IntArray?): Int {
        val widgetState = state[id]
        return widgetState!!.motionControl.buildKeyFrames(rectangles, pathMode, position)
    }

    private fun getWidgetState(widgetId: String): WidgetState? {
        return state[widgetId]
    }

    private fun getWidgetState(widgetId: String?, child: ConstraintWidget?, transitionState: Int): WidgetState {
        var widgetState = state[widgetId]
        if (widgetState == null) {
            widgetState = WidgetState()
            mBundle.applyDelta(widgetState.motionControl)
            state[widgetId] = widgetState
            if (child != null) {
                widgetState.update(child, transitionState)
            }
        }
        return widgetState
    }

    /**
     * Used in debug draw
     *
     * @param child
     * @return
     */
    fun getStart(child: ConstraintWidget): WidgetFrame {
        return getWidgetState(child.stringId, null, START).start
    }

    /**
     * Used in debug draw
     *
     * @param child
     * @return
     */
    fun getEnd(child: ConstraintWidget): WidgetFrame {
        return getWidgetState(child.stringId, null, END).end
    }

    /**
     * Used after the interpolation
     *
     * @param child
     * @return
     */
    fun getInterpolated(child: ConstraintWidget): WidgetFrame {
        return getWidgetState(child.stringId, null, INTERPOLATED).interpolated
    }

    val interpolator: Interpolator?
        get() = getInterpolator(mDefaultInterpolator, mDefaultInterpolatorString)

    internal class WidgetState {
        var start: WidgetFrame
        var end: WidgetFrame
        var interpolated: WidgetFrame
        var motionControl: Motion
        var motionWidgetStart: MotionWidget
        var motionWidgetEnd: MotionWidget
        var motionWidgetInterpolated: MotionWidget
        var myKeyCache = KeyCache()
        var myParentHeight = -1
        var myParentWidth = -1
        fun setKeyPosition(prop: TypedBundle) {
            val keyPosition = MotionKeyPosition()
            prop.applyDelta(keyPosition)
            motionControl.addKey(keyPosition)
        }

        fun setKeyAttribute(prop: TypedBundle) {
            val keyAttributes = MotionKeyAttributes()
            prop.applyDelta(keyAttributes)
            motionControl.addKey(keyAttributes)
        }

        fun setKeyCycle(prop: TypedBundle) {
            val keyAttributes = MotionKeyCycle()
            prop.applyDelta(keyAttributes)
            motionControl.addKey(keyAttributes)
        }

        fun update(child: ConstraintWidget?, state: Int) {
            if (state == START) {
                start.update(child)
                motionControl.setStart(motionWidgetStart)
            } else if (state == END) {
                end.update(child)
                motionControl.setEnd(motionWidgetEnd)
            }
            myParentWidth = -1
        }

        fun getFrame(type: Int): WidgetFrame {
            if (type == START) {
                return start
            } else if (type == END) {
                return end
            }
            return interpolated
        }

        fun interpolate(parentWidth: Int, parentHeight: Int, progress: Float, transition: Transition?) {
            if (true || parentHeight != myParentHeight || parentWidth != myParentWidth) {
                myParentHeight = parentHeight
                myParentWidth = parentWidth
                motionControl.setup(parentWidth, parentHeight, 1f, Clock.System.now().epochSeconds)
            }
            WidgetFrame.interpolate(parentWidth, parentHeight, interpolated, start, end, transition!!, progress)
            interpolated.interpolatedPos = progress
            motionControl.interpolate(motionWidgetInterpolated, progress, Clock.System.now().epochSeconds, myKeyCache)
        }

        init {
            start = WidgetFrame()
            end = WidgetFrame()
            interpolated = WidgetFrame()
            motionWidgetStart = MotionWidget(start)
            motionWidgetEnd = MotionWidget(end)
            motionWidgetInterpolated = MotionWidget(interpolated)
            motionControl = Motion(motionWidgetStart)
            motionControl.setStart(motionWidgetStart)
            motionControl.setEnd(motionWidgetEnd)
        }
    }

    class KeyPosition(var target: String?, var frame: Int, var type: Int, var x: Float, var y: Float)
    companion object {
        const val START = 0
        const val END = 1
        const val INTERPOLATED = 2
        const val EASE_IN_OUT = 0
        const val EASE_IN = 1
        const val EASE_OUT = 2
        const val LINEAR = 3
        const val BOUNCE = 4
        const val OVERSHOOT = 5
        const val ANTICIPATE = 6
        private const val SPLINE_STRING = -1
        private const val INTERPOLATOR_REFERENCE_ID = -2
        fun getInterpolator(interpolator: Int, interpolatorString: String?): Interpolator? {
            when (interpolator) {
                SPLINE_STRING -> return object : Interpolator {
                    override fun getInterpolation(input: Float): Float {
                        return Easing.getInterpolator(interpolatorString)?.get(input.toDouble())?.toFloat() ?: 0f
                    }
                }
                EASE_IN_OUT -> return object : Interpolator {
                    override fun getInterpolation(input: Float): Float {
                        return Easing.getInterpolator("standard")?.get(input.toDouble())?.toFloat() ?: 0f
                    }
                }
                EASE_IN -> return object : Interpolator {
                    override fun getInterpolation(input: Float): Float {
                        return Easing.getInterpolator("accelerate")?.get(input.toDouble())?.toFloat() ?: 0f
                    }
                }
                EASE_OUT -> return object : Interpolator {
                    override fun getInterpolation(input: Float): Float {
                        return Easing.getInterpolator("decelerate")?.get(input.toDouble())?.toFloat() ?: 0f
                    }
                }
                LINEAR -> return object : Interpolator {
                    override fun getInterpolation(input: Float): Float {
                        return Easing.getInterpolator("linear")?.get(input.toDouble())?.toFloat() ?: 0f
                    }
                }
                ANTICIPATE -> return object : Interpolator {
                    override fun getInterpolation(input: Float): Float {
                        return Easing.getInterpolator("anticipate")?.get(input.toDouble())?.toFloat() ?: 0f
                    }
                }
                OVERSHOOT -> return object : Interpolator {
                    override fun getInterpolation(input: Float): Float {
                        return Easing.getInterpolator("overshoot")?.get(input.toDouble())?.toFloat() ?: 0f
                    }
                }
                BOUNCE -> return object : Interpolator {
                    override fun getInterpolation(input: Float): Float {
                        return Easing.getInterpolator("spline(0.0, 0.2, 0.4, 0.6, 0.8 ,1.0, 0.8, 1.0, 0.9, 1.0)")?.get(input.toDouble())?.toFloat() ?: 0f
                    }
                }
            }
            return null
        }
    }
}