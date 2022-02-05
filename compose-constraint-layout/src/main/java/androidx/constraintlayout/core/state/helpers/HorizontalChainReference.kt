/*
 * Copyright (C) 2019 The Android Open Source Project
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

import androidx.constraintlayout.core.state.ConstraintReference
import androidx.constraintlayout.core.state.State
import androidx.constraintlayout.core.widgets.ConstraintWidget

class HorizontalChainReference(state: State?) : ChainReference(state, State.Helper.HORIZONTAL_CHAIN) {
    override fun apply() {
        var first: ConstraintReference? = null
        var previous: ConstraintReference? = null
        for (key in mReferences) {
            val reference = mState.constraints(key)
            reference!!.clearHorizontal()
        }
        for (key in mReferences) {
            val reference = mState.constraints(key)
            if (first == null) {
                first = reference
                if (mStartToStart != null) {
                    first!!.startToStart(mStartToStart).margin(mMarginStart)
                } else if (mStartToEnd != null) {
                    first!!.startToEnd(mStartToEnd).margin(mMarginStart)
                } else if (mLeftToLeft != null) {
                    // TODO: Hack until we support RTL properly
                    first!!.startToStart(mLeftToLeft).margin(mMarginLeft)
                } else if (mLeftToRight != null) {
                    // TODO: Hack until we support RTL properly
                    first!!.startToEnd(mLeftToRight).margin(mMarginLeft)
                } else {
                    first!!.startToStart(State.PARENT)
                }
            }
            if (previous != null) {
                if (reference != null) {
                    previous.endToStart(reference.key)
                }
                reference!!.startToEnd(previous.key)
            }
            previous = reference
        }
        if (previous != null) {
            if (mEndToStart != null) {
                previous.endToStart(mEndToStart).margin(mMarginEnd)
            } else if (mEndToEnd != null) {
                previous.endToEnd(mEndToEnd).margin(mMarginEnd)
            } else if (mRightToLeft != null) {
                // TODO: Hack until we support RTL properly
                previous.endToStart(mRightToLeft).margin(mMarginRight)
            } else if (mRightToRight != null) {
                // TODO: Hack until we support RTL properly
                previous.endToEnd(mRightToRight).margin(mMarginRight)
            } else {
                previous.endToEnd(State.PARENT)
            }
        }
        if (first == null) {
            return
        }
        if (bias != 0.5f) {
            first.horizontalBias(bias)
        }
        when (mStyle) {
            State.Chain.SPREAD -> {
                first.horizontalChainStyle = ConstraintWidget.CHAIN_SPREAD
            }
            State.Chain.SPREAD_INSIDE -> {
                first.horizontalChainStyle = ConstraintWidget.CHAIN_SPREAD_INSIDE
            }
            State.Chain.PACKED -> {
                first.horizontalChainStyle = ConstraintWidget.CHAIN_PACKED
            }
        }
    }
}