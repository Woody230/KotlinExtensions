package com.bselzer.library.kotlin.extension.settings.setting

import com.bselzer.library.kotlin.extension.settings.setting.delegate.NullSetting
import com.bselzer.library.kotlin.extension.settings.setting.delegate.SafeSetting
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.SuspendSettings
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * A base wrapper for a [Settings] instance.
 */
@OptIn(ExperimentalCoroutinesApi::class, ExperimentalSettingsApi::class)
abstract class SettingWrapper<T>(protected val settings: SuspendSettings, override val key: String, override val defaultValue: T) : Setting<T> {
    /**
     * Retrieves the [T] value at the [key].
     *
     * @return the value stored at the [key], or the [defaultValue] if it does not exist
     */
    abstract override suspend fun get(): T

    /**
     * Retrieves the [T] value at the [key].
     *
     * @return the value stored at the [key] or null if it does not exist
     */
    abstract override suspend fun getOrNull(): T?

    /**
     * Stores the [value] at the [key].
     *
     * @param value the value
     */
    abstract override suspend fun put(value: T)

    /**
     * Removes the value at the [key] if it exists.
     */
    override suspend fun remove() = settings.remove(key)

    /**
     * @return whether a value exists for this setting
     */
    override suspend fun exists(): Boolean = settings.hasKey(key)

    /**
     * Stores the [value] at the [key] if a value does not exist already.
     *
     * @return true if the value needed to be initialized
     */
    override suspend fun initialize(value: T): Boolean {
        val initialize = !exists()
        if (initialize) put(value)
        return initialize
    }

    /**
     * @return this setting as a [SafeSetting]
     */
    fun safe(): SafeSetting<T> = SafeSetting(this)

    /**
     * @return this setting as a [NullSetting]
     */
    fun nullable(): NullSetting<T> = NullSetting(this)
}