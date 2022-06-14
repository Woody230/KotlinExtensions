package com.bselzer.ktx.settings.compose.model

import androidx.compose.ui.graphics.Color
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.coroutines.SuspendSettings

/**
 * Retrieves the [Color] as a [Long] at the [key].
 *
 * @param key the location
 * @param defaultValue the default value
 * @return the color value stored at the [key], or the [defaultValue] if it does not exist
 */
@OptIn(ExperimentalSettingsApi::class)
suspend fun SuspendSettings.getColor(key: String, defaultValue: Color = Color.Black): Color = getColorOrNull(key) ?: defaultValue

/**
 * Retrieves the [Color] as a [Long] at the [key].
 *
 * @param key the location
 * @return the color value stored at the [key], or null if it does not exist
 */
@OptIn(ExperimentalSettingsApi::class)
suspend fun SuspendSettings.getColorOrNull(key: String): Color? {
    val value = getLongOrNull(key) ?: return null
    return Color(value)
}

/**
 * Stores the [Color] as a [Long] at the [key].
 *
 * @param key the location
 * @param value the value
 */
@OptIn(ExperimentalSettingsApi::class)
suspend fun SuspendSettings.putColor(key: String, value: Color) = putLong(key, value.value.toLong())