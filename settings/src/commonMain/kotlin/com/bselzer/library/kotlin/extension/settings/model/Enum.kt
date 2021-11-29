package com.bselzer.library.kotlin.extension.settings.model

import com.russhwolf.settings.Settings
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

/**
 * Retrieves the [Enum] as a [String] at the [key].
 *
 * @param key the location
 * @param defaultValue the default value
 * @param E the enum
 * @return the enum value stored at the [key], or the [defaultValue] if it does not exist
 */
@OptIn(ExperimentalSerializationApi::class)
inline fun <reified E : Enum<E>> Settings.getEnum(key: String, defaultValue: E): E = getEnumOrNull<E>(key) ?: defaultValue

/**
 * Retrieves the [Enum] as a [String] at the [key].
 *
 * @param key the location
 * @param E the enum
 * @return the enum value stored at the [key], or null if it does not exist
 */
@OptIn(ExperimentalSerializationApi::class)
inline fun <reified E : Enum<E>> Settings.getEnumOrNull(key: String): E? = getEnumOrNull<E>(serializer(), key)

/**
 * Retrieves the [Enum] as a [String] at the [key].
 *
 * @param deserializer the deserializer
 * @param key the location
 * @param defaultValue the default value
 * @param E the enum
 * @return the enum value stored at the [key], or the [defaultValue] if it does not exist
 */
@OptIn(ExperimentalSerializationApi::class)
fun <E : Enum<E>> Settings.getEnum(deserializer: DeserializationStrategy<E>, key: String, defaultValue: E): E = getEnumOrNull(deserializer, key) ?: defaultValue

/**
 * Retrieves the [Enum] as a [String] at the [key].
 *
 * @param deserializer the deserializer
 * @param key the location
 * @param E the enum
 * @return the enum value stored at the [key], or null if it does not exist
 */
@OptIn(ExperimentalSerializationApi::class)
fun <E : Enum<E>> Settings.getEnumOrNull(deserializer: DeserializationStrategy<E>, key: String): E? {
    try {
        val value = getStringOrNull(key) ?: return null
        return Json.decodeFromString(deserializer, value)
    } catch (exception: Exception) {
        return null
    }
}

/**
 * Stores the [Enum] as a [String] at the [key].
 *
 * @param key the location
 * @param E the enum
 */
@OptIn(ExperimentalSerializationApi::class)
inline fun <reified E : Enum<E>> Settings.putEnum(key: String, value: E) = putEnum(serializer(), key, value)

/**
 * Stores the [Enum] as a [String] at the [key].
 *
 * @param serializer the serializer
 * @param key the location
 * @param E the enum
 */
@OptIn(ExperimentalSerializationApi::class)
fun <E : Enum<E>> Settings.putEnum(serializer: SerializationStrategy<E>, key: String, value: E) = putString(key, Json.encodeToString(serializer, value))

