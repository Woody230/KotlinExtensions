package com.bselzer.library.kotlin.extension.settings.setting.delegate

import com.bselzer.library.kotlin.extension.settings.setting.Setting
import kotlinx.coroutines.flow.Flow
import kotlin.properties.ReadWriteProperty

/**
 * The base class for delegating getting/setting a [Setting]
 */
abstract class DelegateSetting<T>(protected val wrapper: Setting<T>) : ReadWriteProperty<Any?, T?>, Setting<T> {
    override val key = wrapper.key
    override val defaultValue = wrapper.defaultValue
    override fun get(): T = wrapper.get()
    override fun getOrNull(): T? = wrapper.getOrNull()
    override fun put(value: T) = wrapper.put(value)
    override fun exists(): Boolean = wrapper.exists()
    override fun initialize(value: T): Boolean = wrapper.initialize(value)
    override fun remove() = wrapper.remove()
    override fun observe(): Flow<T> = wrapper.observe()
    override fun observeOrNull(): Flow<T?> = wrapper.observeOrNull()
}