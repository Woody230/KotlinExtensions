package com.bselzer.library.kotlin.extension.settings.setting

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings

/**
 * The [Settings] wrapper for a [Float].
 */
@OptIn(ExperimentalSettingsApi::class)
class FloatSetting(settings: ObservableSettings, key: String, defaultValue: Float = 0f) : SettingWrapper<Float>(settings, key, defaultValue) {
    override fun get(): Float = settings.getFloat(key, defaultValue)
    override fun getOrNull(): Float? = settings.getFloatOrNull(key)
    override fun put(value: Float) = settings.putFloat(key, value)
}