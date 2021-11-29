package com.bselzer.library.kotlin.extension.settings.setting

import com.russhwolf.settings.Settings

/**
 * The [Settings] wrapper for a [Long].
 */
class LongSetting(settings: Settings, key: String, defaultValue: Long = 0) : SettingWrapper<Long>(settings, key, defaultValue) {
    override fun get(): Long = settings.getLong(key, defaultValue)
    override fun getOrNull(): Long? = settings.getLongOrNull(key)
    override fun put(value: Long) = settings.putLong(key, value)
}