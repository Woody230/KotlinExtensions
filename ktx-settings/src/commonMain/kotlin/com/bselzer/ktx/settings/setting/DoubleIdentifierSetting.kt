package com.bselzer.ktx.settings.setting

import com.bselzer.ktx.value.identifier.Identifier
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.SuspendSettings

/**
 * The [Settings] wrapper for a [Double] using an [Identifier].
 */
@OptIn(ExperimentalSettingsApi::class)
class DoubleIdentifierSetting<T : Identifier<Double>>(
    settings: SuspendSettings,
    key: String,
    private val create: (Double) -> T,
    defaultValue: T = create(0.0)
) : SettingWrapper<T>(settings, key, defaultValue) {
    override suspend fun get(): T = create(settings.getDouble(key, defaultValue.value))
    override suspend fun getOrNull(): T? = settings.getDoubleOrNull(key)?.let(create)
    override suspend fun put(value: T) = settings.putDouble(key, value.value)
}