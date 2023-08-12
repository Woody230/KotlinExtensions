package com.bselzer.ktx.serialization

import kotlinx.serialization.json.*
import net.mamoe.yamlkt.*

/**
 * Converts a [YamlNull] to a [JsonNull], a [YamlLiteral] to a [JsonPrimitive], a [YamlList] to a [JsonArray], and a [YamlMap] to a [JsonObject].
 */
fun YamlElement.toJsonElement(): JsonElement = when (this) {
    is YamlNull -> JsonNull
    is YamlLiteral -> toJsonPrimitive()
    is YamlList -> toJsonArray()
    is YamlMap -> toJsonObject()
}

fun YamlLiteral.toJsonPrimitive(): JsonPrimitive = when {
    // Use the specific constructors if applicable so that isString is set appropriately.
    content.toBooleanStrictOrNull() != null -> JsonPrimitive(content.toBooleanStrict())

    // If there is no decimal, then prefer to store in a long.
    content.all(Char::isDigit) && content.toLongOrNull() != null -> JsonPrimitive(content.toLong())
    content.toDoubleOrNull() != null -> JsonPrimitive(content.toDouble())

    else -> JsonPrimitive(content)
}

fun YamlList.toJsonArray(): JsonArray {
    val elements = content.map(YamlElement::toJsonElement)
    return JsonArray(elements)
}

fun YamlMap.toJsonObject(): JsonObject {
    @Suppress("unchecked_cast")
    val stringKeys = content.mapKeys { entry -> entry.key.literalContentOrNull }.filterKeys { key -> key != null } as Map<String, YamlElement>
    val elementValues = stringKeys.mapValues { entry -> entry.value.toJsonElement() }
    return JsonObject(elementValues)
}

/**
 * Converts a [JsonNull] to a [YamlNull], a [JsonPrimitive] to a [YamlLiteral], a [JsonArray] to a [YamlList], and a [JsonObject] to a [YamlMap]
 */
fun JsonElement.toYamlElement(): YamlElement = when (this) {
    is JsonNull -> YamlNull
    is JsonPrimitive -> toYamlLiteral()
    is JsonArray -> toYamlList()
    is JsonObject -> toYamlMap()
}

fun JsonPrimitive.toYamlLiteral(): YamlLiteral = YamlLiteral(content)

fun JsonArray.toYamlList(): YamlList {
    val elements = map(JsonElement::toYamlElement)
    return YamlList(elements)
}

fun JsonObject.toYamlMap(): YamlMap {
    val stringKeys = mapKeys { entry -> YamlPrimitive(entry.key) }
    val elementValues = stringKeys.mapValues { entry -> entry.value.toYamlElement() }
    return YamlMap(elementValues)
}