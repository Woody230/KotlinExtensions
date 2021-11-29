package com.bselzer.library.kotlin.extension.settings.setting

import com.russhwolf.settings.Settings

/**
 * A base wrapper for a [Settings] instance.
 */
abstract class SettingWrapper<T>(protected val settings: Settings, key: String, defaultValue: T) : Setting<T>(key, defaultValue) {
    override fun remove() = settings.remove(key)
    override fun exists(): Boolean = settings.hasKey(key)
}