package com.bselzer.ktx.serialization.context

import kotlinx.serialization.json.*

fun JsonElement.toPrimitiveOrNull(): JsonPrimitive? = if (this is JsonPrimitive) this else null
fun JsonElement.toJsonObjectOrNull(): JsonObject? = if (this is JsonObject) this else null
fun JsonElement.toJsonArrayOrNull(): JsonArray? = if (this is JsonArray) this else null

fun JsonObject.getStringOrNull(key: String): String? = get(key)?.toPrimitiveOrNull()?.contentOrNull
fun JsonObject.getIntOrNull(key: String): Int? = get(key)?.toPrimitiveOrNull()?.intOrNull
fun JsonObject.getLongOrNull(key: String): Long? = get(key)?.toPrimitiveOrNull()?.longOrNull
fun JsonObject.getFloatOrNull(key: String): Float? = get(key)?.toPrimitiveOrNull()?.floatOrNull
fun JsonObject.getDoubleOrNull(key: String): Double? = get(key)?.toPrimitiveOrNull()?.doubleOrNull
fun JsonObject.getBooleanOrNull(key: String): Boolean? = get(key)?.toPrimitiveOrNull()?.booleanOrNull

fun JsonObject.getString(key: String): String = getValue(key).jsonPrimitive.content
fun JsonObject.getInt(key: String): Int = getValue(key).jsonPrimitive.int
fun JsonObject.getLong(key: String): Long = getValue(key).jsonPrimitive.long
fun JsonObject.getFloat(key: String): Float = getValue(key).jsonPrimitive.float
fun JsonObject.getDouble(key: String): Double = getValue(key).jsonPrimitive.double
fun JsonObject.getBoolean(key: String): Boolean = getValue(key).jsonPrimitive.boolean