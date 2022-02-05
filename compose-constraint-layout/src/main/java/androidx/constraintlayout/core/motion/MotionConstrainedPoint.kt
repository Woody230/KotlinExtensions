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

import androidx.constraintlayout.core.motion.utils.Easing
import androidx.constraintlayout.core.motion.utils.Rect
import androidx.constraintlayout.core.motion.utils.TypedValues
import androidx.constraintlayout.core.motion.utils.SplineSet
import androidx.constraintlayout.core.motion.utils.SplineSet.CustomSpline

/**
 * All the parameter it extracts from a ConstraintSet/View
 *
 * @suppress
 */
internal class MotionConstrainedPoint : Comparable<MotionConstrainedPoint> {
    private var alpha = 1f
    var mVisibilityMode = MotionWidget.VISIBILITY_MODE_NORMAL
    var visibility = 0
    private var applyElevation = false
    private val elevation = 0f
    private var rotation = 0f
    private var rotationX = 0f
    var rotationY = 0f
    private var scaleX = 1f
    private var scaleY = 1f
    private var mPivotX = Float.NaN
    private var mPivotY = Float.NaN
    private var translationX = 0f
    private var translationY = 0f
    private var translationZ = 0f
    private val mKeyFrameEasing: Easing? = null
    private val mDrawPath = 0
    private val position = 0f
    private var x = 0f
    private var y = 0f
    private var width = 0f
    private var height = 0f
    private val mPathRotate = Float.NaN
    private val mProgress = Float.NaN
    private val mAnimateRelativeTo = -1
    var mCustomVariable = LinkedHashMap<String, CustomVariable>()
    var mMode = 0 // how was this point computed 1=perpendicular 2=deltaRelative
    private fun diff(a: Float, b: Float): Boolean {
        return if (a.isNaN() || b.isNaN()) {
            a.isNaN() != b.isNaN()
        } else Math.abs(a - b) > 0.000001f
    }

    /**
     * Given the start and end points define Keys that need to be built
     *
     * @param points
     * @param keySet
     */
    fun different(points: MotionConstrainedPoint, keySet: HashSet<String>) {
        if (diff(alpha, points.alpha)) {
            keySet.add(TypedValues.AttributesType.S_ALPHA)
        }
        if (diff(elevation, points.elevation)) {
            keySet.add(TypedValues.AttributesType.S_TRANSLATION_Z)
        }
        if (visibility != points.visibility && mVisibilityMode == MotionWidget.VISIBILITY_MODE_NORMAL && (visibility == MotionWidget.VISIBLE
                    || points.visibility == MotionWidget.VISIBLE)
        ) {
            keySet.add(TypedValues.AttributesType.S_ALPHA)
        }
        if (diff(rotation, points.rotation)) {
            keySet.add(TypedValues.AttributesType.S_ROTATION_Z)
        }
        if (!(mPathRotate.isNaN() && points.mPathRotate.isNaN())) {
            keySet.add(TypedValues.AttributesType.S_PATH_ROTATE)
        }
        if (!(mProgress.isNaN() && points.mProgress.isNaN())) {
            keySet.add(TypedValues.AttributesType.S_PROGRESS)
        }
        if (diff(rotationX, points.rotationX)) {
            keySet.add(TypedValues.AttributesType.S_ROTATION_X)
        }
        if (diff(rotationY, points.rotationY)) {
            keySet.add(TypedValues.AttributesType.S_ROTATION_Y)
        }
        if (diff(mPivotX, points.mPivotX)) {
            keySet.add(TypedValues.AttributesType.S_PIVOT_X)
        }
        if (diff(mPivotY, points.mPivotY)) {
            keySet.add(TypedValues.AttributesType.S_PIVOT_Y)
        }
        if (diff(scaleX, points.scaleX)) {
            keySet.add(TypedValues.AttributesType.S_SCALE_X)
        }
        if (diff(scaleY, points.scaleY)) {
            keySet.add(TypedValues.AttributesType.S_SCALE_Y)
        }
        if (diff(translationX, points.translationX)) {
            keySet.add(TypedValues.AttributesType.S_TRANSLATION_X)
        }
        if (diff(translationY, points.translationY)) {
            keySet.add(TypedValues.AttributesType.S_TRANSLATION_Y)
        }
        if (diff(translationZ, points.translationZ)) {
            keySet.add(TypedValues.AttributesType.S_TRANSLATION_Z)
        }
        if (diff(elevation, points.elevation)) {
            keySet.add(TypedValues.AttributesType.S_ELEVATION)
        }
    }

    fun different(points: MotionConstrainedPoint, mask: BooleanArray, custom: Array<String?>?) {
        var c = 0
        mask[c++] = mask[c++] or diff(position, points.position)
        mask[c++] = mask[c++] or diff(x, points.x)
        mask[c++] = mask[c++] or diff(y, points.y)
        mask[c++] = mask[c++] or diff(width, points.width)
        mask[c++] = mask[c++] or diff(height, points.height)
    }

    var mTempValue = DoubleArray(18)
    var mTempDelta = DoubleArray(18)
    fun fillStandard(data: DoubleArray, toUse: IntArray) {
        val set = floatArrayOf(
            position, x, y, width, height, alpha, elevation, rotation, rotationX, rotationY,
            scaleX, scaleY, mPivotX, mPivotY, translationX, translationY, translationZ, mPathRotate
        )
        var c = 0
        for (i in toUse.indices) {
            if (toUse[i] < set.size) {
                data[c++] = set[toUse[i]].toDouble()
            }
        }
    }

    fun hasCustomData(name: String): Boolean {
        return mCustomVariable.containsKey(name)
    }

    fun getCustomDataCount(name: String): Int {
        return mCustomVariable[name]!!.numberOfInterpolatedValues()
    }

    fun getCustomData(name: String, value: DoubleArray, offset: Int): Int {
        var offset = offset
        val a = mCustomVariable[name]
        return if (a!!.numberOfInterpolatedValues() == 1) {
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

    override fun compareTo(o: MotionConstrainedPoint): Int {
        return position.compareTo(o.position)
    }

    fun applyParameters(view: MotionWidget) {
        visibility = view.visibility
        alpha = if (view.visibility != MotionWidget.VISIBLE) 0.0f else view.alpha
        applyElevation = false // TODO figure a way to cache parameters
        rotation = view.rotationZ
        rotationX = view.rotationX
        rotationY = view.rotationY
        scaleX = view.scaleX
        scaleY = view.scaleY
        mPivotX = view.pivotX
        mPivotY = view.pivotY
        translationX = view.translationX
        translationY = view.translationY
        translationZ = view.translationZ
        val at = view.customAttributeNames
        for (s in at) {
            val attr = view.getCustomAttribute(s)
            if (attr != null && attr.isContinuous) {
                mCustomVariable[s] = attr
            }
        }
    }

    fun addValues(splines: HashMap<String, SplineSet>, mFramePosition: Int) {
        for (s in splines.keys) {
            val ViewSpline = splines[s]
            if (DEBUG) {
                //Utils.log(TAG, "setPoint" + mFramePosition + "  spline set = " + s);
            }
            when (s) {
                TypedValues.AttributesType.S_ALPHA -> ViewSpline!!.setPoint(mFramePosition, if (alpha.isNaN()) 1f else alpha)
                TypedValues.AttributesType.S_ROTATION_Z -> ViewSpline!!.setPoint(mFramePosition, if (rotation.isNaN()) 0f else rotation)
                TypedValues.AttributesType.S_ROTATION_X -> ViewSpline!!.setPoint(mFramePosition, if (rotationX.isNaN()) 0f else rotationX)
                TypedValues.AttributesType.S_ROTATION_Y -> ViewSpline!!.setPoint(mFramePosition, if (rotationY.isNaN()) 0f else rotationY)
                TypedValues.AttributesType.S_PIVOT_X -> ViewSpline!!.setPoint(mFramePosition, if (mPivotX.isNaN()) 0f else mPivotX)
                TypedValues.AttributesType.S_PIVOT_Y -> ViewSpline!!.setPoint(mFramePosition, if (mPivotY.isNaN()) 0f else mPivotY)
                TypedValues.AttributesType.S_PATH_ROTATE -> ViewSpline!!.setPoint(mFramePosition, if (mPathRotate.isNaN()) 0f else mPathRotate)
                TypedValues.AttributesType.S_PROGRESS -> ViewSpline!!.setPoint(mFramePosition, if (mProgress.isNaN()) 0f else mProgress)
                TypedValues.AttributesType.S_SCALE_X -> ViewSpline!!.setPoint(mFramePosition, if (scaleX.isNaN()) 1f else scaleX)
                TypedValues.AttributesType.S_SCALE_Y -> ViewSpline!!.setPoint(mFramePosition, if (scaleY.isNaN()) 1f else scaleY)
                TypedValues.AttributesType.S_TRANSLATION_X -> ViewSpline!!.setPoint(mFramePosition, if (translationX.isNaN()) 0f else translationX)
                TypedValues.AttributesType.S_TRANSLATION_Y -> ViewSpline!!.setPoint(mFramePosition, if (translationY.isNaN()) 0f else translationY)
                TypedValues.AttributesType.S_TRANSLATION_Z -> ViewSpline!!.setPoint(mFramePosition, if (translationZ.isNaN()) 0f else translationZ)
                else -> if (s.startsWith("CUSTOM")) {
                    val customName = s.split(",".toRegex()).toTypedArray()[1]
                    if (mCustomVariable.containsKey(customName)) {
                        val custom = mCustomVariable[customName]
                        if (ViewSpline is CustomSpline) {
                            ViewSpline.setPoint(mFramePosition, custom)
                        } else {
                            /*Utils.loge(TAG, s + " ViewSpline not a CustomSet frame = " +
                                        mFramePosition + ", value" + custom.getValueToInterpolate() +
                                        ViewSpline);*/
                        }
                    }
                } else {
                    //Utils.loge(TAG, "UNKNOWN spline " + s);
                }
            }
        }
    }

    fun setState(view: MotionWidget) {
        setBounds(view.left.toFloat(), view.top.toFloat(), view.width.toFloat(), view.height.toFloat())
        applyParameters(view)
    }

    /**
     * @param rect     assumes pre rotated
     * @param view
     * @param rotation mode Surface.ROTATION_0,Surface.ROTATION_90...
     */
    fun setState(rect: Rect, view: MotionWidget, rotation: Int, prevous: Float) {
        setBounds(rect.left.toFloat(), rect.top.toFloat(), rect.width().toFloat(), rect.height().toFloat())
        applyParameters(view)
        mPivotX = Float.NaN
        mPivotY = Float.NaN
        when (rotation) {
            MotionWidget.ROTATE_PORTRATE_OF_LEFT -> this.rotation = prevous + 90
            MotionWidget.ROTATE_PORTRATE_OF_RIGHT -> this.rotation = prevous - 90
        }
    } //   TODO support Screen Rotation

    //    /**
    //     * Sets the state of the position given a rect, constraintset, rotation and viewid
    //     *
    //     * @param cw
    //     * @param constraintSet
    //     * @param rotation
    //     * @param viewId
    //     */
    //    public void setState(Rect cw, ConstraintSet constraintSet, int rotation, int viewId) {
    //        setBounds(cw.left, cw.top, cw.width(), cw.height());
    //        applyParameters(constraintSet.getParameters(viewId));
    //        switch (rotation) {
    //            case ConstraintSet.ROTATE_PORTRATE_OF_RIGHT:
    //            case ConstraintSet.ROTATE_RIGHT_OF_PORTRATE:
    //                this.rotation -= 90;
    //                break;
    //            case ConstraintSet.ROTATE_PORTRATE_OF_LEFT:
    //            case ConstraintSet.ROTATE_LEFT_OF_PORTRATE:
    //                this.rotation += 90;
    //                if (this.rotation > 180) this.rotation -= 360;
    //                break;
    //        }
    //    }
    companion object {
        const val TAG = "MotionPaths"
        const val DEBUG = false
        const val PERPENDICULAR = 1
        const val CARTESIAN = 2
        var names = arrayOf("position", "x", "y", "width", "height", "pathRotate")
    }
}