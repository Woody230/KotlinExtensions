package com.bselzer.ktx.settings.setting

import com.bselzer.ktx.value.identifier.Identifier
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.SuspendSettings

/**
 * The [Settings] wrapper for a [String] using an [Identifier].
 */
@OptIn(ExperimentalSettingsApi::class)
class StringIdentifierSetting<T : Identifier<String>>(
    settings: SuspendSettings,
    key: String,
    private val create: (String) -> T,
    defaultValue: T = create("")
) : SettingWrapper<T>(settings, key, defaultValue) {
    override suspend fun get(): T = create(settings.getString(key, defaultValue.value))
    override suspend fun getOrNull(): T? = settings.getStringOrNull(key)?.let(create)
    override suspend fun put(value: T) = settings.putString(key, value.value)
}