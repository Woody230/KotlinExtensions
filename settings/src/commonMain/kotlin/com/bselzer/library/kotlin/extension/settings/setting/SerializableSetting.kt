package com.bselzer.library.kotlin.extension.settings.setting

import com.bselzer.library.kotlin.extension.settings.model.getSerializable
import com.bselzer.library.kotlin.extension.settings.model.getSerializableOrNull
import com.bselzer.library.kotlin.extension.settings.model.putSerializable
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import kotlinx.serialization.KSerializer

/**
 * The [Settings] wrapper for a serializable object.
 */
@OptIn(ExperimentalSettingsApi::class)
class SerializableSetting<T>(settings: ObservableSettings, key: String, defaultValue: T, private val serializer: KSerializer<T>) :
    SettingWrapper<T>(settings, key, defaultValue) {
    override fun get(): T = settings.getSerializable(serializer, key, defaultValue)
    override fun getOrNull(): T? = settings.getSerializableOrNull(serializer, key)
    override fun put(value: T) = settings.putSerializable(serializer, key, value)
}