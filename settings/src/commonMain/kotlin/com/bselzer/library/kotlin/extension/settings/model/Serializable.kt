package com.bselzer.library.kotlin.extension.settings.model

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.coroutines.SuspendSettings
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

/**
 * Retrieves the value as a [String] at the [key].
 *
 * @param key the location
 * @param defaultValue the default value
 * @param T the type of value
 * @return the value stored at the [key], or the [defaultValue] if it does not exist
 */
@OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
suspend inline fun <reified T> SuspendSettings.getSerializable(key: String, defaultValue: T): T = getSerializableOrNull<T>(key) ?: defaultValue

/**
 * Retrieves the value as a [String] at the [key].
 *
 * @param key the location
 * @param T the type of value
 * @return the value stored at the [key], or null if it does not exist
 */
@OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
suspend inline fun <reified T> SuspendSettings.getSerializableOrNull(key: String): T? = getSerializableOrNull<T>(serializer(), key)

/**
 * Retrieves the value as a [String] at the [key].
 *
 * @param deserializer the deserializer
 * @param key the location
 * @param defaultValue the default value
 * @param T the type of value
 * @return the value stored at the [key], or the [defaultValue] if it does not exist
 */
@OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
suspend fun <T> SuspendSettings.getSerializable(deserializer: DeserializationStrategy<T>, key: String, defaultValue: T): T =
    getSerializableOrNull(deserializer, key) ?: defaultValue

/**
 * Retrieves the value as a [String] at the [key].
 *
 * @param deserializer the deserializer
 * @param key the location
 * @param T the type of value
 * @return the value stored at the [key], or null if it does not exist
 */
@OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
suspend fun <T> SuspendSettings.getSerializableOrNull(deserializer: DeserializationStrategy<T>, key: String): T? {
    try {
        val value = getStringOrNull(key) ?: return null
        return Json.decodeFromString(deserializer, value)
    } catch (exception: Exception) {
        return null
    }
}

/**
 * Stores the [value] as a [String] at the [key].
 *
 * @param key the location
 * @param T the type of value
 */
@OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
suspend inline fun <reified T> SuspendSettings.putSerializable(key: String, value: T) = putSerializable(serializer(), key, value)

/**
 * Stores the [value] as a [String] at the [key].
 *
 * @param serializer the serializer
 * @param key the location
 * @param T the type of value
 */
@OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
suspend fun <T> SuspendSettings.putSerializable(serializer: SerializationStrategy<T>, key: String, value: T) = putString(key, Json.encodeToString(serializer, value))