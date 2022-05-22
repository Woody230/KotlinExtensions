package com.bselzer.ktx.serialization.json.function

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.decodeFromJsonElement

/**
 * @return the enum value of the string
 */
inline fun <reified E : Enum<E>> String.enumValue(json: Json = Json): E = json.decodeFromJsonElement(JsonPrimitive(this))

/**
 * @return the enum value of the string, or null if it is invalid
 */
inline fun <reified E : Enum<E>> String.enumValueOrNull(json: Json = Json): E? = try
{
    enumValue<E>(json)
} catch (e: Exception)
{
    null
}

/**
 * @return the enum values of the collection of strings that can be converted
 */
inline fun <reified E : Enum<E>> Collection<String?>.validEnumValues(json: Json = Json): List<E> =
    mapNotNull { s -> s?.enumValueOrNull<E>(json) }

/**
 * @return the enum values of the map of strings that can be converted
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified E : Enum<E>, T> Map<String, T>.validEnumValues(json: Json = Json): Map<E, T> =
    mapKeys { e -> e.key.enumValueOrNull<E>(json) }.minus(null) as Map<E, T>