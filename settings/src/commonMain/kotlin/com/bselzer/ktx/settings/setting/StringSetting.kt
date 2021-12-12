package com.bselzer.ktx.settings.setting

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.SuspendSettings

/**
 * The [Settings] wrapper for a [String].
 */
@OptIn(ExperimentalSettingsApi::class)
class StringSetting(settings: SuspendSettings, key: String, defaultValue: String = "") : SettingWrapper<String>(settings, key, defaultValue) {
    override suspend fun get(): String = settings.getString(key, defaultValue)
    override suspend fun getOrNull(): String? = settings.getStringOrNull(key)
    override suspend fun put(value: String) = settings.putString(key, value)
}