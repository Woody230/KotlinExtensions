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

internal object Json {
    /**
     * Returns the input if it is a JSON-permissible value; throws otherwise.
     */
    @Throws(JsonException::class)
    fun checkDouble(d: Double): Double {
        if (d.isInfinite() || d.isNaN()) {
            throw JsonException("Forbidden numeric value: $d")
        }
        return d
    }

    fun toBoolean(value: Any?): Boolean? {
        if (value is Boolean) {
            return value
        } else if (value is String) {
            val stringValue = value
            if ("true".equals(stringValue, ignoreCase = true)) {
                return true
            } else if ("false".equals(stringValue, ignoreCase = true)) {
                return false
            }
        }
        return null
    }

    fun toDouble(value: Any?): Double? {
        if (value is Double) {
            return value
        } else if (value is Number) {
            return value.toDouble()
        } else if (value is String) {
            try {
                return value.toDoubleOrNull()
            } catch (ignored: NumberFormatException) {
            }
        }
        return null
    }

    fun toInteger(value: Any?): Int? {
        if (value is Int) {
            return value
        } else if (value is Number) {
            return value.toInt()
        } else if (value is String) {
            try {
                return value.toIntOrNull()
            } catch (ignored: NumberFormatException) {
            }
        }
        return null
    }

    fun toLong(value: Any?): Long? {
        if (value is Long) {
            return value
        } else if (value is Number) {
            return value.toLong()
        } else if (value is String) {
            try {
                return value.toLongOrNull()
            } catch (ignored: NumberFormatException) {
            }
        }
        return null
    }

    fun toString(value: Any?): String? {
        if (value is String) {
            return value
        } else if (value != null) {
            return value.toString()
        }
        return null
    }

    @Throws(JsonException::class)
    fun typeMismatch(
        indexOrName: Any, actual: Any?,
        requiredType: String
    ): JsonException {
        if (actual == null) {
            throw JsonException("Value at $indexOrName is null.")
        } else {
            throw JsonException(
                "Value " + actual + " at " + indexOrName
                        + " of type " + actual::class.qualifiedName
                        + " cannot be converted to " + requiredType
            )
        }
    }

    @Throws(JsonException::class)
    fun typeMismatch(actual: Any?, requiredType: String): JsonException {
        if (actual == null) {
            throw JsonException("Value is null.")
        } else {
            throw JsonException(
                ("Value " + actual
                        + " of type " + actual::class.qualifiedName
                        + " cannot be converted to " + requiredType)
            )
        }
    }
}