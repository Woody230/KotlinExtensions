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

// Note: this class was written without inspecting the non-free org.Json sourcecode.
/**
 * Implements [JsonObject.toString] and [JsonArray.toString]. Most
 * application developers should use those methods directly and disregard this
 * API. For example:<pre>
 * JsonObject object = ...
 * String Json = object.toString();</pre>
 *
 *
 * Stringers only encode well-formed Json strings. In particular:
 *
 *  * The stringer must have exactly one top-level array or object.
 *  * Lexical scopes must be balanced: every call to [.array] must
 * have a matching call to [.endArray] and every call to [       ][.object] must have a matching call to [.endObject].
 *  * Arrays may not contain keys (property names).
 *  * Objects must alternate keys (property names) and values.
 *  * Values are inserted with either literal [value][.value]
 * calls, or by nesting arrays or objects.
 *
 * Calls that would result in a malformed Json string will fail with a
 * [JsonException].
 *
 *
 * This class provides no facility for pretty-printing (ie. indenting)
 * output. To encode indented output, use [JsonObject.toString] or
 * [JsonArray.toString].
 *
 *
 * Some implementations of the API support at most 20 levels of nesting.
 * Attempts to create more than 20 levels of nesting may fail with a [ ].
 *
 *
 * Each stringer may be used to encode a single top level value. Instances of
 * this class are not thread safe. Although this class is nonfinal, it was not
 * designed for inheritance and should not be subclassed. In particular,
 * self-use by overrideable methods is not specified. See *Effective Java*
 * Item 17, "Design and Document or inheritance or else prohibit it" for further
 * information.
 */
class JsonStringer {
    /** The output data, containing at most one top-level array or object.  */
    val out = StringBuilder()

    /**
     * Unlike the original implementation, this stack isn't limited to 20
     * levels of nesting.
     */
    private val stack: MutableList<Scope> = ArrayList()

    /**
     * A string containing a full set of spaces for a single level of
     * indentation, or null for no pretty printing.
     */
    private val indent: String?

    constructor() {
        indent = null
    }

    internal constructor(indentSpaces: Int) {
        val indentChars = CharArray(indentSpaces)
        indentChars.fill(' ')
        indent = indentChars.concatToString()
    }

    /**
     * Begins encoding a new array. Each call to this method must be paired with
     * a call to [.endArray].
     *
     * @return this stringer.
     */
    @Throws(JsonException::class)
    fun array(): JsonStringer {
        return open(Scope.EMPTY_ARRAY, "[")
    }

    /**
     * Ends encoding the current array.
     *
     * @return this stringer.
     */
    @Throws(JsonException::class)
    fun endArray(): JsonStringer {
        return close(Scope.EMPTY_ARRAY, Scope.NONEMPTY_ARRAY, "]")
    }

    /**
     * Begins encoding a new object. Each call to this method must be paired
     * with a call to [.endObject].
     *
     * @return this stringer.
     */
    @Throws(JsonException::class)
    fun `object`(): JsonStringer {
        return open(Scope.EMPTY_OBJECT, "{")
    }

    /**
     * Ends encoding the current object.
     *
     * @return this stringer.
     */
    @Throws(JsonException::class)
    fun endObject(): JsonStringer {
        return close(Scope.EMPTY_OBJECT, Scope.NONEMPTY_OBJECT, "}")
    }

    /**
     * Enters a new scope by appending any necessary whitespace and the given
     * bracket.
     */
    @Throws(JsonException::class)
    fun open(empty: Scope, openBracket: String?): JsonStringer {
        if (stack.isEmpty() && out.length > 0) {
            throw JsonException("Nesting problem: multiple top-level roots")
        }
        beforeValue()
        stack.add(empty)
        out.append(openBracket)
        return this
    }

    /**
     * Closes the current scope by appending any necessary whitespace and the
     * given bracket.
     */
    @Throws(JsonException::class)
    fun close(empty: Scope, nonempty: Scope, closeBracket: String?): JsonStringer {
        val context: Scope = peek()
        if (context != nonempty && context != empty) {
            throw JsonException("Nesting problem")
        }
        stack.removeAt(stack.size - 1)
        if (context == nonempty) {
            newline()
        }
        out.append(closeBracket)
        return this
    }

    /**
     * Returns the value on the top of the stack.
     */
    @Throws(JsonException::class)
    private fun peek(): Scope {
        if (stack.isEmpty()) {
            throw JsonException("Nesting problem")
        }
        return stack[stack.size - 1]
    }

    /**
     * Replace the value on the top of the stack with the given value.
     */
    private fun replaceTop(topOfStack: Scope) {
        stack[stack.size - 1] = topOfStack
    }

    /**
     * Encodes `value`.
     *
     * @param value a [JsonObject], [JsonArray], String, Boolean,
     * Integer, Long, Double or null. May not be [NaNs][Double.isNaN]
     * or [infinities][Double.isInfinite].
     * @return this stringer.
     */
    @Throws(JsonException::class)
    fun value(value: Any?): JsonStringer {
        if (stack.isEmpty()) {
            throw JsonException("Nesting problem")
        }
        if (value is JsonArray) {
            value.writeTo(this)
            return this
        } else if (value is JsonObject) {
            value.writeTo(this)
            return this
        }
        beforeValue()
        if (value == null || value is Boolean
            || value === JsonObject.NULL
        ) {
            out.append(value)
        } else if (value is Number) {
            out.append(JsonObject.numberToString(value as Number?))
        } else {
            string(value.toString())
        }
        return this
    }

    /**
     * Encodes `value` to this stringer.
     *
     * @return this stringer.
     */
    @Throws(JsonException::class)
    fun value(value: Boolean): JsonStringer {
        if (stack.isEmpty()) {
            throw JsonException("Nesting problem")
        }
        beforeValue()
        out.append(value)
        return this
    }

    /**
     * Encodes `value` to this stringer.
     *
     * @param value a finite value. May not be [NaNs][Double.isNaN] or
     * [infinities][Double.isInfinite].
     * @return this stringer.
     */
    @Throws(JsonException::class)
    fun value(value: Double): JsonStringer {
        if (stack.isEmpty()) {
            throw JsonException("Nesting problem")
        }
        beforeValue()
        out.append(JsonObject.numberToString(value))
        return this
    }

    /**
     * Encodes `value` to this stringer.
     *
     * @return this stringer.
     */
    @Throws(JsonException::class)
    fun value(value: Long): JsonStringer {
        if (stack.isEmpty()) {
            throw JsonException("Nesting problem")
        }
        beforeValue()
        out.append(value)
        return this
    }

    private fun string(value: String) {
        out.append("\"")
        var i = 0
        val length = value.length
        while (i < length) {
            val c = value[i]
            when (c) {
                '"', '\\', '/' -> out.append('\\').append(c)
                '\t' -> out.append("\\t")
                '\b' -> out.append("\\b")
                '\n' -> out.append("\\n")
                '\r' -> out.append("\\r")
                // TODO form feed '\f' -> out.append("\\f")
                else -> out.append(c)
                /* TODO no formatting available if (c.code <= 0x1F) {
                    out.append(String.format("\\u%04x", c))
                } else {
                    out.append(c)
                }*/
            }
            i++
        }
        out.append("\"")
    }

    private fun newline() {
        if (indent == null) {
            return
        }
        out.append("\n")
        for (i in stack.indices) {
            out.append(indent)
        }
    }

    /**
     * Encodes the key (property name) to this stringer.
     *
     * @param name the name of the forthcoming value. May not be null.
     * @return this stringer.
     */
    @Throws(JsonException::class)
    fun key(name: String?): JsonStringer {
        if (name == null) {
            throw JsonException("Names must be non-null")
        }
        beforeKey()
        string(name)
        return this
    }

    /**
     * Inserts any necessary separators and whitespace before a name. Also
     * adjusts the stack to expect the key's value.
     */
    @Throws(JsonException::class)
    private fun beforeKey() {
        val context: Scope = peek()
        if (context == Scope.NONEMPTY_OBJECT) { // first in object
            out.append(',')
        } else if (context != Scope.EMPTY_OBJECT) { // not in an object!
            throw JsonException("Nesting problem")
        }
        newline()
        replaceTop(Scope.DANGLING_KEY)
    }

    /**
     * Inserts any necessary separators and whitespace before a literal value,
     * inline array, or inline object. Also adjusts the stack to expect either a
     * closing bracket or another element.
     */
    @Throws(JsonException::class)
    private fun beforeValue() {
        if (stack.isEmpty()) {
            return
        }
        val context: Scope = peek()
        if (context == Scope.EMPTY_ARRAY) { // first in array
            replaceTop(Scope.NONEMPTY_ARRAY)
            newline()
        } else if (context == Scope.NONEMPTY_ARRAY) { // another in array
            out.append(',')
            newline()
        } else if (context == Scope.DANGLING_KEY) { // value for key
            out.append(if (indent == null) ":" else ": ")
            replaceTop(Scope.NONEMPTY_OBJECT)
        } else if (context != Scope.NULL) {
            throw JsonException("Nesting problem")
        }
    }

    /**
     * Returns the encoded Json string.
     *
     *
     * If invoked with unterminated arrays or unclosed objects, this method's
     * return value is undefined.
     *
     *
     * **Warning:** although it contradicts the general contract
     * of [Object.toString], this method returns null if the stringer
     * contains no data.
     */
    override fun toString(): String {
        return if (out.isEmpty()) "" else out.toString()
    }
}