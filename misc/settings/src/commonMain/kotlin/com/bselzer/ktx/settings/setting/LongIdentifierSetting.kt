package com.bselzer.ktx.settings.setting

import com.bselzer.ktx.value.identifier.Identifier
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.SuspendSettings

/**
 * The [Settings] wrapper for a [Long] using an [Identifier].
 */
@OptIn(ExperimentalSettingsApi::class)
class LongIdentifierSetting<T : Identifier<Long>>(
    settings: SuspendSettings,
    key: String,
    private val create: (Long) -> T,
    defaultValue: T = create(0)
) : SettingWrapper<T>(settings, key, defaultValue) {
    override suspend fun get(): T = create(settings.getLong(key, defaultValue.value))
    override suspend fun getOrNull(): T? = settings.getLongOrNull(key)?.let(create)
    override suspend fun put(value: T) = settings.putLong(key, value.value)
}