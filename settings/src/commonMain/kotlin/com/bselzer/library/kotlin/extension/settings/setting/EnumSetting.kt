package com.bselzer.library.kotlin.extension.settings.setting

import com.bselzer.library.kotlin.extension.settings.model.getEnum
import com.bselzer.library.kotlin.extension.settings.model.getEnumOrNull
import com.bselzer.library.kotlin.extension.settings.model.putEnum
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import kotlinx.serialization.KSerializer

/**
 * The [Settings] wrapper for a [Enum].
 */
@OptIn(ExperimentalSettingsApi::class)
class EnumSetting<E : Enum<E>>(settings: ObservableSettings, key: String, defaultValue: E, private val serializer: KSerializer<E>) :
    SettingWrapper<E>(settings, key, defaultValue) {
    override fun get(): E = settings.getEnum(serializer, key, defaultValue)
    override fun getOrNull(): E? = settings.getEnumOrNull(serializer, key)
    override fun put(value: E) = settings.putEnum(serializer, key, value)
}