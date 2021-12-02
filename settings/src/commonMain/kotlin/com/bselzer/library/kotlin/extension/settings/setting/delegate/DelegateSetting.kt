package com.bselzer.library.kotlin.extension.settings.setting.delegate

import com.bselzer.library.kotlin.extension.settings.setting.Setting

/**
 * The base class for delegating getting/setting a [Setting]
 */
abstract class DelegateSetting<T>(protected val wrapper: Setting<T>) : Setting<T> {
    override val key = wrapper.key
    override val defaultValue = wrapper.defaultValue
    override suspend fun get(): T = wrapper.get()
    override suspend fun put(value: T) = wrapper.put(value)
    override suspend fun exists(): Boolean = wrapper.exists()
    override suspend fun initialize(value: T): Boolean = wrapper.initialize(value)
    override suspend fun remove() = wrapper.remove()
}