package com.bselzer.ktx.settings.setting

import com.bselzer.ktx.value.identifier.Identifier
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.SuspendSettings

/**
 * The [Settings] wrapper for an [Int] using an [Identifier].
 */
@OptIn(ExperimentalSettingsApi::class)
class IntIdentifierSetting<T : Identifier<Int>>(
    settings: SuspendSettings,
    key: String,
    private val create: (Int) -> T,
    defaultValue: T = create(0)
) : SettingWrapper<T>(settings, key, defaultValue) {
    override suspend fun get(): T = create(settings.getInt(key, defaultValue.value))
    override suspend fun getOrNull(): T? = settings.getIntOrNull(key)?.let(create)
    override suspend fun put(value: T) = settings.putInt(key, value.value)
}