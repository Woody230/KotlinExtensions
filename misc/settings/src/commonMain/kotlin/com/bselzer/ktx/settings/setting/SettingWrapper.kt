package com.bselzer.ktx.settings.setting

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.SuspendSettings
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * A base wrapper for a [Settings] instance.
 */
@OptIn(ExperimentalSettingsApi::class)
abstract class SettingWrapper<T>(protected val settings: SuspendSettings, override val key: String, override val defaultValue: T) : Setting<T> {
    private val listeners: MutableList<SettingListener> = mutableListOf()

    private companion object {
        class SettingListener(val callback: suspend () -> Unit)
    }

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
    protected abstract suspend fun put(value: T)

    /**
     * Stores the [value] at the [key].
     *
     * @param value the value
     */
    final override suspend fun set(value: T) {
        put(value)

        // Avoid concurrent modification exception with toList
        listeners.toList().forEach { listener -> listener.callback() }
    }

    /**
     * Removes the value at the [key] if it exists.
     */
    final override suspend fun remove() {
        settings.remove(key)

        // Avoid concurrent modification exception with toList
        listeners.toList().forEach { listener -> listener.callback() }
    }

    /**
     * @return whether a value exists for this setting
     */
    final override suspend fun exists(): Boolean = settings.hasKey(key)

    /**
     * Stores the [value] at the [key] if a value does not exist already.
     *
     * @return true if the value needed to be initialized
     */
    final override suspend fun initialize(value: T): Boolean {
        val initialize = !exists()
        if (initialize) set(value)
        return initialize
    }

    /**
     * Streams the value stored at the [key], or the [defaultValue] if it does not exist.
     *
     * Only changes performed through this instance will be persisted.
     *
     * @return the stream
     */
    final override fun observe(): Flow<T> = flow { get() }

    /**
     * Streams the value stored at the [key], or null if it does not exist.
     *
     * Only changes performed through this instance will be persisted.
     *
     * @return the stream
     */
    final override fun observeOrNull(): Flow<T?> = flow { getOrNull() }

    /**
     * @return a flow listening for changes to the setting within the context of this class
     */
    private fun <T> flow(getter: suspend () -> T): Flow<T> = callbackFlow {
        send(getter())
        val listener = SettingListener { trySend(getter()) }
        listeners.add(listener)
        awaitClose { listeners.remove(listener) }
    }
}