package com.bselzer.library.kotlin.extension.settings.setting

import com.russhwolf.settings.Settings

/**
 * An individual setting within a [Settings] instance.
 */
abstract class Setting<T>(val key: String, val defaultValue: T) {
    /**
     * Retrieves the [T] value at the [key].
     *
     * @return the value stored at the [key], or the [defaultValue] if it does not exist
     */
    abstract fun get(): T

    /**
     * Retrieves the [T] value at the [key].
     *
     * @return the value stored at the [key] or null if it does not exist
     */
    abstract fun getOrNull(): T?

    /**
     * Stores the [value] at the [key].
     *
     * @param value the value
     */
    abstract fun put(value: T)

    /**
     * Removes the value at the [key] if it exists.
     */
    abstract fun remove()

    /**
     * @return whether a value exists for this setting
     */
    abstract fun exists(): Boolean
}