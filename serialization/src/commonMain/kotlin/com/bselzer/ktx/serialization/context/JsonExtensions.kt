package com.bselzer.ktx.serialization.context

import kotlinx.serialization.json.*

fun JsonElement.toPrimitiveOrNull(): JsonPrimitive? = if (this is JsonPrimitive) this else null
fun JsonElement.toObjectOrNull(): JsonObject? = if (this is JsonObject) this else null
fun JsonElement.toArrayOrNull(): JsonArray? = if (this is JsonArray) this else null

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

fun JsonArray.toContentList(): List<String> = map { element -> element.toContent() }
fun JsonArray.toIntList(): List<Int> = map { element -> element.toInt() }
fun JsonArray.toLongList(): List<Long> = map { element -> element.toLong() }
fun JsonArray.toFloatList(): List<Float> = map { element -> element.toFloat() }
fun JsonArray.toDoubleList(): List<Double> = map { element -> element.toDouble() }
fun JsonArray.toBooleanList(): List<Boolean> = map { element -> element.toBoolean() }

fun JsonObject.getContentListOrEmpty(key: String): List<String> = get(key)?.jsonArray?.toContentList() ?: emptyList()
fun JsonObject.getIntListOrEmpty(key: String): List<Int> = get(key)?.jsonArray?.toIntList() ?: emptyList()
fun JsonObject.getLongListOrEmpty(key: String): List<Long> = get(key)?.jsonArray?.toLongList() ?: emptyList()
fun JsonObject.getFloatListOrEmpty(key: String): List<Float> = get(key)?.jsonArray?.toFloatList() ?: emptyList()
fun JsonObject.getDoubleListOrEmpty(key: String): List<Double> = get(key)?.jsonArray?.toDoubleList() ?: emptyList()
fun JsonObject.getBooleanListOrEmpty(key: String): List<Boolean> = get(key)?.jsonArray?.toBooleanList() ?: emptyList()

fun JsonObject.toContentMap(): Map<String, String> = mapValues { entry -> entry.value.toContent() }
fun JsonObject.toIntMap(): Map<String, Int> = mapValues { entry -> entry.value.toInt() }
fun JsonObject.toLongMap(): Map<String, Long> = mapValues { entry -> entry.value.toLong() }
fun JsonObject.toFloatMap(): Map<String, Float> = mapValues { entry -> entry.value.toFloat() }
fun JsonObject.toDoubleMap(): Map<String, Double> = mapValues { entry -> entry.value.toDouble() }
fun JsonObject.toBooleanMap(): Map<String, Boolean> = mapValues { entry -> entry.value.toBoolean() }

fun JsonObject.getContentMapOrEmpty(key: String): Map<String, String> = get(key)?.jsonObject?.toContentMap() ?: emptyMap()
fun JsonObject.getIntMapOrEmpty(key: String): Map<String, Int> = get(key)?.jsonObject?.toIntMap() ?: emptyMap()
fun JsonObject.getLongMapOrEmpty(key: String): Map<String, Long> = get(key)?.jsonObject?.toLongMap() ?: emptyMap()
fun JsonObject.getFloatMapOrEmpty(key: String): Map<String, Float> = get(key)?.jsonObject?.toFloatMap() ?: emptyMap()
fun JsonObject.getDoubleMapOrEmpty(key: String): Map<String, Double> = get(key)?.jsonObject?.toDoubleMap() ?: emptyMap()
fun JsonObject.getBooleanMapOrEmpty(key: String): Map<String, Boolean> = get(key)?.jsonObject?.toBooleanMap() ?: emptyMap()
