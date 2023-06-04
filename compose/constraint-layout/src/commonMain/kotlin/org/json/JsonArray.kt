/*
 * Copyright (C) 2010 The Android Open Source Project
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
package org.json

import org.json.Json.checkDouble
import org.json.Json.toBoolean
import org.json.Json.toDouble
import org.json.Json.toInteger
import org.json.Json.toLong
import org.json.Json.typeMismatch

// Note: this class was written without inspecting the non-free org.Json sourcecode.
/**
 * A dense indexed sequence of values. Values may be any mix of
 * [JsonObjects][JsonObject], other [JsonArrays][JsonArray], Strings,
 * Booleans, Integers, Longs, Doubles, `null` or [JsonObject.NULL].
 * Values may not be [NaNs][Double.isNaN], [ infinities][Double.isInfinite], or of any type not listed here.
 *
 *
 * `JsonArray` has the same type coercion behavior and
 * optional/mandatory accessors as [JsonObject]. See that class'
 * documentation for details.
 *
 *
 * **Warning:** this class represents null in two incompatible
 * ways: the standard Java `null` reference, and the sentinel value [ ][JsonObject.NULL]. In particular, `get` fails if the requested index
 * holds the null reference, but succeeds if it holds `JsonObject.NULL`.
 *
 *
 * Instances of this class are not thread safe. Although this class is
 * nonfinal, it was not designed for inheritance and should not be subclassed.
 * In particular, self-use by overridable methods is not specified. See
 * *Effective Java* Item 17, "Design and Document or inheritance or else
 * prohibit it" for further information.
 */
class JsonArray {
    private val values: MutableList<Any?>

    /**
     * Creates a `JsonArray` with no values.
     */
    constructor() {
        values = ArrayList()
    }

    /**
     * Creates a new `JsonArray` by copying all values from the given
     * collection.
     *
     * @param copyFrom a collection whose values are of supported types.
     * Unsupported values are not permitted and will yield an array in an
     * inconsistent state.
     */
    /* Accept a raw type for API compatibility */
    constructor(copyFrom: Collection<*>?) : this() {
        if (copyFrom != null) {
            val it = copyFrom.iterator()
            while (it.hasNext()) {
                put(JsonObject.wrap(it.next()))
            }
        }
    }

    /**
     * Creates a new `JsonArray` with values from the next array in the
     * tokener.
     *
     * @param readFrom a tokener whose nextValue() method will yield a
     * `JsonArray`.
     * @throws JsonException if the parse fails or doesn't yield a
     * `JsonArray`.
     */
    constructor(readFrom: JsonTokener) {
        /*
         * Getting the parser to populate this could get tricky. Instead, just
         * parse to temporary JsonArray and then steal the data from that.
         */
        val `object` = readFrom.nextValue()
        if (`object` is JsonArray) {
            values = `object`.values
        } else {
            throw typeMismatch(`object`, "JsonArray")
        }
    }

    /**
     * Creates a new `JsonArray` with values from the Json string.
     *
     * @param Json a Json-encoded string containing an array.
     * @throws JsonException if the parse fails or doesn't yield a `JsonArray`.
     */
    constructor(Json: String?) : this(JsonTokener(Json)) {}

    /**
     * Creates a new `JsonArray` with values from the given primitive array.
     */
    constructor(array: Any) {
        if (array !is kotlin.Array<*>) {
            throw JsonException("Not a primitive array: " + array::class.qualifiedName)
        }
        val length = array.count()
        values = mutableListOf()
        for (i in 0 until length) {
            put(JsonObject.wrap(array[i]))
        }
    }

    /**
     * Returns the number of values in this array.
     */
    fun length(): Int {
        return values.size
    }

    /**
     * Appends `value` to the end of this array.
     *
     * @return this array.
     */
    fun put(value: Boolean): JsonArray {
        values.add(value)
        return this
    }

    /**
     * Appends `value` to the end of this array.
     *
     * @param value a finite value. May not be [NaNs][Double.isNaN] or
     * [infinities][Double.isInfinite].
     * @return this array.
     */
    @Throws(JsonException::class)
    fun put(value: Double): JsonArray {
        values.add(checkDouble(value))
        return this
    }

    /**
     * Appends `value` to the end of this array.
     *
     * @return this array.
     */
    fun put(value: Int): JsonArray {
        values.add(value)
        return this
    }

    /**
     * Appends `value` to the end of this array.
     *
     * @return this array.
     */
    fun put(value: Long): JsonArray {
        values.add(value)
        return this
    }

    /**
     * Appends `value` to the end of this array.
     *
     * @param value a [JsonObject], [JsonArray], String, Boolean,
     * Integer, Long, Double, [JsonObject.NULL], or `null`. May
     * not be [NaNs][Double.isNaN] or [     infinities][Double.isInfinite]. Unsupported values are not permitted and will cause the
     * array to be in an inconsistent state.
     * @return this array.
     */
    fun put(value: Any?): JsonArray {
        values.add(value)
        return this
    }

    /**
     * Same as [.put], with added validity checks.
     */
    @Throws(JsonException::class)
    fun checkedPut(value: Any?) {
        if (value is Number) {
            checkDouble(value.toDouble())
        }
        put(value)
    }

    /**
     * Sets the value at `index` to `value`, null padding this array
     * to the required length if necessary. If a value already exists at `index`, it will be replaced.
     *
     * @return this array.
     */
    @Throws(JsonException::class)
    fun put(index: Int, value: Boolean): JsonArray {
        return put(index, value)
    }

    /**
     * Sets the value at `index` to `value`, null padding this array
     * to the required length if necessary. If a value already exists at `index`, it will be replaced.
     *
     * @param value a finite value. May not be [NaNs][Double.isNaN] or
     * [infinities][Double.isInfinite].
     * @return this array.
     */
    @Throws(JsonException::class)
    fun put(index: Int, value: Double): JsonArray {
        return put(index, value)
    }

    /**
     * Sets the value at `index` to `value`, null padding this array
     * to the required length if necessary. If a value already exists at `index`, it will be replaced.
     *
     * @return this array.
     */
    @Throws(JsonException::class)
    fun put(index: Int, value: Int): JsonArray {
        return put(index, value)
    }

    /**
     * Sets the value at `index` to `value`, null padding this array
     * to the required length if necessary. If a value already exists at `index`, it will be replaced.
     *
     * @return this array.
     */
    @Throws(JsonException::class)
    fun put(index: Int, value: Long): JsonArray {
        return put(index, value)
    }

    /**
     * Sets the value at `index` to `value`, null padding this array
     * to the required length if necessary. If a value already exists at `index`, it will be replaced.
     *
     * @param value a [JsonObject], [JsonArray], String, Boolean,
     * Integer, Long, Double, [JsonObject.NULL], or `null`. May
     * not be [NaNs][Double.isNaN] or [     infinities][Double.isInfinite].
     * @return this array.
     */
    @Throws(JsonException::class)
    fun put(index: Int, value: Any?): JsonArray {
        if (value is Number) {
            // deviate from the original by checking all Numbers, not just floats & doubles
            checkDouble(value.toDouble())
        }
        while (values.size <= index) {
            values.add(null)
        }
        values[index] = value
        return this
    }

    /**
     * Returns true if this array has no value at `index`, or if its value
     * is the `null` reference or [JsonObject.NULL].
     */
    fun isNull(index: Int): Boolean {
        val value = opt(index)
        return value == null || value === JsonObject.NULL
    }

    /**
     * Returns the value at `index`.
     *
     * @throws JsonException if this array has no value at `index`, or if
     * that value is the `null` reference. This method returns
     * normally if the value is `JsonObject#NULL`.
     */
    @Throws(JsonException::class)
    operator fun get(index: Int): Any {
        return try {
            val value = values[index] ?: throw JsonException("Value at $index is null.")
            value
        } catch (e: IndexOutOfBoundsException) {
            throw JsonException("Index " + index + " out of range [0.." + values.size + ")", e)
        }
    }

    /**
     * Returns the value at `index`, or null if the array has no value
     * at `index`.
     */
    fun opt(index: Int): Any? {
        return if (index < 0 || index >= values.size) {
            null
        } else values[index]
    }

    /**
     * Removes and returns the value at `index`, or null if the array has no value
     * at `index`.
     */
    fun remove(index: Int): Any? {
        return if (index < 0 || index >= values.size) {
            null
        } else values.removeAt(index)
    }

    /**
     * Returns the value at `index` if it exists and is a boolean or can
     * be coerced to a boolean.
     *
     * @throws JsonException if the value at `index` doesn't exist or
     * cannot be coerced to a boolean.
     */
    @Throws(JsonException::class)
    fun getBoolean(index: Int): Boolean {
        val `object` = get(index)
        return toBoolean(`object`) ?: throw typeMismatch(index, `object`, "boolean")
    }
    /**
     * Returns the value at `index` if it exists and is a boolean or can
     * be coerced to a boolean. Returns `fallback` otherwise.
     */
    /**
     * Returns the value at `index` if it exists and is a boolean or can
     * be coerced to a boolean. Returns false otherwise.
     */
    @JvmOverloads
    fun optBoolean(index: Int, fallback: Boolean = false): Boolean {
        val `object` = opt(index)
        val result = toBoolean(`object`)
        return result ?: fallback
    }

    /**
     * Returns the value at `index` if it exists and is a double or can
     * be coerced to a double.
     *
     * @throws JsonException if the value at `index` doesn't exist or
     * cannot be coerced to a double.
     */
    @Throws(JsonException::class)
    fun getDouble(index: Int): Double {
        val `object` = get(index)
        return toDouble(`object`) ?: throw typeMismatch(index, `object`, "double")
    }
    /**
     * Returns the value at `index` if it exists and is a double or can
     * be coerced to a double. Returns `fallback` otherwise.
     */
    /**
     * Returns the value at `index` if it exists and is a double or can
     * be coerced to a double. Returns `NaN` otherwise.
     */
    @JvmOverloads
    fun optDouble(index: Int, fallback: Double = Double.NaN): Double {
        val `object` = opt(index)
        val result = toDouble(`object`)
        return result ?: fallback
    }

    /**
     * Returns the value at `index` if it exists and is an int or
     * can be coerced to an int.
     *
     * @throws JsonException if the value at `index` doesn't exist or
     * cannot be coerced to a int.
     */
    @Throws(JsonException::class)
    fun getInt(index: Int): Int {
        val `object` = get(index)
        return toInteger(`object`) ?: throw typeMismatch(index, `object`, "int")
    }
    /**
     * Returns the value at `index` if it exists and is an int or
     * can be coerced to an int. Returns `fallback` otherwise.
     */
    /**
     * Returns the value at `index` if it exists and is an int or
     * can be coerced to an int. Returns 0 otherwise.
     */
    @JvmOverloads
    fun optInt(index: Int, fallback: Int = 0): Int {
        val `object` = opt(index)
        val result = toInteger(`object`)
        return result ?: fallback
    }

    /**
     * Returns the value at `index` if it exists and is a long or
     * can be coerced to a long.
     *
     * @throws JsonException if the value at `index` doesn't exist or
     * cannot be coerced to a long.
     */
    @Throws(JsonException::class)
    fun getLong(index: Int): Long {
        val `object` = get(index)
        return toLong(`object`) ?: throw typeMismatch(index, `object`, "long")
    }
    /**
     * Returns the value at `index` if it exists and is a long or
     * can be coerced to a long. Returns `fallback` otherwise.
     */
    /**
     * Returns the value at `index` if it exists and is a long or
     * can be coerced to a long. Returns 0 otherwise.
     */
    @JvmOverloads
    fun optLong(index: Int, fallback: Long = 0L): Long {
        val `object` = opt(index)
        val result = toLong(`object`)
        return result ?: fallback
    }

    /**
     * Returns the value at `index` if it exists, coercing it if
     * necessary.
     *
     * @throws JsonException if no such value exists.
     */
    @Throws(JsonException::class)
    fun getString(index: Int): String {
        val `object` = get(index)
        return Json.toString(`object` as Int) ?: throw typeMismatch(index, `object`, "String")
    }
    /**
     * Returns the value at `index` if it exists, coercing it if
     * necessary. Returns `fallback` if no such value exists.
     */
    /**
     * Returns the value at `index` if it exists, coercing it if
     * necessary. Returns the empty string if no such value exists.
     */
    @JvmOverloads
    fun optString(index: Int, fallback: String? = ""): String {
        val `object` = opt(index)
        return Json.toString(`object`) ?: fallback ?: ""
    }

    /**
     * Returns the value at `index` if it exists and is a `JsonArray`.
     *
     * @throws JsonException if the value doesn't exist or is not a `JsonArray`.
     */
    @Throws(JsonException::class)
    fun getJsonArray(index: Int): JsonArray {
        val `object` = get(index)
        return if (`object` is JsonArray) {
            `object`
        } else {
            throw typeMismatch(index, `object`, "JsonArray")
        }
    }

    /**
     * Returns the value at `index` if it exists and is a `JsonArray`. Returns null otherwise.
     */
    fun optJsonArray(index: Int): JsonArray? {
        val `object` = opt(index)
        return if (`object` is JsonArray) `object` else null
    }

    /**
     * Returns the value at `index` if it exists and is a `JsonObject`.
     *
     * @throws JsonException if the value doesn't exist or is not a `JsonObject`.
     */
    @Throws(JsonException::class)
    fun getJsonObject(index: Int): JsonObject {
        val `object` = get(index)
        return if (`object` is JsonObject) {
            `object`
        } else {
            throw typeMismatch(index, `object`, "JsonObject")
        }
    }

    /**
     * Returns the value at `index` if it exists and is a `JsonObject`. Returns null otherwise.
     */
    fun optJsonObject(index: Int): JsonObject? {
        val `object` = opt(index)
        return if (`object` is JsonObject) `object` else null
    }

    /**
     * Returns a new object whose values are the values in this array, and whose
     * names are the values in `names`. Names and values are paired up by
     * index from 0 through to the shorter array's length. Names that are not
     * strings will be coerced to strings. This method returns null if either
     * array is empty.
     */
    @Throws(JsonException::class)
    fun toJsonObject(names: JsonArray): JsonObject? {
        val result = JsonObject()
        val length = names.length().coerceAtMost(values.size)
        if (length == 0) {
            return null
        }
        for (i in 0 until length) {
            val name = Json.toString(names.opt(i))
            result.put(name, opt(i))
        }
        return result
    }

    /**
     * Returns a new string by alternating this array's values with `separator`. This array's string values are quoted and have their special
     * characters escaped. For example, the array containing the strings '12"
     * pizza', 'taco' and 'soda' joined on '+' returns this:
     * <pre>"12\" pizza"+"taco"+"soda"</pre>
     */
    @Throws(JsonException::class)
    fun join(separator: String?): String {
        val stringer = JsonStringer()
        stringer.open(Scope.NULL, "")
        var i = 0
        val size = values.size
        while (i < size) {
            if (i > 0) {
                stringer.out.append(separator)
            }
            stringer.value(values[i])
            i++
        }
        stringer.close(Scope.NULL, Scope.NULL, "")
        return stringer.out.toString()
    }

    /**
     * Encodes this array as a compact Json string, such as:
     * <pre>[94043,90210]</pre>
     */
    override fun toString(): String {
        return try {
            val stringer = JsonStringer()
            writeTo(stringer)
            stringer.toString()
        } catch (e: JsonException) {
            ""
        }
    }

    /**
     * Encodes this array as a human readable Json string for debugging, such
     * as:
     * <pre>
     * [
     * 94043,
     * 90210
     * ]</pre>
     *
     * @param indentSpaces the number of spaces to indent for each level of
     * nesting.
     */
    @Throws(JsonException::class)
    fun toString(indentSpaces: Int): String {
        val stringer = JsonStringer(indentSpaces)
        writeTo(stringer)
        return stringer.toString()
    }

    @Throws(JsonException::class)
    fun writeTo(stringer: JsonStringer) {
        stringer.array()
        for (value in values) {
            stringer.value(value)
        }
        stringer.endArray()
    }

    override fun equals(o: Any?): Boolean {
        return o is JsonArray && o.values == values
    }

    override fun hashCode(): Int {
        // diverge from the original, which doesn't implement hashCode
        return values.hashCode()
    }
}