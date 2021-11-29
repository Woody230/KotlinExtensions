package com.bselzer.library.kotlin.extension.settings.setting

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings

/**
 * The [Settings] wrapper for an [Int].
 */
@OptIn(ExperimentalSettingsApi::class)
class IntSetting(settings: ObservableSettings, key: String, defaultValue: Int = 0) : SettingWrapper<Int>(settings, key, defaultValue) {
    override fun get(): Int = settings.getInt(key, defaultValue)
    override fun getOrNull(): Int? = settings.getIntOrNull(key)
    override fun put(value: Int) = settings.putInt(key, value)
}