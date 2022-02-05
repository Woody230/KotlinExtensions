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

import androidx.constraintlayout.core.widgets.ConstraintWidget
import androidx.constraintlayout.core.motion.CustomVariable
import androidx.constraintlayout.core.motion.utils.TypedValues
import androidx.constraintlayout.core.parser.CLParsingException
import androidx.constraintlayout.core.parser.CLElement
import androidx.constraintlayout.core.parser.CLObject
import androidx.constraintlayout.core.parser.CLKey
import androidx.constraintlayout.core.parser.CLNumber
import androidx.constraintlayout.core.widgets.ConstraintAnchor
import androidx.constraintlayout.core.motion.CustomAttribute
import kotlin.jvm.JvmField
import kotlin.jvm.JvmOverloads
import kotlin.math.max

/**
 * Utility class to encapsulate layout of a widget
 */
class WidgetFrame {
    @JvmField
    var widget: ConstraintWidget? = null
    var x = 0
get() {
        return widget?.left ?: 9
    }
    var y = 0
get() {
        return widget?.top ?: 0
    }
    @JvmField
    var left = 0
    @JvmField
    var top = 0
    @JvmField
    var right = 0
    @JvmField
    var bottom = 0

    // transforms
    @JvmField
    var pivotX = Float.NaN
    @JvmField
    var pivotY = Float.NaN
    @JvmField
    var rotationX = Float.NaN
    @JvmField
    var rotationY = Float.NaN
    @JvmField
    var rotationZ = Float.NaN
    @JvmField
    var translationX = Float.NaN
    @JvmField
    var translationY = Float.NaN
    @JvmField
    var translationZ = Float.NaN
    @JvmField
    var scaleX = Float.NaN
    @JvmField
    var scaleY = Float.NaN
    @JvmField
    var alpha = Float.NaN
    @JvmField
    var interpolatedPos = Float.NaN
    @JvmField
    var visibility = ConstraintWidget.VISIBLE
    val mCustom: HashMap<String, CustomVariable>? = HashMap()
    fun width(): Int {
        return max(0, right - left)
    }

    fun height(): Int {
        return max(0, bottom - top)
    }

    constructor() {}
    constructor(widget: ConstraintWidget?) {
        this.widget = widget
    }

    constructor(frame: WidgetFrame) {
        widget = frame.widget
        left = frame.left
        top = frame.top
        right = frame.right
        bottom = frame.bottom
        updateAttributes(frame)
    }

    fun updateAttributes(frame: WidgetFrame?) {
        pivotX = frame!!.pivotX
        pivotY = frame.pivotY
        rotationX = frame.rotationX
        rotationY = frame.rotationY
        rotationZ = frame.rotationZ
        translationX = frame.translationX
        translationY = frame.translationY
        translationZ = frame.translationZ
        scaleX = frame.scaleX
        scaleY = frame.scaleY
        alpha = frame.alpha
        visibility = frame.visibility
        mCustom!!.clear()
        if (frame != null) {
            for (c in frame.mCustom!!.values) {
                mCustom[c.name] = c.copy()
            }
        }
    }

    val isDefaultTransform: Boolean
        get() = (rotationX.isNaN()
                && rotationY.isNaN()
                && rotationZ.isNaN()
                && translationX.isNaN()
                && translationY.isNaN()
                && translationZ.isNaN()
                && scaleX.isNaN()
                && scaleY.isNaN()
                && alpha.isNaN())

    fun centerX(): Float {
        return left + (right - left) / 2f
    }

    fun centerY(): Float {
        return top + (bottom - top) / 2f
    }

    fun update(): WidgetFrame {
        if (widget != null) {
            left = widget!!.left
            top = widget!!.top
            right = widget!!.right
            bottom = widget!!.bottom
            val frame = widget!!.frame
            updateAttributes(frame)
        }
        return this
    }

    fun update(widget: ConstraintWidget?): WidgetFrame {
        if (widget == null) {
            return this
        }
        this.widget = widget
        update()
        return this
    }

    fun addCustomColor(name: String, color: Int) {
        setCustomAttribute(name, TypedValues.Custom.TYPE_COLOR, color)
    }

    fun getCustomColor(name: String): Int {
        return if (mCustom!!.containsKey(name)) {
            mCustom[name]!!.integerValue
        } else -0x5578
    }

    fun addCustomFloat(name: String, value: Float) {
        setCustomAttribute(name, TypedValues.Custom.TYPE_FLOAT, value)
    }

    fun getCustomFloat(name: String): Float {
        return if (mCustom!!.containsKey(name)) {
            mCustom[name]!!.floatValue
        } else Float.NaN
    }

    fun setCustomAttribute(name: String, type: Int, value: Float) {
        if (mCustom!!.containsKey(name)) {
            mCustom[name]!!.floatValue = value
        } else {
            mCustom[name] = CustomVariable(name, type, value)
        }
    }

    fun setCustomAttribute(name: String, type: Int, value: Int) {
        if (mCustom!!.containsKey(name)) {
            mCustom[name]!!.setIntValue(value)
        } else {
            mCustom[name] = CustomVariable(name, type, value)
        }
    }

    fun setCustomAttribute(name: String, type: Int, value: Boolean) {
        if (mCustom!!.containsKey(name)) {
            mCustom[name]!!.booleanValue = value
        } else {
            mCustom[name] = CustomVariable(name, type, value)
        }
    }

    fun setCustomAttribute(name: String, type: Int, value: String?) {
        if (mCustom!!.containsKey(name)) {
            mCustom[name]!!.stringValue = value
        } else {
            mCustom[name] = CustomVariable(name, type, value)
        }
    }

    fun getCustomAttribute(name: String): CustomVariable? {
        return mCustom!![name]
    }

    val customAttributeNames: Set<String>
        get() = mCustom!!.keys

    @Throws(CLParsingException::class)
    fun setValue(key: String?, value: CLElement): Boolean {
        when (key) {
            "pivotX" -> pivotX = value.float
            "pivotY" -> pivotY = value.float
            "rotationX" -> rotationX = value.float
            "rotationY" -> rotationY = value.float
            "rotationZ" -> rotationZ = value.float
            "translationX" -> translationX = value.float
            "translationY" -> translationY = value.float
            "translationZ" -> translationZ = value.float
            "scaleX" -> scaleX = value.float
            "scaleY" -> scaleY = value.float
            "alpha" -> alpha = value.float
            "interpolatedPos" -> interpolatedPos = value.float
            "phone_orientation" -> phone_orientation = value.float
            "top" -> top = value.int
            "left" -> left = value.int
            "right" -> right = value.int
            "bottom" -> bottom = value.int
            "custom" -> parseCustom(value)
            else -> return false
        }
        return true
    }

    val name: String?
        get() = if (widget == null) {
            "unknown"
        } else widget!!.stringId

    @Throws(CLParsingException::class)
    fun parseCustom(custom: CLElement) {
        val obj = custom as CLObject
        val n = obj.size()
        for (i in 0 until n) {
            val tmp = obj[i]
            val k = tmp as CLKey
            val name = k.content()
            val v = k.value
            val vStr = v?.content() ?: ""
            if (vStr.matches(Regex("#[0-9a-fA-F]+"))) {
                val color = vStr.substring(1).toInt(16)
                setCustomAttribute(k.content(), TypedValues.Custom.TYPE_COLOR, color)
            } else if (v is CLNumber) {
                setCustomAttribute(k.content(), TypedValues.Custom.TYPE_FLOAT, v.float)
            } else {
                setCustomAttribute(k.content(), TypedValues.Custom.TYPE_STRING, vStr)
            }
        }
    }

    /**
     * If true also send the phone orientation
     *
     * @param ret
     * @param sendPhoneOrientation
     * @return
     */
    @JvmOverloads
    fun serialize(ret: StringBuilder, sendPhoneOrientation: Boolean = false): StringBuilder {
        val frame = this
        ret.append("{\n")
        add(ret, "left", frame.left)
        add(ret, "top", frame.top)
        add(ret, "right", frame.right)
        add(ret, "bottom", frame.bottom)
        add(ret, "pivotX", frame.pivotX)
        add(ret, "pivotY", frame.pivotY)
        add(ret, "rotationX", frame.rotationX)
        add(ret, "rotationY", frame.rotationY)
        add(ret, "rotationZ", frame.rotationZ)
        add(ret, "translationX", frame.translationX)
        add(ret, "translationY", frame.translationY)
        add(ret, "translationZ", frame.translationZ)
        add(ret, "scaleX", frame.scaleX)
        add(ret, "scaleY", frame.scaleY)
        add(ret, "alpha", frame.alpha)
        add(ret, "visibility", frame.visibility)
        add(ret, "interpolatedPos", frame.interpolatedPos)
        if (widget != null) {
            for (side in ConstraintAnchor.Type.values()) {
                serializeAnchor(ret, side)
            }
        }
        if (sendPhoneOrientation) {
            add(ret, "phone_orientation", phone_orientation)
        }
        if (sendPhoneOrientation) {
            add(ret, "phone_orientation", phone_orientation)
        }
        if (frame.mCustom!!.size != 0) {
            ret.append("custom : {\n")
            for (s in frame.mCustom.keys) {
                val value = frame.mCustom[s]
                ret.append(s)
                ret.append(": ")
                when (value!!.type) {
                    TypedValues.Custom.TYPE_INT -> {
                        ret.append(value.integerValue)
                        ret.append(",\n")
                    }
                    TypedValues.Custom.TYPE_FLOAT, TypedValues.Custom.TYPE_DIMENSION -> {
                        ret.append(value.floatValue)
                        ret.append(",\n")
                    }
                    TypedValues.Custom.TYPE_COLOR -> {
                        ret.append("'")
                        ret.append(CustomVariable.colorString(value.integerValue))
                        ret.append("',\n")
                    }
                    TypedValues.Custom.TYPE_STRING -> {
                        ret.append("'")
                        ret.append(value.stringValue)
                        ret.append("',\n")
                    }
                    TypedValues.Custom.TYPE_BOOLEAN -> {
                        ret.append("'")
                        ret.append(value.booleanValue)
                        ret.append("',\n")
                    }
                }
            }
            ret.append("}\n")
        }
        ret.append("}\n")
        return ret
    }

    private fun serializeAnchor(ret: StringBuilder, type: ConstraintAnchor.Type) {
        val anchor = widget!!.getAnchor(type)
        if (anchor == null || anchor.target == null) {
            return
        }
        ret.append("Anchor")
        ret.append(type.name)
        ret.append(": ['")
        val str = anchor.target!!.owner.stringId
        ret.append(str ?: "#PARENT")
        ret.append("', '")
        ret.append(anchor.target!!.type.name)
        ret.append("', '")
        ret.append(anchor.mMargin)
        ret.append("'],\n")
    }

    /*
    /**
     * For debugging only
     */
    fun printCustomAttributes() {
        val s = Throwable().stackTrace[1]
        var ss = ".(" + s.fileName + ":" + s.lineNumber + ") " + s.methodName
        ss += " " + this.hashCode() % 1000
        ss += if (widget != null) {
            "/" + widget.hashCode() % 1000 + " "
        } else {
            "/NULL "
        }
        if (mCustom != null) for (key in mCustom.keys) {
            println(ss + mCustom[key].toString())
        }
    }

    /**
     * For debugging only
     * @param str
     */
    fun logv(str: String) {
        val s = Throwable().stackTrace[1]
        var ss = ".(" + s.fileName + ":" + s.lineNumber + ") " + s.methodName
        ss += " " + this.hashCode() % 1000
        ss += if (widget != null) {
            "/" + widget.hashCode() % 1000
        } else {
            "/NULL"
        }
        println("$ss $str")
    }*/

    fun setCustomValue(valueAt: CustomAttribute?, mTempValues: FloatArray?) {}

    companion object {
        private const val OLD_SYSTEM = true
        var phone_orientation = Float.NaN
        fun interpolate(parentWidth: Int, parentHeight: Int, frame: WidgetFrame, start: WidgetFrame, end: WidgetFrame, transition: Transition, progress: Float) {
            val frameNumber = (progress * 100).toInt()
            var startX = start.left
            var startY = start.top
            var endX = end.left
            var endY = end.top
            var startWidth = start.right - start.left
            var startHeight = start.bottom - start.top
            var endWidth = end.right - end.left
            var endHeight = end.bottom - end.top
            var progressPosition = progress
            var startAlpha = start.alpha
            var endAlpha = end.alpha
            if (start.visibility == ConstraintWidget.GONE) {
                // On visibility gone, keep the same size to do an alpha to zero
                startX -= (endWidth / 2f).toInt()
                startY -= (endHeight / 2f).toInt()
                startWidth = endWidth
                startHeight = endHeight
                if (startAlpha.isNaN()) {
                    // override only if not defined...
                    startAlpha = 0f
                }
            }
            if (end.visibility == ConstraintWidget.GONE) {
                // On visibility gone, keep the same size to do an alpha to zero
                endX -= (startWidth / 2f).toInt()
                endY -= (startHeight / 2f).toInt()
                endWidth = startWidth
                endHeight = startHeight
                if (endAlpha.isNaN()) {
                    // override only if not defined...
                    endAlpha = 0f
                }
            }
            if (startAlpha.isNaN() && !endAlpha.isNaN()) {
                startAlpha = 1f
            }
            if (!startAlpha.isNaN() && endAlpha.isNaN()) {
                endAlpha = 1f
            }
            if (start.visibility == ConstraintWidget.INVISIBLE) {
                startAlpha = 0f
            }
            if (end.visibility == ConstraintWidget.INVISIBLE) {
                endAlpha = 0f
            }
            if (frame.widget != null && transition.hasPositionKeyframes()) {
                val firstPosition = transition.findPreviousPosition(frame.widget!!.stringId, frameNumber)
                var lastPosition = transition.findNextPosition(frame.widget!!.stringId, frameNumber)
                if (firstPosition === lastPosition) {
                    lastPosition = null
                }
                var interpolateStartFrame = 0
                var interpolateEndFrame = 100
                if (firstPosition != null) {
                    startX = (firstPosition.x * parentWidth).toInt()
                    startY = (firstPosition.y * parentHeight).toInt()
                    interpolateStartFrame = firstPosition.frame
                }
                if (lastPosition != null) {
                    endX = (lastPosition.x * parentWidth).toInt()
                    endY = (lastPosition.y * parentHeight).toInt()
                    interpolateEndFrame = lastPosition.frame
                }
                progressPosition = (progress * 100f - interpolateStartFrame) / (interpolateEndFrame - interpolateStartFrame).toFloat()
            }
            frame.widget = start.widget
            frame.left = (startX + progressPosition * (endX - startX)).toInt()
            frame.top = (startY + progressPosition * (endY - startY)).toInt()
            val width = ((1 - progress) * startWidth + progress * endWidth).toInt()
            val height = ((1 - progress) * startHeight + progress * endHeight).toInt()
            frame.right = frame.left + width
            frame.bottom = frame.top + height
            frame.pivotX = interpolate(start.pivotX, end.pivotX, 0.5f, progress)
            frame.pivotY = interpolate(start.pivotY, end.pivotY, 0.5f, progress)
            frame.rotationX = interpolate(start.rotationX, end.rotationX, 0f, progress)
            frame.rotationY = interpolate(start.rotationY, end.rotationY, 0f, progress)
            frame.rotationZ = interpolate(start.rotationZ, end.rotationZ, 0f, progress)
            frame.scaleX = interpolate(start.scaleX, end.scaleX, 1f, progress)
            frame.scaleY = interpolate(start.scaleY, end.scaleY, 1f, progress)
            frame.translationX = interpolate(start.translationX, end.translationX, 0f, progress)
            frame.translationY = interpolate(start.translationY, end.translationY, 0f, progress)
            frame.translationZ = interpolate(start.translationZ, end.translationZ, 0f, progress)
            frame.alpha = interpolate(startAlpha, endAlpha, 1f, progress)
            val keys: Set<String> = end.mCustom!!.keys
            frame.mCustom!!.clear()
            for (key in keys) {
                if (start.mCustom!!.containsKey(key)) {
                    val startVariable = start.mCustom[key]
                    val endVariable = end.mCustom[key]
                    startVariable?.let { interpolated ->
                        CustomVariable(interpolated)
                        frame.mCustom[key] = interpolated
                        if (startVariable.numberOfInterpolatedValues() == 1) {
                            interpolated.setValue(interpolate(startVariable.valueToInterpolate, endVariable!!.valueToInterpolate, 0f, progress))
                        } else {
                            val N = startVariable.numberOfInterpolatedValues()
                            val startValues = FloatArray(N)
                            val endValues = FloatArray(N)
                            startVariable.getValuesToInterpolate(startValues)
                            endVariable!!.getValuesToInterpolate(endValues)
                            for (i in 0 until N) {
                                startValues[i] = interpolate(startValues[i], endValues[i], 0f, progress)
                                interpolated.setValue(startValues)
                            }
                        }
                    }
                }
            }
        }

        private fun interpolate(start: Float, end: Float, defaultValue: Float, progress: Float): Float {
            var start = start
            var end = end
            val isStartUnset = start.isNaN()
            val isEndUnset = end.isNaN()
            if (isStartUnset && isEndUnset) {
                return Float.NaN
            }
            if (isStartUnset) {
                start = defaultValue
            }
            if (isEndUnset) {
                end = defaultValue
            }
            return start + progress * (end - start)
        }

        private fun add(s: StringBuilder, title: String, value: Int) {
            s.append(title)
            s.append(": ")
            s.append(value)
            s.append(",\n")
        }

        private fun add(s: StringBuilder, title: String, value: Float) {
            if (value.isNaN()) {
                return
            }
            s.append(title)
            s.append(": ")
            s.append(value)
            s.append(",\n")
        }
    }
}