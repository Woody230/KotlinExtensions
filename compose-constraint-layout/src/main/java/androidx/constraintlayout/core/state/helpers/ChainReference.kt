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

import androidx.constraintlayout.core.state.HelperReference
import androidx.constraintlayout.core.state.State

open class ChainReference(state: State?, type: State.Helper?) : HelperReference(state!!, type!!) {
    var bias = 0.5f
        protected set
    @JvmField
    protected var mStyle = State.Chain.SPREAD
    val style: State.Chain
        get() = State.Chain.SPREAD

    fun style(style: State.Chain): ChainReference {
        mStyle = style
        return this
    }

    override fun bias(bias: Float): ChainReference {
        this.bias = bias
        return this
    }
}