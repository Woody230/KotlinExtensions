package com.bselzer.ktx.serialization.context

import kotlinx.serialization.json.*
import net.mamoe.yamlkt.*

/**
 * Converts the [YamlNull] to a [JsonNull], a [YamlLiteral] to a [JsonPrimitive], a [YamlList] to a [JsonArray] and a [YamlMap] to a [JsonObject].
 */
fun YamlElement.toJsonElement(): JsonElement = when (this) {
    is YamlNull -> JsonNull
    is YamlLiteral -> literalContentOrNull.let { content ->
        when {
            content == null -> JsonNull

            // Use the specific constructors if applicable so that isString is set appropriately.
            content.toBooleanStrictOrNull() != null -> JsonPrimitive(content.toBooleanStrict())
            content.toDoubleOrNull() != null -> JsonPrimitive(content.toDouble())
            content.toLongOrNull() != null -> JsonPrimitive(content.toLong())

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