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

import androidx.constraintlayout.core.state.ConstraintReference
import androidx.constraintlayout.core.state.HelperReference
import androidx.constraintlayout.core.state.State
import androidx.constraintlayout.core.widgets.Barrier
import androidx.constraintlayout.core.widgets.HelperWidget

class BarrierReference(state: State?) : HelperReference(state!!, State.Helper.BARRIER) {
    private var mDirection: State.Direction? = null
    private var mMargin = 0
    private var mBarrierWidget: Barrier? = null
    fun setBarrierDirection(barrierDirection: State.Direction?) {
        mDirection = barrierDirection
    }

    override fun margin(value: Any?): ConstraintReference {
        margin(mState.convertDimension(value))
        return this
    }

    override fun margin(value: Int): ConstraintReference {
        mMargin = value
        return this
    }

    override var helperWidget: HelperWidget?
        get() {
            if (mBarrierWidget == null) {
                mBarrierWidget = Barrier()
            }
            return mBarrierWidget!!
        }
        set(helperWidget) {
            super.helperWidget = helperWidget
        }

    override fun apply() {
        helperWidget
        var direction = Barrier.LEFT
        when (mDirection) {
            State.Direction.LEFT, State.Direction.START -> {}
            State.Direction.RIGHT, State.Direction.END -> {

                // TODO: handle RTL
                direction = Barrier.RIGHT
            }
            State.Direction.TOP -> {
                direction = Barrier.TOP
            }
            State.Direction.BOTTOM -> {
                direction = Barrier.BOTTOM
            }

            else -> {}
        }
        mBarrierWidget!!.barrierType = direction
        mBarrierWidget!!.margin = mMargin
    }
}