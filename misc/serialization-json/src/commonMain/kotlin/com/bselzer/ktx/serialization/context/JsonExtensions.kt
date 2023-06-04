package com.bselzer.ktx.serialization.context

import com.bselzer.ktx.serialization.context.JsonContext.Default.decode
import com.bselzer.ktx.serialization.context.JsonContext.Default.decodeValues
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.double
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.float
import kotlinx.serialization.json.floatOrNull
import kotlinx.serialization.json.int
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.long
import kotlinx.serialization.json.longOrNull

fun JsonElement.toPrimitiveOrNull(): JsonPrimitive? = if (this is JsonPrimitive) this else null
fun JsonElement.toObjectOrNull(): JsonObject? = if (this is JsonObject) this else null
fun JsonElement.toArrayOrNull(): JsonArray? = if (this is JsonArray) this else null

fun JsonPrimitive.toNumberOrNull(): Number? = content.toLongOrNull() ?: content.toDoubleOrNull()

inline fun <reified T> JsonObject.getEnum(key: String): T = getContent(key).decode()
inline fun <reified T> JsonObject.getEnumOrNull(key: String): T? = getContentOrNull(key)?.decode<T>()
fun JsonObject.getContentOrNull(key: String): String? = get(key)?.toContentOrNull()
fun JsonObject.getIntOrNull(key: String): Int? = get(key)?.toIntOrNull()
fun JsonObject.getLongOrNull(key: String): Long? = get(key)?.toLongOrNull()
fun JsonObject.getFloatOrNull(key: String): Float? = get(key)?.toFloatOrNull()
fun JsonObject.getDoubleOrNull(key: String): Double? = get(key)?.toDoubleOrNull()
fun JsonObject.getBooleanOrNull(key: String): Boolean? = get(key)?.toBooleanOrNull()
fun JsonObject.getBooleanOrFalse(key: String): Boolean = getBooleanOrNull(key) ?: false
fun JsonObject.getNumberOrNull(key: String): Number? = get(key)?.toNumberOrNull()

fun JsonObject.getContent(key: String): String = getValue(key).toContent()
fun JsonObject.getInt(key: String): Int = getValue(key).toInt()
fun JsonObject.getLong(key: String): Long = getValue(key).toLong()
fun JsonObject.getFloat(key: String): Float = getValue(key).toFloat()
fun JsonObject.getDouble(key: String): Double = getValue(key).toDouble()
fun JsonObject.getBoolean(key: String): Boolean = getValue(key).toBoolean()

inline fun <reified T> JsonElement.toEnumOrNull(): T? = toContentOrNull()?.decode<T>()
fun JsonElement.toContentOrNull(): String? = toPrimitiveOrNull()?.contentOrNull
fun JsonElement.toIntOrNull(): Int? = toPrimitiveOrNull()?.intOrNull
fun JsonElement.toLongOrNull(): Long? = toPrimitiveOrNull()?.longOrNull
fun JsonElement.toFloatOrNull(): Float? = toPrimitiveOrNull()?.floatOrNull
fun JsonElement.toDoubleOrNull(): Double? = toPrimitiveOrNull()?.doubleOrNull
fun JsonElement.toBooleanOrNull(): Boolean? = toPrimitiveOrNull()?.booleanOrNull
fun JsonElement.toNumberOrNull(): Number? = toPrimitiveOrNull()?.toNumberOrNull()

inline fun <reified T> JsonElement.toEnum(): T = toContent().decode()
fun JsonElement.toContent(): String = jsonPrimitive.content
fun JsonElement.toInt(): Int = jsonPrimitive.int
fun JsonElement.toLong(): Long = jsonPrimitive.long
fun JsonElement.toFloat(): Float = jsonPrimitive.float
fun JsonElement.toDouble(): Double = jsonPrimitive.double
fun JsonElement.toBoolean(): Boolean = jsonPrimitive.boolean

inline fun <reified Value> JsonElement.toEnumList(): List<Value> = toContentList().decode()
fun JsonElement.toContentList(): List<String> = jsonArray.toContentList()
fun JsonElement.toIntList(): List<Int> = jsonArray.toIntList()
fun JsonElement.toLongList(): List<Long> = jsonArray.toLongList()
fun JsonElement.toFloatList(): List<Float> = jsonArray.toFloatList()
fun JsonElement.toDoubleList(): List<Double> = jsonArray.toDoubleList()
fun JsonElement.toBooleanList(): List<Boolean> = jsonArray.toBooleanList()

inline fun <reified Value> JsonElement.toEnumMap(): Map<String, Value> = toContentMap().decodeValues()
fun JsonElement.toContentMap(): Map<String, String> = jsonObject.toContentMap()
fun JsonElement.toIntMap(): Map<String, Int> = jsonObject.toIntMap()
fun JsonElement.toLongMap(): Map<String, Long> = jsonObject.toLongMap()
fun JsonElement.toFloatMap(): Map<String, Float> = jsonObject.toFloatMap()
fun JsonElement.toDoubleMap(): Map<String, Double> = jsonObject.toDoubleMap()
fun JsonElement.toBooleanMap(): Map<String, Boolean> = jsonObject.toBooleanMap()

fun <Value> JsonArray.toList(getValue: (JsonElement) -> Value): List<Value> = map(getValue)
inline fun <reified Value> JsonArray.toEnumList(): List<Value> = toContentList().decode()
fun JsonArray.toContentList(): List<String> = toList(JsonElement::toContent)
fun JsonArray.toIntList(): List<Int> = toList(JsonElement::toInt)
fun JsonArray.toLongList(): List<Long> = toList(JsonElement::toLong)
fun JsonArray.toFloatList(): List<Float> = toList(JsonElement::toFloat)
fun JsonArray.toDoubleList(): List<Double> = toList(JsonElement::toDouble)
fun JsonArray.toBooleanList(): List<Boolean> = toList(JsonElement::toBoolean)

fun <Value> JsonObject.getListOrEmpty(key: String, getValue: (JsonElement) -> Value?): List<Value> {
    val array = get(key)?.toArrayOrNull() ?: return emptyList()
    return array.mapNotNull(getValue)
}

inline fun <reified Value> JsonObject.getEnumListOrEmpty(key: String): List<Value> = getContentListOrEmpty(key).decode()
fun JsonObject.getContentListOrEmpty(key: String): List<String> = getListOrEmpty(key, JsonElement::toContentOrNull)
fun JsonObject.getIntListOrEmpty(key: String): List<Int> = getListOrEmpty(key, JsonElement::toIntOrNull)
fun JsonObject.getLongListOrEmpty(key: String): List<Long> = getListOrEmpty(key, JsonElement::toLongOrNull)
fun JsonObject.getFloatListOrEmpty(key: String): List<Float> = getListOrEmpty(key, JsonElement::toFloatOrNull)
fun JsonObject.getDoubleListOrEmpty(key: String): List<Double> = getListOrEmpty(key, JsonElement::toDoubleOrNull)
fun JsonObject.getBooleanListOrEmpty(key: String): List<Boolean> = getListOrEmpty(key, JsonElement::toBooleanOrNull)

fun <Value> JsonObject.toMap(getValue: (JsonElement) -> Value): Map<String, Value> = mapValues { entry -> getValue(entry.value) }
inline fun <reified Value> JsonObject.toEnumMap(): Map<String, Value> = toContentMap().decodeValues()
fun JsonObject.toContentMap(): Map<String, String> = toMap(JsonElement::toContent)
fun JsonObject.toIntMap(): Map<String, Int> = toMap(JsonElement::toInt)
fun JsonObject.toLongMap(): Map<String, Long> = toMap(JsonElement::toLong)
fun JsonObject.toFloatMap(): Map<String, Float> = toMap(JsonElement::toFloat)
fun JsonObject.toDoubleMap(): Map<String, Double> = toMap(JsonElement::toDouble)
fun JsonObject.toBooleanMap(): Map<String, Boolean> = toMap(JsonElement::toBoolean)

@Suppress("UNCHECKED_CAST")
fun <Value> JsonObject.getMapOrEmpty(key: String, getValue: (JsonElement) -> Value?): Map<String, Value> {
    val jsonObject = get(key)?.jsonObject ?: return emptyMap()
    return jsonObject.mapValues { entry -> getValue(entry.value) }.filterValues { value -> value != null } as Map<String, Value>
}

inline fun <reified Value> JsonObject.getEnumMapOrEmpty(key: String): Map<String, Value> = getContentMapOrEmpty(key).decodeValues()
fun JsonObject.getContentMapOrEmpty(key: String): Map<String, String> = getMapOrEmpty(key, JsonElement::toContentOrNull)
fun JsonObject.getIntMapOrEmpty(key: String): Map<String, Int> = getMapOrEmpty(key, JsonElement::toIntOrNull)
fun JsonObject.getLongMapOrEmpty(key: String): Map<String, Long> = getMapOrEmpty(key, JsonElement::toLongOrNull)
fun JsonObject.getFloatMapOrEmpty(key: String): Map<String, Float> = getMapOrEmpty(key, JsonElement::toFloatOrNull)
fun JsonObject.getDoubleMapOrEmpty(key: String): Map<String, Double> = getMapOrEmpty(key, JsonElement::toDoubleOrNull)
fun JsonObject.getBooleanMapOrEmpty(key: String): Map<String, Boolean> = getMapOrEmpty(key, JsonElement::toBooleanOrNull)

fun <Value> JsonObject.getObject(key: String, getValue: (JsonObject) -> Value): Value {
    val jsonObject = getValue(key).jsonObject
    return getValue(jsonObject)
}

fun <Value> JsonObject.getObjectOrNull(key: String, getValue: (JsonObject) -> Value?): Value? {
    val jsonObject = get(key)?.toObjectOrNull() ?: return null
    return getValue(jsonObject)
}

fun <Value> JsonObject.getElement(key: String, getValue: (JsonElement) -> Value): Value {
    val jsonElement = getValue(key)
    return getValue(jsonElement)
}

fun <Value> JsonObject.getElementOrNull(key: String, getValue: (JsonElement) -> Value?): Value? {
    val jsonElement = get(key) ?: return null
    return getValue(jsonElement)
}

fun <Value> JsonObject.getObjectMapOrEmpty(
    key: String,
    getValue: (JsonObject) -> Value?
): Map<String, Value> = getMapOrEmpty(key) { element ->
    element.toObjectOrNull()?.let(getValue)
}

fun <Value> JsonObject.getObjectListOrEmpty(
    key: String,
    getValue: (JsonObject) -> Value?
) = getListOrEmpty(key) { element ->
    element.toObjectOrNull()?.let(getValue)
}