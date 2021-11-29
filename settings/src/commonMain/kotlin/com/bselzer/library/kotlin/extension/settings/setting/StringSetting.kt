package com.bselzer.library.kotlin.extension.settings.setting

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings

/**
 * The [Settings] wrapper for a [String].
 */
@OptIn(ExperimentalSettingsApi::class)
class StringSetting(settings: ObservableSettings, key: String, defaultValue: String = "") : SettingWrapper<String>(settings, key, defaultValue) {
    override fun get(): String = settings.getString(key, defaultValue)
    override fun getOrNull(): String? = settings.getStringOrNull(key)
    override fun put(value: String) = settings.putString(key, value)
}