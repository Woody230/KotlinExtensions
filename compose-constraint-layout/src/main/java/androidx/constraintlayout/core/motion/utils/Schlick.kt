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

/**
 * Schlick’s bias and gain functions
 * curve for use in an easing function including quantize functions
 *
 */
class Schlick internal constructor(configString: String) : Easing() {
    var mS: Double
    var mT: Double
    var eps: Double = 0.0
    private fun func(x: Double): Double {
        if (x < mT) {
            return mT * x / (x + mS * (mT - x))
        }
        return ((1 - mT) * (x - 1)) / (1 - x - (mS * (mT - x)))
    }

    private fun dfunc(x: Double): Double {
        if (x < mT) {
            return (mS * mT * mT) / ((mS * (mT - x) + x) * (mS * (mT - x) + x))
        }
        return (mS * (mT - 1) * (mT - 1)) / ((-mS * (mT - x) - x + 1) * (-mS * (mT - x) - x + 1))
    }

    public override fun getDiff(x: Double): Double {
        return dfunc(x)
    }

    public override fun get(x: Double): Double {
        return func(x)
    }

    companion object {
        private val DEBUG: Boolean = false
    }

    init {
        // done this way for efficiency
        str = configString
        val start: Int = configString.indexOf('(')
        val off1: Int = configString.indexOf(',', start)
        mS = configString.substring(start + 1, off1).trim({ it <= ' ' }).toDouble()
        val off2: Int = configString.indexOf(',', off1 + 1)
        mT = configString.substring(off1 + 1, off2).trim({ it <= ' ' }).toDouble()
    }
}