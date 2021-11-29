package com.bselzer.library.kotlin.extension.settings.setting

import com.russhwolf.settings.Settings

/**
 * The [Settings] wrapper for a [Double].
 */
class DoubleSetting(settings: Settings, key: String, defaultValue: Double = 0.0) : SettingWrapper<Double>(settings, key, defaultValue) {
    override fun get(): Double = settings.getDouble(key, defaultValue)
    override fun getOrNull(): Double? = settings.getDoubleOrNull(key)
    override fun put(value: Double) = settings.putDouble(key, value)
}