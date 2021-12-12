package com.bselzer.ktx.settings.setting

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.SuspendSettings

/**
 * The [Settings] wrapper for a [Double].
 */
@OptIn(ExperimentalSettingsApi::class)
class DoubleSetting(settings: SuspendSettings, key: String, defaultValue: Double = 0.0) : SettingWrapper<Double>(settings, key, defaultValue) {
    override suspend fun get(): Double = settings.getDouble(key, defaultValue)
    override suspend fun getOrNull(): Double? = settings.getDoubleOrNull(key)
    override suspend fun put(value: Double) = settings.putDouble(key, value)
}