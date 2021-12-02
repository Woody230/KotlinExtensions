package com.bselzer.library.kotlin.extension.settings.setting

import com.bselzer.library.kotlin.extension.settings.model.getSerializable
import com.bselzer.library.kotlin.extension.settings.model.getSerializableOrNull
import com.bselzer.library.kotlin.extension.settings.model.putSerializable
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.SuspendSettings
import kotlinx.serialization.KSerializer

/**
 * The [Settings] wrapper for a serializable object.
 */
@OptIn(ExperimentalSettingsApi::class)
class SerializableSetting<T>(settings: SuspendSettings, key: String, defaultValue: T, private val serializer: KSerializer<T>) :
    SettingWrapper<T>(settings, key, defaultValue) {
    override suspend fun get(): T = settings.getSerializable(serializer, key, defaultValue)
    override suspend fun getOrNull(): T? = settings.getSerializableOrNull(serializer, key)
    override suspend fun put(value: T) = settings.putSerializable(serializer, key, value)
}