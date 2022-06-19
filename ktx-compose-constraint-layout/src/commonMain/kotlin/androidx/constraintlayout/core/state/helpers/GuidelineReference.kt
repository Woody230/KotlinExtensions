/*
 * Copyright 2019 The Android Open Source Project
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
package androidx.constraintlayout.core.state.helpers

import androidx.constraintlayout.core.state.Reference
import androidx.constraintlayout.core.state.State
import androidx.constraintlayout.core.widgets.ConstraintWidget
import androidx.constraintlayout.core.widgets.Guideline

class GuidelineReference(val mState: State) : Facade, Reference {
    var orientation = 0
    private var mGuidelineWidget: Guideline? = null
    private var mStart = -1
    private var mEnd = -1
    private var mPercent = 0f
    override var key: Any? = null
    fun start(margin: Any?): GuidelineReference {
        mStart = mState.convertDimension(margin)
        mEnd = -1
        mPercent = 0f
        return this
    }

    fun end(margin: Any?): GuidelineReference {
        mStart = -1
        mEnd = mState.convertDimension(margin)
        mPercent = 0f
        return this
    }

    fun percent(percent: Float): GuidelineReference {
        mStart = -1
        mEnd = -1
        mPercent = percent
        return this
    }

    override fun apply() {
        mGuidelineWidget!!.orientation = orientation
        if (mStart != -1) {
            mGuidelineWidget!!.setGuideBegin(mStart)
        } else if (mEnd != -1) {
            mGuidelineWidget!!.setGuideEnd(mEnd)
        } else {
            mGuidelineWidget!!.setGuidePercent(mPercent)
        }
    }

    override val facade: Facade?
        get() = null
    override var constraintWidget: ConstraintWidget?
        get() {
            if (mGuidelineWidget == null) {
                mGuidelineWidget = Guideline()
            }
            return mGuidelineWidget
        }
        set(widget) {
            mGuidelineWidget = if (widget is Guideline) {
                widget
            } else {
                null
            }
        }
}