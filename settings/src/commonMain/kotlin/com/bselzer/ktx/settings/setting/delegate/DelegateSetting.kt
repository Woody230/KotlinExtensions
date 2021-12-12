package com.bselzer.ktx.settings.setting.delegate

import com.bselzer.ktx.settings.setting.Setting
import com.bselzer.ktx.settings.setting.encapsulation.*
import kotlinx.coroutines.flow.Flow

/**
 * The base class for delegating getting/setting a [Setting]
 */
abstract class DelegateSetting<T>(protected val wrapper: Setting<T>) : Exists, Initialize<T>, Setter<T>, Removable, SafeGetter<T>, SafeObserver<T> {
    val key = wrapper.key
    val defaultValue = wrapper.defaultValue
    final override suspend fun get(): T = wrapper.get()
    final override suspend fun set(value: T) = wrapper.set(value)
    final override suspend fun exists(): Boolean = wrapper.exists()
    final override suspend fun initialize(value: T): Boolean = wrapper.initialize(value)
    final override suspend fun remove() = wrapper.remove()
    final override fun observe(): Flow<T> = wrapper.observe()
}