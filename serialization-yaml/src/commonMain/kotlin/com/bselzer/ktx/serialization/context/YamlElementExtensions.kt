package com.bselzer.ktx.serialization.context

import kotlinx.serialization.json.*
import net.mamoe.yamlkt.*

/**
 * Converts a [YamlNull] to a [JsonNull], a [YamlLiteral] to a [JsonPrimitive], a [YamlList] to a [JsonArray], and a [YamlMap] to a [JsonObject].
 */
fun YamlElement.toJsonElement(): JsonElement = when (this) {
    is YamlNull -> JsonNull
    is YamlLiteral -> literalContentOrNull.let { content ->
        when {
            content == null -> JsonNull

            // Use the specific constructors if applicable so that isString is set appropriately.
            content.toBooleanStrictOrNull() != null -> JsonPrimitive(content.toBooleanStrict())

            // If there is no decimal, then prefer to store in a long.
            content.all(Char::isDigit) && content.toLongOrNull() != null -> JsonPrimitive(content.toLong())
            content.toDoubleOrNull() != null -> JsonPrimitive(content.toDouble())

            else -> JsonPrimitive(content)
        }
    }

    is YamlList -> JsonArray(content.map { it.toJsonElement() })
    is YamlMap -> content.let { content ->
        @Suppress("unchecked_cast")
        val stringKeys = content.mapKeys { entry -> entry.key.literalContentOrNull }.filterKeys { key -> key != null } as Map<String, YamlElement>
        JsonObject(stringKeys.mapValues { entry -> entry.value.toJsonElement() })
    }
}

/**
 * Converts a [JsonNull] to a [YamlNull], a [JsonPrimitive] to a [YamlLiteral], a [JsonArray] to a [YamlList], and a [JsonObject] to a [YamlMap]
 */
fun JsonElement.toYamlElement(): YamlElement = when (this) {
    is JsonNull -> YamlNull
    is JsonPrimitive -> YamlLiteral(content)
    is JsonArray -> YamlList(map { it.toYamlElement() })
    is JsonObject -> YamlMap(mapKeys { entry -> YamlPrimitive(entry.key) }.mapValues { entry -> entry.value.toYamlElement() })
}