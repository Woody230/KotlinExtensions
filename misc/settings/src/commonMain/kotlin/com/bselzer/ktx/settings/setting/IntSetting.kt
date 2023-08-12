package com.bselzer.ktx.settings.setting

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.SuspendSettings

/**
 * The [Settings] wrapper for an [Int].
 */
@OptIn(ExperimentalSettingsApi::class)
class IntSetting(settings: SuspendSettings, key: String, defaultValue: Int = 0) : SettingWrapper<Int>(settings, key, defaultValue) {
    override suspend fun get(): Int = settings.getInt(key, defaultValue)
    override suspend fun getOrNull(): Int? = settings.getIntOrNull(key)
    override suspend fun put(value: Int) = settings.putInt(key, value)
}