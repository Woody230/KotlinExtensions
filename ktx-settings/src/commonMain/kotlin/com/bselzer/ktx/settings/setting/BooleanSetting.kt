package com.bselzer.ktx.settings.setting

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.SuspendSettings

/**
 * The [Settings] wrapper for a [Boolean].
 */
@OptIn(ExperimentalSettingsApi::class)
class BooleanSetting(settings: SuspendSettings, key: String, defaultValue: Boolean = false) : SettingWrapper<Boolean>(settings, key, defaultValue) {
    override suspend fun get(): Boolean = settings.getBoolean(key, defaultValue)
    override suspend fun getOrNull(): Boolean? = settings.getBooleanOrNull(key)
    override suspend fun put(value: Boolean) = settings.putBoolean(key, value)
}