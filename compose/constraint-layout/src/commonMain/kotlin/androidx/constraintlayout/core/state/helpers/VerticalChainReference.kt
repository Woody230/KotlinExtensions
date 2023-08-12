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

class VerticalChainReference(state: State?) : ChainReference(state, State.Helper.VERTICAL_CHAIN) {
    override fun apply() {
        var first: ConstraintReference? = null
        var previous: ConstraintReference? = null
        for (key in mReferences) {
            val reference = mState.constraints(key)
            reference!!.clearVertical()
        }
        for (key in mReferences) {
            val reference = mState.constraints(key)
            if (first == null) {
                first = reference
                if (mTopToTop != null) {
                    first!!.topToTop(mTopToTop)
                } else if (mTopToBottom != null) {
                    first!!.topToBottom(mTopToBottom)
                } else {
                    first!!.topToTop(State.PARENT)
                }
            }
            if (previous != null) {
                if (reference != null) {
                    previous.bottomToTop(reference.key)
                }
                reference!!.topToBottom(previous.key)
            }
            previous = reference
        }
        if (previous != null) {
            if (mBottomToTop != null) {
                previous.bottomToTop(mBottomToTop)
            } else if (mBottomToBottom != null) {
                previous.bottomToBottom(mBottomToBottom)
            } else {
                previous.bottomToBottom(State.PARENT)
            }
        }
        if (first == null) {
            return
        }
        if (bias != 0.5f) {
            first.verticalBias(bias)
        }
        when (mStyle) {
            State.Chain.SPREAD -> {
                first.setVerticalChainStyle(ConstraintWidget.CHAIN_SPREAD)
            }
            State.Chain.SPREAD_INSIDE -> {
                first.setVerticalChainStyle(ConstraintWidget.CHAIN_SPREAD_INSIDE)
            }
            State.Chain.PACKED -> {
                first.setVerticalChainStyle(ConstraintWidget.CHAIN_PACKED)
            }
        }
    }
}