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
package androidx.constraintlayout.core.motion.utils

import kotlin.math.sqrt

/**
 * This contains the class to provide the logic for an animation to come to a stop using a spring
 * model.
 *
 * @suppress
 */
class SpringStopEngine constructor() : StopEngine {
    var mDamping: Double = 0.5
    private var mInitialized: Boolean = false
    private var mStiffness: Double = 0.0
    private var mTargetPos: Double = 0.0
    private var mLastVelocity: Double = 0.0
    private var mLastTime: Float = 0f
    private var mPos: Float = 0f
    private var mV: Float = 0f
    private var mMass: Float = 0f
    private var mStopThreshold: Float = 0f
    private var mBoundaryMode: Int = 0
    public override fun debug(desc: String, time: Float): String? {
        return null
    }

    /*
    fun log(str: String) {
        val s: StackTraceElement = Throwable().getStackTrace().get(1)
        val line: String = ".(" + s.getFileName() + ":" + s.getLineNumber() + ") " + s.getMethodName() + "() "
        println(line + str)
    }*/

    fun springConfig(
        currentPos: Float, target: Float, currentVelocity: Float, mass: Float,
        stiffness: Float, damping: Float, stopThreshold: Float, boundaryMode: Int
    ) {
        mTargetPos = target.toDouble()
        mDamping = damping.toDouble()
        mInitialized = false
        mPos = currentPos
        mLastVelocity = currentVelocity.toDouble()
        mStiffness = stiffness.toDouble()
        mMass = mass
        mStopThreshold = stopThreshold
        mBoundaryMode = boundaryMode
        mLastTime = 0f
    }

    public override fun getVelocity(t: Float): Float {
        return mV
    }

    public override fun getInterpolation(time: Float): Float {
        compute((time - mLastTime).toDouble())
        mLastTime = time
        return (mPos)
    }

    val acceleration: Float
        get() {
            val k: Double = mStiffness
            val c: Double = mDamping
            val x: Double = (mPos - mTargetPos)
            return (-k * x - c * mV).toFloat() / mMass
        }
    override val velocity: Float
        get() {
            return 0f
        }
    override val isStopped: Boolean
        get() {
            val x: Double = (mPos - mTargetPos)
            val k: Double = mStiffness
            val v: Double = mV.toDouble()
            val m: Double = mMass.toDouble()
            val energy: Double = v * v * m + k * x * x
            val max_def: Double = sqrt(energy / k)
            return max_def <= mStopThreshold
        }

    private fun compute(dt: Double) {
        var dt: Double = dt
        val k: Double = mStiffness
        val c: Double = mDamping
        // Estimate how many time we should over sample based on the frequency and current sampling
        val overSample: Int = (1 + 9 / (sqrt(mStiffness / mMass) * dt * 4)).toInt()
        dt /= overSample.toDouble()
        for (i in 0 until overSample) {
            val x: Double = (mPos - mTargetPos)
            var a: Double = (-k * x - c * mV) / mMass
            // This refinement of a simple coding of the acceleration increases accuracy
            var avgV: Double = mV + a * dt / 2 // pass 1 calculate the average velocity
            val avgX: Double = mPos + dt * (avgV) / 2 - mTargetPos // pass 1 calculate the average pos
            a = (-avgX * k - avgV * c) / mMass //  calculate acceleration over that average pos
            val dv: Double = a * dt //  calculate change in velocity
            avgV = mV + dv / 2 //  average  velocity is current + half change
            mV += dv.toFloat()
            mPos += (avgV * dt).toFloat()
            if (mBoundaryMode > 0) {
                if (mPos < 0 && ((mBoundaryMode and 1) == 1)) {
                    mPos = -mPos
                    mV = -mV
                }
                if (mPos > 1 && ((mBoundaryMode and 2) == 2)) {
                    mPos = 2 - mPos
                    mV = -mV
                }
            }
        }
    }

    companion object {
        private val UNSET: Double = Double.MAX_VALUE
    }
}