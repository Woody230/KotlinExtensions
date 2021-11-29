package com.bselzer.library.kotlin.extension.settings.setting

import com.russhwolf.settings.Settings

/**
 * The [Settings] wrapper for a [Float].
 */
class FloatSetting(settings: Settings, key: String, defaultValue: Float = 0f) : SettingWrapper<Float>(settings, key, defaultValue) {
    override fun get(): Float = settings.getFloat(key, defaultValue)
    override fun getOrNull(): Float? = settings.getFloatOrNull(key)
    override fun put(value: Float) = settings.putFloat(key, value)
}