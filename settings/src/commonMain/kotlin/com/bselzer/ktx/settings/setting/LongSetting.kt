package com.bselzer.ktx.settings.setting

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.SuspendSettings

/**
 * The [Settings] wrapper for a [Long].
 */
@OptIn(ExperimentalSettingsApi::class)
class LongSetting(settings: SuspendSettings, key: String, defaultValue: Long = 0) : SettingWrapper<Long>(settings, key, defaultValue) {
    override suspend fun get(): Long = settings.getLong(key, defaultValue)
    override suspend fun getOrNull(): Long? = settings.getLongOrNull(key)
    override suspend fun put(value: Long) = settings.putLong(key, value)
}