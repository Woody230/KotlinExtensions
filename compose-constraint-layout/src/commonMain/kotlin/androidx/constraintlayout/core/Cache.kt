/*
 * Copyright (C) 2016 The Android Open Source Project
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
package androidx.constraintlayout.core

import androidx.constraintlayout.core.ArrayRow
import androidx.constraintlayout.core.SolverVariable
import kotlin.jvm.JvmField

/**
 * Cache for common objects
 */
class Cache {
    var optimizedArrayRowPool: Pools.SimplePool<ArrayRow> = Pools.SimplePool(256)
    var arrayRowPool: Pools.SimplePool<ArrayRow> = Pools.SimplePool(256)
    var solverVariablePool: Pools.SimplePool<SolverVariable> = Pools.SimplePool(256)
    @JvmField
    var mIndexedVariables = arrayOfNulls<SolverVariable>(32)
}