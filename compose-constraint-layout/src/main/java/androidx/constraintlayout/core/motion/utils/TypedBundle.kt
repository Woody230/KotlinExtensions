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

class TypedBundle constructor() {
    var mTypeInt: IntArray = IntArray(INITIAL_INT)
    var mValueInt: IntArray = IntArray(INITIAL_INT)
    var mCountInt: Int = 0
    var mTypeFloat: IntArray = IntArray(INITIAL_FLOAT)
    var mValueFloat: FloatArray = FloatArray(INITIAL_FLOAT)
    var mCountFloat: Int = 0
    var mTypeString: IntArray = IntArray(INITIAL_STRING)
    var mValueString: Array<String?> = arrayOfNulls(INITIAL_STRING)
    var mCountString: Int = 0
    var mTypeBoolean: IntArray = IntArray(INITIAL_BOOLEAN)
    var mValueBoolean: BooleanArray = BooleanArray(INITIAL_BOOLEAN)
    var mCountBoolean: Int = 0
    fun getInteger(type: Int): Int {
        for (i in 0 until mCountInt) {
            if (mTypeInt.get(i) == type) {
                return mValueInt.get(i)
            }
        }
        return -1
    }

    fun add(type: Int, value: Int) {
        if (mCountInt >= mTypeInt.size) {
            mTypeInt = mTypeInt.copyOf(mTypeInt.size * 2)
            mValueInt = mValueInt.copyOf(mValueInt.size * 2)
        }
        mTypeInt[mCountInt] = type
        mValueInt[mCountInt++] = value
    }

    fun add(type: Int, value: Float) {
        if (mCountFloat >= mTypeFloat.size) {
            mTypeFloat = mTypeFloat.copyOf(mTypeFloat.size * 2)
            mValueFloat = mValueFloat.copyOf(mValueFloat.size * 2)
        }
        mTypeFloat[mCountFloat] = type
        mValueFloat[mCountFloat++] = value
    }

    fun addIfNotNull(type: Int, value: String?) {
        if (value != null) {
            add(type, value)
        }
    }

    fun add(type: Int, value: String?) {
        if (mCountString >= mTypeString.size) {
            mTypeString = mTypeString.copyOf(mTypeString.size * 2)
            mValueString = mValueString.copyOf(mValueString.size * 2)
        }
        mTypeString[mCountString] = type
        mValueString[mCountString++] = value
    }

    fun add(type: Int, value: Boolean) {
        if (mCountBoolean >= mTypeBoolean.size) {
            mTypeBoolean = mTypeBoolean.copyOf(mTypeBoolean.size * 2)
            mValueBoolean = mValueBoolean.copyOf(mValueBoolean.size * 2)
        }
        mTypeBoolean[mCountBoolean] = type
        mValueBoolean[mCountBoolean++] = value
    }

    fun applyDelta(values: TypedValues) {
        for (i in 0 until mCountInt) {
            values.setValue(mTypeInt.get(i), mValueInt.get(i))
        }
        for (i in 0 until mCountFloat) {
            values.setValue(mTypeFloat.get(i), mValueFloat.get(i))
        }
        for (i in 0 until mCountString) {
            values.setValue(mTypeString.get(i), mValueString.get(i))
        }
        for (i in 0 until mCountBoolean) {
            values.setValue(mTypeBoolean.get(i), mValueBoolean.get(i))
        }
    }

    fun applyDelta(values: TypedBundle) {
        for (i in 0 until mCountInt) {
            values.add(mTypeInt.get(i), mValueInt.get(i))
        }
        for (i in 0 until mCountFloat) {
            values.add(mTypeFloat.get(i), mValueFloat.get(i))
        }
        for (i in 0 until mCountString) {
            values.add(mTypeString.get(i), mValueString.get(i))
        }
        for (i in 0 until mCountBoolean) {
            values.add(mTypeBoolean.get(i), mValueBoolean.get(i))
        }
    }

    fun clear() {
        mCountBoolean = 0
        mCountString = 0
        mCountFloat = 0
        mCountInt = 0
    }

    companion object {
        private val INITIAL_BOOLEAN: Int = 4
        private val INITIAL_INT: Int = 10
        private val INITIAL_FLOAT: Int = 10
        private val INITIAL_STRING: Int = 5
    }
}