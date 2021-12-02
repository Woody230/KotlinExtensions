package com.bselzer.library.kotlin.extension.settings.model

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.coroutines.SuspendSettings
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

/**
 * Retrieves the [Duration] as a [String] at the [key].
 *
 * @param key the location
 * @param defaultValue the default value
 * @return the duration value stored at the [key], or the [defaultValue] if it does not exist
 */
@OptIn(ExperimentalTime::class, ExperimentalSettingsApi::class)
suspend fun SuspendSettings.getDuration(key: String, defaultValue: Duration = Duration.ZERO): Duration = getDurationOrNull(key) ?: defaultValue

/**
 * Retrieves the [Duration] as a [String] at the [key].
 *
 * @param key the location
 * @return the duration value stored at the [key], or null if it does not exist
 */
@OptIn(ExperimentalTime::class, ExperimentalSettingsApi::class)
suspend fun SuspendSettings.getDurationOrNull(key: String): Duration? {
    val value = getStringOrNull(key) ?: return null
    return Duration.parseIsoStringOrNull(value)
}

/**
 * Stores the [Duration] as a [String] at the [key].
 *
 * @param key the location
 * @param value the value
 */
@OptIn(ExperimentalTime::class, ExperimentalSettingsApi::class)
suspend fun SuspendSettings.putDuration(key: String, value: Duration) = putString(key, value.toIsoString())
