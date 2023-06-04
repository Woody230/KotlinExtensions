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

import androidx.constraintlayout.core.motion.CustomAttribute
import androidx.constraintlayout.core.motion.CustomVariable

class KeyFrameArray constructor() {
    // =================================== CustomAttribute =================================
    class CustomArray constructor() {
        var keys: IntArray = IntArray(101)
        var values: Array<CustomAttribute?> = arrayOfNulls(101)
        var count: Int = 0
        fun clear() {
            keys.fill(EMPTY)
            values.fill(null)
            count = 0
        }

        /*
        fun dump() {
            println("V: " + Arrays.toString(Arrays.copyOf(keys, count)))
            print("K: [")
            for (i in 0 until count) {
                print(((if (i == 0) "" else ", ")) + valueAt(i))
            }
            println("]")
        }*/

        fun size(): Int {
            return count
        }

        fun valueAt(i: Int): CustomAttribute? {
            return values.get(keys.get(i))
        }

        fun keyAt(i: Int): Int {
            return keys.get(i)
        }

        fun append(position: Int, value: CustomAttribute?) {
            if (values.get(position) != null) {
                remove(position)
            }
            values[position] = value
            keys[count++] = position
            keys.sort()
        }

        fun remove(position: Int) {
            values[position] = null
            var j: Int = 0
            var i: Int = 0
            while (i < count) {
                if (position == keys.get(i)) {
                    keys[i] = EMPTY
                    j++
                }
                if (i != j) {
                    keys[i] = keys.get(j)
                }
                j++
                i++
            }
            count--
        }

        companion object {
            private val EMPTY: Int = 999
        }

        init {
            clear()
        }
    }

    // =================================== CustomVar =================================
    class CustomVar constructor() {
        var keys: IntArray = IntArray(101)
        var values: Array<CustomVariable?> = arrayOfNulls(101)
        var count: Int = 0
        fun clear() {
            keys.fill(EMPTY)
            values.fill(null)
            count = 0
        }

        /*
        fun dump() {
            println("V: " + Arrays.toString(Arrays.copyOf(keys, count)))
            print("K: [")
            for (i in 0 until count) {
                print(((if (i == 0) "" else ", ")) + valueAt(i))
            }
            println("]")
        }*/

        fun size(): Int {
            return count
        }

        fun valueAt(i: Int): CustomVariable? {
            return values.get(keys.get(i))
        }

        fun keyAt(i: Int): Int {
            return keys.get(i)
        }

        fun append(position: Int, value: CustomVariable?) {
            if (values.get(position) != null) {
                remove(position)
            }
            values[position] = value
            keys[count++] = position
            keys.sort()
        }

        fun remove(position: Int) {
            values[position] = null
            var j: Int = 0
            var i: Int = 0
            while (i < count) {
                if (position == keys.get(i)) {
                    keys[i] = EMPTY
                    j++
                }
                if (i != j) {
                    keys[i] = keys.get(j)
                }
                j++
                i++
            }
            count--
        }

        companion object {
            private val EMPTY: Int = 999
        }

        init {
            clear()
        }
    }

    // =================================== FloatArray ======================================
    class FloatArray constructor() {
        var keys: IntArray = IntArray(101)
        var values: Array<kotlin.FloatArray?> = arrayOfNulls(101)
        var count: Int = 0
        fun clear() {
            keys.fill(EMPTY)
            values.fill(null)
            count = 0
        }

        /*
        fun dump() {
            println("V: " + Arrays.toString(Arrays.copyOf(keys, count)))
            print("K: [")
            for (i in 0 until count) {
                print(((if (i == 0) "" else ", ")) + Arrays.toString(valueAt(i)))
            }
            println("]")
        }*/

        fun size(): Int {
            return count
        }

        fun valueAt(i: Int): kotlin.FloatArray? {
            return values.get(keys.get(i))
        }

        fun keyAt(i: Int): Int {
            return keys.get(i)
        }

        fun append(position: Int, value: kotlin.FloatArray?) {
            if (values.get(position) != null) {
                remove(position)
            }
            values[position] = value
            keys[count++] = position
            keys.sort()
        }

        fun remove(position: Int) {
            values[position] = null
            var j: Int = 0
            var i: Int = 0
            while (i < count) {
                if (position == keys.get(i)) {
                    keys[i] = EMPTY
                    j++
                }
                if (i != j) {
                    keys[i] = keys.get(j)
                }
                j++
                i++
            }
            count--
        }

        companion object {
            private val EMPTY: Int = 999
        }

        init {
            clear()
        }
    }
}