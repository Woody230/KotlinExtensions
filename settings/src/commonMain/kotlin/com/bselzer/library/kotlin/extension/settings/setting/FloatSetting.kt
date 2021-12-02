package com.bselzer.library.kotlin.extension.settings.setting

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.SuspendSettings

/**
 * The [Settings] wrapper for a [Float].
 */
@OptIn(ExperimentalSettingsApi::class)
class FloatSetting(settings: SuspendSettings, key: String, defaultValue: Float = 0f) : SettingWrapper<Float>(settings, key, defaultValue) {
    override suspend fun get(): Float = settings.getFloat(key, defaultValue)
    override suspend fun getOrNull(): Float? = settings.getFloatOrNull(key)
    override suspend fun put(value: Float) = settings.putFloat(key, value)
}