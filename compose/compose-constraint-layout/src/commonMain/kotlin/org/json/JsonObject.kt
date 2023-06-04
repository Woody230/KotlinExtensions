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

import kotlin.jvm.JvmOverloads

// Note: this class was written without inspecting the non-free org.Json sourcecode.
/**
 * A modifiable set of name/value mappings. Names are unique, non-null strings.
 * Values may be any mix of [JsonObjects][JsonObject], [ JsonArrays][JsonArray], Strings, Booleans, Integers, Longs, Doubles or [.NULL].
 * Values may not be `null`, [NaNs][Double.isNaN], [ ][Double.isInfinite], or of any type not listed here.
 *
 *
 * This class can coerce values to another type when requested.
 *
 *  * When the requested type is a boolean, strings will be coerced using a
 * case-insensitive comparison to "true" and "false".
 *  * When the requested type is a double, other [Number] types will
 * be coerced using [doubleValue][Number.doubleValue]. Strings
 * that can be coerced using [Double.valueOf] will be.
 *  * When the requested type is an int, other [Number] types will
 * be coerced using [intValue][Number.intValue]. Strings
 * that can be coerced using [Double.valueOf] will be,
 * and then cast to int.
 *  * <a name="lossy">When the requested type is a long, other [Number] types will
 * be coerced using [longValue][Number.longValue]. Strings
 * that can be coerced using [Double.valueOf] will be,
 * and then cast to long. This two-step conversion is lossy for very
 * large values. For example, the string "9223372036854775806" yields the
 * long 9223372036854775807.</a>
 *  * When the requested type is a String, other non-null values will be
 * coerced using [String.valueOf]. Although null cannot be
 * coerced, the sentinel value [JsonObject.NULL] is coerced to the
 * string "null".
 *
 *
 *
 * This class can look up both mandatory and optional values:
 *
 *  * Use `get*Type*()` to retrieve a mandatory value. This
 * fails with a `JsonException` if the requested name has no value
 * or if the value cannot be coerced to the requested type.
 *  * Use `opt*Type*()` to retrieve an optional value. This
 * returns a system- or user-supplied default if the requested name has no
 * value or if the value cannot be coerced to the requested type.
 *
 *
 *
 * **Warning:** this class represents null in two incompatible
 * ways: the standard Java `null` reference, and the sentinel value [ ][JsonObject.NULL]. In particular, calling `put(name, null)` removes the
 * named entry from the object but `put(name, JsonObject.NULL)` stores an
 * entry whose value is `JsonObject.NULL`.
 *
 *
 * Instances of this class are not thread safe. Although this class is
 * nonfinal, it was not designed for inheritance and should not be subclassed.
 * In particular, self-use by overrideable methods is not specified. See
 * *Effective Java* Item 17, "Design and Document or inheritance or else
 * prohibit it" for further information.
 */
class JsonObject {
    private val nameValuePairs: LinkedHashMap<String?, Any>

    /**
     * Creates a `JsonObject` with no name/value mappings.
     */
    constructor() {
        nameValuePairs = LinkedHashMap()
    }

    /**
     * Creates a new `JsonObject` by copying all name/value mappings from
     * the given map.
     *
     * @param copyFrom a map whose keys are of type [String] and whose
     * values are of supported types.
     * @throws NullPointerException if any of the map's keys are null.
     */
    /* (accept a raw type for API compatibility) */
    constructor(copyFrom: Map<*, *>) : this() {
        for ((key1, value) in copyFrom) {
            /*
             * Deviate from the original by checking that keys are non-null and
             * of the proper type. (We still defer validating the values).
             */
            val key = key1 as String? ?: throw NullPointerException("key == null")
            nameValuePairs[key] = wrap(value) ?: ""
        }
    }

    /**
     * Creates a new `JsonObject` with name/value mappings from the next
     * object in the tokener.
     *
     * @param readFrom a tokener whose nextValue() method will yield a
     * `JsonObject`.
     * @throws JsonException if the parse fails or doesn't yield a
     * `JsonObject`.
     */
    constructor(readFrom: JsonTokener) {
        /*
         * Getting the parser to populate this could get tricky. Instead, just
         * parse to temporary JsonObject and then steal the data from that.
         */
        val `object` = readFrom.nextValue()
        if (`object` is JsonObject) {
            nameValuePairs = `object`.nameValuePairs
        } else {
            throw Json.typeMismatch(`object`, "JsonObject")
        }
    }

    /**
     * Creates a new `JsonObject` with name/value mappings from the Json
     * string.
     *
     * @param Json a Json-encoded string containing an object.
     * @throws JsonException if the parse fails or doesn't yield a `JsonObject`.
     */
    constructor(Json: String?) : this(JsonTokener(Json)) {}

    /**
     * Creates a new `JsonObject` by copying mappings for the listed names
     * from the given object. Names that aren't present in `copyFrom` will
     * be skipped.
     */
    constructor(copyFrom: JsonObject, names: Array<String?>?) : this() {
        for (name in names!!) {
            val value = copyFrom.opt(name)
            if (value != null) {
                nameValuePairs[name] = value
            }
        }
    }

    /**
     * Returns the number of name/value mappings in this object.
     */
    fun length(): Int {
        return nameValuePairs.size
    }

    /**
     * Maps `name` to `value`, clobbering any existing name/value
     * mapping with the same name.
     *
     * @return this object.
     */
    
    @Throws(JsonException::class)
    fun put(name: String?, value: Boolean): JsonObject {
        nameValuePairs[checkName(name)] = value
        return this
    }

    /**
     * Maps `name` to `value`, clobbering any existing name/value
     * mapping with the same name.
     *
     * @param value a finite value. May not be [NaNs][Double.isNaN] or
     * [infinities][Double.isInfinite].
     * @return this object.
     */
    
    @Throws(JsonException::class)
    fun put(name: String?, value: Double): JsonObject {
        nameValuePairs[checkName(name)] = Json.checkDouble(value)
        return this
    }

    /**
     * Maps `name` to `value`, clobbering any existing name/value
     * mapping with the same name.
     *
     * @return this object.
     */
    
    @Throws(JsonException::class)
    fun put(name: String?, value: Int): JsonObject {
        nameValuePairs[checkName(name)] = value
        return this
    }

    /**
     * Maps `name` to `value`, clobbering any existing name/value
     * mapping with the same name.
     *
     * @return this object.
     */
    
    @Throws(JsonException::class)
    fun put(name: String?, value: Long): JsonObject {
        nameValuePairs[checkName(name)] = value
        return this
    }

    /**
     * Maps `name` to `value`, clobbering any existing name/value
     * mapping with the same name. If the value is `null`, any existing
     * mapping for `name` is removed.
     *
     * @param value a [JsonObject], [JsonArray], String, Boolean,
     * Integer, Long, Double, [.NULL], or `null`. May not be
     * [NaNs][Double.isNaN] or [     infinities][Double.isInfinite].
     * @return this object.
     */
    
    @Throws(JsonException::class)
    fun put(name: String?, value: Any?): JsonObject {
        if (value == null) {
            nameValuePairs.remove(name)
            return this
        }
        if (value is Number) {
            // deviate from the original by checking all Numbers, not just floats & doubles
            Json.checkDouble(value.toDouble())
        }
        nameValuePairs[checkName(name)] = value
        return this
    }

    /**
     * Equivalent to `put(name, value)` when both parameters are non-null;
     * does nothing otherwise.
     */
    
    @Throws(JsonException::class)
    fun putOpt( name: String?,  value: Any?): JsonObject {
        return if (name == null || value == null) {
            this
        } else put(name, value)
    }

    /**
     * Appends `value` to the array already mapped to `name`. If
     * this object has no mapping for `name`, this inserts a new mapping.
     * If the mapping exists but its value is not an array, the existing
     * and new values are inserted in order into a new array which is itself
     * mapped to `name`. In aggregate, this allows values to be added to a
     * mapping one at a time.
     *
     *
     *  Note that `append(String, Object)` provides better semantics.
     * In particular, the mapping for `name` will **always** be a
     * [JsonArray]. Using `accumulate` will result in either a
     * [JsonArray] or a mapping whose type is the type of `value`
     * depending on the number of calls to it.
     *
     * @param value a [JsonObject], [JsonArray], String, Boolean,
     * Integer, Long, Double, [.NULL] or null. May not be [     ][Double.isNaN] or [infinities][Double.isInfinite].
     */
    // TODO: Change {@code append) to {@link #append} when append is
    // unhidden.
    
    @Throws(JsonException::class)
    fun accumulate(name: String?,  value: Any?): JsonObject {
        val current = nameValuePairs[checkName(name)] ?: return put(name, value)
        if (current is JsonArray) {
            current.checkedPut(value)
        } else {
            val array = JsonArray()
            array.checkedPut(current)
            array.checkedPut(value)
            nameValuePairs[name] = array
        }
        return this
    }

    /**
     * Appends values to the array mapped to `name`. A new [JsonArray]
     * mapping for `name` will be inserted if no mapping exists. If the existing
     * mapping for `name` is not a [JsonArray], a [JsonException]
     * will be thrown.
     *
     * @throws JsonException if `name` is `null` or if the mapping for
     * `name` is non-null and is not a [JsonArray].
     *
     * @hide
     */
    @Throws(JsonException::class)
    fun append(name: String, value: Any?): JsonObject {
        val current = nameValuePairs[checkName(name)]
        val array: JsonArray
        if (current is JsonArray) {
            array = current
        } else if (current == null) {
            val newArray = JsonArray()
            nameValuePairs[name] = newArray
            array = newArray
        } else {
            throw JsonException("Key $name is not a JsonArray")
        }
        array.checkedPut(value)
        return this
    }

    @Throws(JsonException::class)
    fun checkName(name: String?): String {
        if (name == null) {
            throw JsonException("Names must be non-null")
        }
        return name
    }

    /**
     * Removes the named mapping if it exists; does nothing otherwise.
     *
     * @return the value previously mapped by `name`, or null if there was
     * no such mapping.
     */
    fun remove( name: String?): Any {
        return nameValuePairs.remove(name)!!
    }

    /**
     * Returns true if this object has no mapping for `name` or if it has
     * a mapping whose value is [.NULL].
     */
    fun isNull( name: String?): Boolean {
        val value = nameValuePairs[name]
        return value == null || value === JsonObject.Companion.NULL
    }

    /**
     * Returns true if this object has a mapping for `name`. The mapping
     * may be [.NULL].
     */
    fun has( name: String?): Boolean {
        return nameValuePairs.containsKey(name)
    }

    /**
     * Returns the value mapped by `name`, or throws if no such mapping exists.
     *
     * @throws JsonException if no such mapping exists.
     */
    
    @Throws(JsonException::class)
    operator fun get(name: String): Any {
        return nameValuePairs[name] ?: throw JsonException("No value for $name")
    }

    /**
     * Returns the value mapped by `name`, or null if no such mapping
     * exists.
     */
    
    fun opt( name: String?): Any? {
        return nameValuePairs[name]
    }

    /**
     * Returns the value mapped by `name` if it exists and is a boolean or
     * can be coerced to a boolean, or throws otherwise.
     *
     * @throws JsonException if the mapping doesn't exist or cannot be coerced
     * to a boolean.
     */
    @Throws(JsonException::class)
    fun getBoolean(name: String): Boolean {
        val `object` = get(name)
        return Json.toBoolean(`object`) ?: throw Json.typeMismatch(name, `object`, "boolean")
    }
    /**
     * Returns the value mapped by `name` if it exists and is a boolean or
     * can be coerced to a boolean, or `fallback` otherwise.
     */
    /**
     * Returns the value mapped by `name` if it exists and is a boolean or
     * can be coerced to a boolean, or false otherwise.
     */
    @JvmOverloads
    fun optBoolean(name: String?, fallback: Boolean = false): Boolean {
        val `object` = opt(name)
        return Json.toBoolean(`object`) ?: fallback
    }

    /**
     * Returns the value mapped by `name` if it exists and is a double or
     * can be coerced to a double, or throws otherwise.
     *
     * @throws JsonException if the mapping doesn't exist or cannot be coerced
     * to a double.
     */
    @Throws(JsonException::class)
    fun getDouble(name: String): Double {
        val `object` = get(name)
        return Json.toDouble(`object`) ?: throw Json.typeMismatch(name, `object`, "double")
    }
    /**
     * Returns the value mapped by `name` if it exists and is a double or
     * can be coerced to a double, or `fallback` otherwise.
     */
    /**
     * Returns the value mapped by `name` if it exists and is a double or
     * can be coerced to a double, or `NaN` otherwise.
     */
    @JvmOverloads
    fun optDouble(name: String?, fallback: Double = Double.NaN): Double {
        val `object` = opt(name)
        return Json.toDouble(`object`) ?: fallback
    }

    /**
     * Returns the value mapped by `name` if it exists and is an int or
     * can be coerced to an int, or throws otherwise.
     *
     * @throws JsonException if the mapping doesn't exist or cannot be coerced
     * to an int.
     */
    @Throws(JsonException::class)
    fun getInt(name: String): Int {
        val `object` = get(name)
        return Json.toInteger(`object`) ?: throw Json.typeMismatch(name, `object`, "int")
    }
    /**
     * Returns the value mapped by `name` if it exists and is an int or
     * can be coerced to an int, or `fallback` otherwise.
     */
    /**
     * Returns the value mapped by `name` if it exists and is an int or
     * can be coerced to an int, or 0 otherwise.
     */
    @JvmOverloads
    fun optInt(name: String?, fallback: Int = 0): Int {
        val `object` = opt(name)
        return Json.toInteger(`object`) ?: fallback
    }

    /**
     * Returns the value mapped by `name` if it exists and is a long or
     * can be coerced to a long, or throws otherwise.
     * Note that Json represents numbers as doubles,
     * so this is [lossy](#lossy); use strings to transfer numbers via Json.
     *
     * @throws JsonException if the mapping doesn't exist or cannot be coerced
     * to a long.
     */
    @Throws(JsonException::class)
    fun getLong(name: String): Long {
        val `object` = get(name)
        return Json.toLong(`object`) ?: throw Json.typeMismatch(name, `object`, "long")
    }
    /**
     * Returns the value mapped by `name` if it exists and is a long or
     * can be coerced to a long, or `fallback` otherwise. Note that Json represents
     * numbers as doubles, so this is [lossy](#lossy); use strings to transfer
     * numbers via Json.
     */
    /**
     * Returns the value mapped by `name` if it exists and is a long or
     * can be coerced to a long, or 0 otherwise. Note that Json represents numbers as doubles,
     * so this is [lossy](#lossy); use strings to transfer numbers via Json.
     */
    @JvmOverloads
    fun optLong(name: String?, fallback: Long = 0L): Long {
        val `object` = opt(name)
        return Json.toLong(`object`) ?: fallback
    }

    /**
     * Returns the value mapped by `name` if it exists, coercing it if
     * necessary, or throws if no such mapping exists.
     *
     * @throws JsonException if no such mapping exists.
     */
    
    @Throws(JsonException::class)
    fun getString(name: String): String {
        val `object` = get(name)
        return Json.toString(`object`) ?: throw Json.typeMismatch(name, `object`, "String")
    }

    /**
     * Returns the value mapped by `name` if it exists, coercing it if
     * necessary, or the empty string if no such mapping exists.
     */
    
    fun optString( name: String?): String {
        return optString(name, "")
    }

    /**
     * Returns the value mapped by `name` if it exists, coercing it if
     * necessary, or `fallback` if no such mapping exists.
     */

    fun optString(name: String?, fallback: String?): String {
        val `object` = opt(name)
        return Json.toString(`object`) ?: fallback ?: ""
    }

    /**
     * Returns the value mapped by `name` if it exists and is a `JsonArray`, or throws otherwise.
     *
     * @throws JsonException if the mapping doesn't exist or is not a `JsonArray`.
     */
    
    @Throws(JsonException::class)
    fun getJsonArray(name: String): JsonArray {
        val `object` = get(name)
        return if (`object` is JsonArray) {
            `object`
        } else {
            throw Json.typeMismatch(name, `object`, "JsonArray")
        }
    }

    /**
     * Returns the value mapped by `name` if it exists and is a `JsonArray`, or null otherwise.
     */
    
    fun optJsonArray( name: String?): JsonArray? {
        val `object` = opt(name)
        return if (`object` is JsonArray) `object` else null
    }

    /**
     * Returns the value mapped by `name` if it exists and is a `JsonObject`, or throws otherwise.
     *
     * @throws JsonException if the mapping doesn't exist or is not a `JsonObject`.
     */
    
    @Throws(JsonException::class)
    fun getJsonObject(name: String): JsonObject {
        val `object` = get(name)
        return if (`object` is JsonObject) {
            `object`
        } else {
            throw Json.typeMismatch(name, `object`, "JsonObject")
        }
    }

    /**
     * Returns the value mapped by `name` if it exists and is a `JsonObject`, or null otherwise.
     */
    
    fun optJsonObject( name: String?): JsonObject? {
        val `object` = opt(name)
        return if (`object` is JsonObject) `object` else null
    }

    /**
     * Returns an array with the values corresponding to `names`. The
     * array contains null for names that aren't mapped. This method returns
     * null if `names` is either null or empty.
     */
    
    @Throws(JsonException::class)
    fun toJsonArray( names: JsonArray?): JsonArray? {
        val result = JsonArray()
        if (names == null) {
            return null
        }
        val length = names.length()
        if (length == 0) {
            return null
        }
        for (i in 0 until length) {
            val name: String = Json.toString(names.opt(i)) ?: ""
            result.put(opt(name))
        }
        return result
    }

    /**
     * Returns an iterator of the `String` names in this object. The
     * returned iterator supports [remove][Iterator.remove], which will
     * remove the corresponding mapping from this object. If this object is
     * modified after the iterator is returned, the iterator's behavior is
     * undefined. The order of the keys is undefined.
     */
    
    fun keys(): Iterator<String?> {
        return nameValuePairs.keys.iterator()
    }

    /**
     * Returns the set of `String` names in this object. The returned set
     * is a view of the keys in this object. [Set.remove] will remove
     * the corresponding mapping from this object and set iterator behaviour
     * is undefined if this object is modified after it is returned.
     *
     * See [.keys].
     *
     * @return set of keys in this object
     *
     * @hide
     */
    fun keySet(): Set<String?> {
        return nameValuePairs.keys
    }

    /**
     * Returns an array containing the string names in this object. This method
     * returns null if this object contains no mappings.
     */
    
    fun names(): JsonArray? {
        return if (nameValuePairs.isEmpty()) null else JsonArray(nameValuePairs.keys.toTypedArray())
    }

    /**
     * Encodes this object as a compact Json string, such as:
     * <pre>{"query":"Pizza","locations":[94043,90210]}</pre>
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
     * Encodes this object as a human readable Json string for debugging, such
     * as:
     * <pre>
     * {
     * "query": "Pizza",
     * "locations": [
     * 94043,
     * 90210
     * ]
     * }</pre>
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
        stringer.`object`()
        for ((key, value) in nameValuePairs) {
            stringer.key(key).value(value)
        }
        stringer.endObject()
    }

    companion object {
        private val NEGATIVE_ZERO = -0.0

        /**
         * A sentinel value used to explicitly define a name with no value. Unlike
         * `null`, names with this value:
         *
         *  * show up in the [.names] array
         *  * show up in the [.keys] iterator
         *  * return `true` for [.has]
         *  * do not throw on [.get]
         *  * are included in the encoded Json string.
         *
         *
         *
         * This value violates the general contract of [Object.equals] by
         * returning true when compared to `null`. Its [.toString]
         * method returns "null".
         */
        
        val NULL: Any = object : Any() {
            override fun equals(o: Any?): Boolean {
                return o === this || o == null // API specifies this broken equals implementation
            }

            // at least make the broken equals(null) consistent with Objects.hashCode(null).
            override fun hashCode(): Int {
                return 0
            }

            override fun toString(): String {
                return "null"
            }
        }

        /**
         * Encodes the number as a Json string.
         *
         * @param number a finite value. May not be [NaNs][Double.isNaN] or
         * [infinities][Double.isInfinite].
         */
        
        @Throws(JsonException::class)
        fun numberToString(number: Number?): String {
            if (number == null) {
                throw JsonException("Number must be non-null")
            }
            val doubleValue = number.toDouble()
            Json.checkDouble(doubleValue)

            // the original returns "-0" instead of "-0.0" for negative zero
            if (number == JsonObject.Companion.NEGATIVE_ZERO) {
                return "-0"
            }
            val longValue = number.toLong()
            return if (doubleValue == longValue.toDouble()) {
                longValue.toString()
            } else number.toString()
        }

        /**
         * Encodes `data` as a Json string. This applies quotes and any
         * necessary character escaping.
         *
         * @param data the string to encode. Null will be interpreted as an empty
         * string.
         */
        
        fun quote( data: String?): String {
            return if (data == null) {
                "\"\""
            } else try {
                val stringer = JsonStringer()
                stringer.open(Scope.NULL, "")
                stringer.value(data)
                stringer.close(Scope.NULL, Scope.NULL, "")
                stringer.toString()
            } catch (e: JsonException) {
                throw AssertionError()
            }
        }

        /**
         * Wraps the given object if necessary.
         *
         *
         * If the object is null or , returns [.NULL].
         * If the object is a `JsonArray` or `JsonObject`, no wrapping is necessary.
         * If the object is `NULL`, no wrapping is necessary.
         * If the object is an array or `Collection`, returns an equivalent `JsonArray`.
         * If the object is a `Map`, returns an equivalent `JsonObject`.
         * If the object is a primitive wrapper type or `String`, returns the object.
         * Otherwise if the object is from a `java` package, returns the result of `toString`.
         * If wrapping fails, returns null.
         */
        
        fun wrap( o: Any?): Any? {
            if (o == null) {
                return JsonObject.Companion.NULL
            }
            if (o is JsonArray || o is JsonObject) {
                return o
            }
            if (o == JsonObject.Companion.NULL) {
                return o
            }
            try {
                if (o is Collection<*>) {
                    return JsonArray(o as Collection<*>?)
                } else if (o is Array<*>) {
                    return JsonArray(o)
                }
                if (o is Map<*, *>) {
                    return (o as Map<*, *>?)?.let { JsonObject(it) }
                }
                if (o is Boolean ||
                    o is Byte ||
                    o is Char ||
                    o is Double ||
                    o is Float ||
                    o is Int ||
                    o is Long ||
                    o is Short ||
                    o is String
                ) {
                    return o
                }
                /*
                if (o.javaClass.getPackage().getName().startsWith("java.")) {
                    return o.toString()
                }*/
            } catch (ignored: Exception) {
            }
            return null
        }
    }
}