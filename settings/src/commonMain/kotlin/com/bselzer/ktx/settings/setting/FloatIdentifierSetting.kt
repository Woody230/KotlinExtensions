package com.bselzer.ktx.settings.setting

import com.bselzer.ktx.value.identifier.Identifier
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.SuspendSettings

/**
 * The [Settings] wrapper for a [Float] using an [Identifier].
 */
@OptIn(ExperimentalSettingsApi::class)
class FloatIdentifierSetting<T : Identifier<Float>>(
    settings: SuspendSettings,
    key: String,
    private val create: (Float) -> T,
    defaultValue: T = create(0f)
) : SettingWrapper<T>(settings, key, defaultValue) {
    override suspend fun get(): T = create(settings.getFloat(key, defaultValue.value))
    override suspend fun getOrNull(): T? = settings.getFloatOrNull(key)?.let(create)
    override suspend fun put(value: T) = settings.putFloat(key, value.value)
}