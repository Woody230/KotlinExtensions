package com.bselzer.library.kotlin.extension.settings.setting

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings

/**
 * The [Settings] wrapper for a [Boolean].
 */
@OptIn(ExperimentalSettingsApi::class)
class BooleanSetting(settings: ObservableSettings, key: String, defaultValue: Boolean = false) : SettingWrapper<Boolean>(settings, key, defaultValue) {
    override fun get(): Boolean = settings.getBoolean(key, defaultValue)
    override fun getOrNull(): Boolean? = settings.getBooleanOrNull(key)
    override fun put(value: Boolean) = settings.putBoolean(key, value)
}