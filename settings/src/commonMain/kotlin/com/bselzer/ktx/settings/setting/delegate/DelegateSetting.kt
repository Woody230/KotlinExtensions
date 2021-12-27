package com.bselzer.ktx.settings.setting.delegate

import com.bselzer.ktx.settings.setting.Setting
import kotlinx.coroutines.flow.Flow

/**
 * The base class for delegating getting/setting a [Setting].
 */
abstract class DelegateSetting<T>(protected val wrapper: Setting<T>) : DelegableSetting<T> {
    final override val key = wrapper.key
    final override val defaultValue = wrapper.defaultValue
    final override suspend fun get(): T = wrapper.get()
    final override suspend fun set(value: T) = wrapper.set(value)
    final override suspend fun exists(): Boolean = wrapper.exists()
    final override suspend fun initialize(value: T): Boolean = wrapper.initialize(value)
    final override suspend fun remove() = wrapper.remove()
    final override fun observe(): Flow<T> = wrapper.observe()
}