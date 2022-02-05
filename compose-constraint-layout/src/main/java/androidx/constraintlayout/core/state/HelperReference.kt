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
package androidx.constraintlayout.core.state

import androidx.constraintlayout.core.widgets.HelperWidget
import androidx.constraintlayout.core.state.helpers.Facade
import androidx.constraintlayout.core.widgets.ConstraintWidget

open class HelperReference(state: State, type: State.Helper) : ConstraintReference(state), Facade {
    @JvmField
    protected val mState: State = state

    val type: State.Helper
    @JvmField
    var mReferences = ArrayList<Any>()
    open var helperWidget: HelperWidget? = null
    fun add(vararg objects: Any): HelperReference {
        mReferences.addAll(objects)
        return this
    }

    override var constraintWidget: ConstraintWidget? = helperWidget

    override fun apply() {
        // nothing
    }

    init {
        this.type = type
    }
}