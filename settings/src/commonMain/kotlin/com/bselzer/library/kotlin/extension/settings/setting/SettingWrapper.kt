package com.bselzer.library.kotlin.extension.settings.setting

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * A base wrapper for a [Settings] instance.
 */
@OptIn(ExperimentalCoroutinesApi::class, ExperimentalSettingsApi::class)
abstract class SettingWrapper<T>(protected val settings: ObservableSettings, val key: String, val defaultValue: T) : Setting<T> {
    /**
     * Retrieves the [T] value at the [key].
     *
     * @return the value stored at the [key], or the [defaultValue] if it does not exist
     */
    abstract override fun get(): T

    /**
     * Retrieves the [T] value at the [key].
     *
     * @return the value stored at the [key] or null if it does not exist
     */
    abstract override fun getOrNull(): T?

    /**
     * Stores the [value] at the [key].
     *
     * @param value the value
     */
    abstract override fun put(value: T)

    /**
     * Removes the value at the [key] if it exists.
     */
    override fun remove() = settings.remove(key)

    /**
     * @return whether a value exists for this setting
     */
    override fun exists(): Boolean = settings.hasKey(key)

    /**
     * Streams the value stored at the [key], or the [defaultValue] if it does not exist.
     *
     * @return the stream
     */
    override suspend fun observe(): Flow<T> = callbackFlow {
        // Mimicking what the coroutines setting module does. https://github.com/russhwolf/multiplatform-settings/blob/master/multiplatform-settings-coroutines-internal/src/commonMain/kotlin/com/russhwolf/settings/coroutines/CoroutineExtensions.kt
        send(get())
        val listener = settings.addListener(key) {
            trySend(get())
        }
        awaitClose {
            listener.deactivate()
        }
    }

    /**
     * Streams the value stored at the [key], or null if it does not exist.
     *
     * @return the stream
     */
    override suspend fun observeOrNull(): Flow<T?> = callbackFlow {
        send(getOrNull())
        val listener = settings.addListener(key) {
            trySend(getOrNull())
        }
        awaitClose {
            listener.deactivate()
        }
    }
}