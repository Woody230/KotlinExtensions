package com.bselzer.ktx.serialization.context

import kotlinx.serialization.json.*

fun JsonElement.toPrimitiveOrNull(): JsonPrimitive? = if (this is JsonPrimitive) this else null
fun JsonElement.toJsonObjectOrNull(): JsonObject? = if (this is JsonObject) this else null
fun JsonElement.toJsonArrayOrNull(): JsonArray? = if (this is JsonArray) this else null

fun JsonObject.getContentOrNull(key: String): String? = get(key)?.toContentOrNull()
fun JsonObject.getIntOrNull(key: String): Int? = get(key)?.toIntOrNull()
fun JsonObject.getLongOrNull(key: String): Long? = get(key)?.toLongOrNull()
fun JsonObject.getFloatOrNull(key: String): Float? = get(key)?.toFloatOrNull()
fun JsonObject.getDoubleOrNull(key: String): Double? = get(key)?.toDoubleOrNull()
fun JsonObject.getBooleanOrNull(key: String): Boolean? = get(key)?.toBooleanOrNull()

fun JsonObject.getContent(key: String): String = getValue(key).toContent()
fun JsonObject.getInt(key: String): Int = getValue(key).toInt()
fun JsonObject.getLong(key: String): Long = getValue(key).toLong()
fun JsonObject.getFloat(key: String): Float = getValue(key).toFloat()
fun JsonObject.getDouble(key: String): Double = getValue(key).toDouble()
fun JsonObject.getBoolean(key: String): Boolean = getValue(key).toBoolean()

fun JsonElement.toContentOrNull() = jsonPrimitive.contentOrNull
fun JsonElement.toIntOrNull() = jsonPrimitive.intOrNull
fun JsonElement.toLongOrNull() = jsonPrimitive.longOrNull
fun JsonElement.toFloatOrNull() = jsonPrimitive.floatOrNull
fun JsonElement.toDoubleOrNull() = jsonPrimitive.doubleOrNull
fun JsonElement.toBooleanOrNull() = jsonPrimitive.booleanOrNull

fun JsonElement.toContent() = jsonPrimitive.content
fun JsonElement.toInt() = jsonPrimitive.int
fun JsonElement.toLong() = jsonPrimitive.long
fun JsonElement.toFloat() = jsonPrimitive.float
fun JsonElement.toDouble() = jsonPrimitive.double
fun JsonElement.toBoolean() = jsonPrimitive.boolean

fun JsonArray.toContents(): List<String> = map { element -> element.toContent() }
fun JsonArray.toInts(): List<Int> = map { element -> element.toInt() }
fun JsonArray.toLongs(): List<Long> = map { element -> element.toLong() }
fun JsonArray.toFloats(): List<Float> = map { element -> element.toFloat() }
fun JsonArray.toDoubles(): List<Double> = map { element -> element.toDouble() }
fun JsonArray.toBooleans(): List<Boolean> = map { element -> element.toBoolean() }

fun JsonObject.getContentsOrEmpty(key: String): List<String> = get(key)?.jsonArray?.toContents() ?: emptyList()
fun JsonObject.getIntsOrEmpty(key: String): List<Int> = get(key)?.jsonArray?.toInts() ?: emptyList()
fun JsonObject.getLongsOrEmpty(key: String): List<Long> = get(key)?.jsonArray?.toLongs() ?: emptyList()
fun JsonObject.getFloatsOrEmpty(key: String): List<Float> = get(key)?.jsonArray?.toFloats() ?: emptyList()
fun JsonObject.getDoublesOrEmpty(key: String): List<Double> = get(key)?.jsonArray?.toDoubles() ?: emptyList()
fun JsonObject.getBooleansOrEmpty(key: String): List<Boolean> = get(key)?.jsonArray?.toBooleans() ?: emptyList()