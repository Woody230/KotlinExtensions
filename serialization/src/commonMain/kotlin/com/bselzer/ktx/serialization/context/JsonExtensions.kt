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
fun JsonObject.getBooleanOrFalse(key: String): Boolean = getBooleanOrNull(key) ?: false

fun JsonObject.getContent(key: String): String = getValue(key).toContent()
fun JsonObject.getInt(key: String): Int = getValue(key).toInt()
fun JsonObject.getLong(key: String): Long = getValue(key).toLong()
fun JsonObject.getFloat(key: String): Float = getValue(key).toFloat()
fun JsonObject.getDouble(key: String): Double = getValue(key).toDouble()
fun JsonObject.getBoolean(key: String): Boolean = getValue(key).toBoolean()

fun JsonElement.toContentOrNull(): String? = jsonPrimitive.contentOrNull
fun JsonElement.toIntOrNull(): Int? = jsonPrimitive.intOrNull
fun JsonElement.toLongOrNull(): Long? = jsonPrimitive.longOrNull
fun JsonElement.toFloatOrNull(): Float? = jsonPrimitive.floatOrNull
fun JsonElement.toDoubleOrNull(): Double? = jsonPrimitive.doubleOrNull
fun JsonElement.toBooleanOrNull(): Boolean? = jsonPrimitive.booleanOrNull

fun JsonElement.toContent(): String = jsonPrimitive.content
fun JsonElement.toInt(): Int = jsonPrimitive.int
fun JsonElement.toLong(): Long = jsonPrimitive.long
fun JsonElement.toFloat(): Float = jsonPrimitive.float
fun JsonElement.toDouble(): Double = jsonPrimitive.double
fun JsonElement.toBoolean(): Boolean = jsonPrimitive.boolean

fun JsonElement.toContentList(): List<String> = jsonArray.toContentList()
fun JsonElement.toIntList(): List<Int> = jsonArray.toIntList()
fun JsonElement.toLongList(): List<Long> = jsonArray.toLongList()
fun JsonElement.toFloatList(): List<Float> = jsonArray.toFloatList()
fun JsonElement.toDoubleList(): List<Double> = jsonArray.toDoubleList()
fun JsonElement.toBooleanList(): List<Boolean> = jsonArray.toBooleanList()

fun JsonElement.toContentMap(): Map<String, String> = jsonObject.toContentMap()
fun JsonElement.toIntMap(): Map<String, Int> = jsonObject.toIntMap()
fun JsonElement.toLongMap(): Map<String, Long> = jsonObject.toLongMap()
fun JsonElement.toFloatMap(): Map<String, Float> = jsonObject.toFloatMap()
fun JsonElement.toDoubleMap(): Map<String, Double> = jsonObject.toDoubleMap()
fun JsonElement.toBooleanMap(): Map<String, Boolean> = jsonObject.toBooleanMap()

fun <Value> JsonArray.toList(getValue: (JsonElement) -> Value): List<Value> = map(getValue)
fun JsonArray.toContentList(): List<String> = toList(JsonElement::toContent)
fun JsonArray.toIntList(): List<Int> = toList(JsonElement::toInt)
fun JsonArray.toLongList(): List<Long> = toList(JsonElement::toLong)
fun JsonArray.toFloatList(): List<Float> = toList(JsonElement::toFloat)
fun JsonArray.toDoubleList(): List<Double> = toList(JsonElement::toDouble)
fun JsonArray.toBooleanList(): List<Boolean> = toList(JsonElement::toBoolean)

fun <Value> JsonObject.getListOrEmpty(key: String, getValue: (JsonElement) -> Value): List<Value> {
    val array = get(key)?.jsonArray ?: return emptyList()
    return array.map(getValue)
}
fun JsonObject.getContentListOrEmpty(key: String): List<String> = getListOrEmpty(key, JsonElement::toContent)
fun JsonObject.getIntListOrEmpty(key: String): List<Int> = getListOrEmpty(key, JsonElement::toInt)
fun JsonObject.getLongListOrEmpty(key: String): List<Long> = getListOrEmpty(key, JsonElement::toLong)
fun JsonObject.getFloatListOrEmpty(key: String): List<Float> = getListOrEmpty(key, JsonElement::toFloat)
fun JsonObject.getDoubleListOrEmpty(key: String): List<Double> = getListOrEmpty(key, JsonElement::toDouble)
fun JsonObject.getBooleanListOrEmpty(key: String): List<Boolean> = getListOrEmpty(key, JsonElement::toBoolean)

fun <Value> JsonObject.toMap(getValue: (JsonElement) -> Value): Map<String, Value> = mapValues { entry -> getValue(entry.value) }
fun JsonObject.toContentMap(): Map<String, String> = toMap(JsonElement::toContent)
fun JsonObject.toIntMap(): Map<String, Int> = toMap(JsonElement::toInt)
fun JsonObject.toLongMap(): Map<String, Long> = toMap(JsonElement::toLong)
fun JsonObject.toFloatMap(): Map<String, Float> = toMap(JsonElement::toFloat)
fun JsonObject.toDoubleMap(): Map<String, Double> = toMap(JsonElement::toDouble)
fun JsonObject.toBooleanMap(): Map<String, Boolean> = toMap(JsonElement::toBoolean)

fun <Value> JsonObject.getMapOrEmpty(key: String, getValue: (JsonElement) -> Value): Map<String, Value> {
    val jsonObject = get(key)?.jsonObject ?: return emptyMap()
    return jsonObject.mapValues { entry -> getValue(entry.value) }
}

fun JsonObject.getContentMapOrEmpty(key: String): Map<String, String> = getMapOrEmpty(key, JsonElement::toContent)
fun JsonObject.getIntMapOrEmpty(key: String): Map<String, Int> = getMapOrEmpty(key, JsonElement::toInt)
fun JsonObject.getLongMapOrEmpty(key: String): Map<String, Long> = getMapOrEmpty(key, JsonElement::toLong)
fun JsonObject.getFloatMapOrEmpty(key: String): Map<String, Float> = getMapOrEmpty(key, JsonElement::toFloat)
fun JsonObject.getDoubleMapOrEmpty(key: String): Map<String, Double> = getMapOrEmpty(key, JsonElement::toDouble)
fun JsonObject.getBooleanMapOrEmpty(key: String): Map<String, Boolean> = getMapOrEmpty(key, JsonElement::toBoolean)

fun <Value> JsonObject.getObject(key: String, getValue: (JsonObject) -> Value): Value {
    val jsonObject = getValue(key).jsonObject
    return getValue(jsonObject)
}

fun <Value> JsonObject.getObjectOrNull(key: String, getValue: (JsonObject) -> Value): Value? {
    val jsonObject = get(key)?.jsonObject ?: return null
    return getValue(jsonObject)
}

fun <Value> JsonObject.getElement(key: String, getValue: (JsonElement) -> Value): Value {
    val jsonElement = getValue(key)
    return getValue(jsonElement)
}

fun <Value> JsonObject.getElementOrNull(key: String, getValue: (JsonElement) -> Value): Value? {
    val jsonElement = get(key) ?: return null
    return getValue(jsonElement)
}

fun <Value> JsonObject.getObjectMapOrEmpty(
    key: String,
    getValue: (JsonObject) -> Value
): Map<String, Value> = getMapOrEmpty(key) { element ->
    getValue(element.jsonObject)
}

fun <Value> JsonObject.getObjectListOrEmpty(
    key: String,
    getValue: (JsonObject) -> Value
) = getListOrEmpty(key) { element ->
    getValue(element.jsonObject)
}