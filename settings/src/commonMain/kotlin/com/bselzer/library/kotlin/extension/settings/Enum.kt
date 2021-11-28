package com.bselzer.library.kotlin.extension.settings

import com.russhwolf.settings.Settings
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

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
inline fun <reified E : Enum<E>> Settings.getEnumOrNull(key: String): E? {
    try {
        val value = getStringOrNull(key) ?: return null
        return Json.decodeFromString(value)
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
inline fun <reified E : Enum<E>> Settings.putEnum(key: String, value: E) = putString(key, Json.encodeToString(value))

