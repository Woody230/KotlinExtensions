package com.bselzer.ktx.serialization.json

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.decodeFromJsonElement

sealed class JsonContext(
    val instance: Json = Json
) {
    companion object Default : JsonContext()

    /**
     * @return the enum value of the string
     */
    inline fun <reified E : Enum<E>> String.enumValue(): E = instance.decodeFromJsonElement(JsonPrimitive(this))

    /**
     * @return the enum value of the string, or null if it is invalid
     */
    inline fun <reified E : Enum<E>> String.enumValueOrNull(): E? = try {
        enumValue<E>()
    } catch (e: Exception) {
        null
    }

    /**
     * @return the enum values of the collection of strings that can be converted
     */
    inline fun <reified E : Enum<E>> Collection<String?>.validEnumValues(): List<E> =
        mapNotNull { s -> s?.enumValueOrNull<E>() }

    /**
     * @return the enum values of the map of strings that can be converted
     */
    @Suppress("UNCHECKED_CAST")
    inline fun <reified E : Enum<E>, T> Map<String, T>.validEnumValues(): Map<E, T> =
        mapKeys { e -> e.key.enumValueOrNull<E>() }.minus(null) as Map<E, T>
}