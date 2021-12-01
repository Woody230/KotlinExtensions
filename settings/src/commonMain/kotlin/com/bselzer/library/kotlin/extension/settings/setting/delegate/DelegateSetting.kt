package com.bselzer.library.kotlin.extension.settings.setting.delegate

import com.bselzer.library.kotlin.extension.settings.setting.Setting
import com.bselzer.library.kotlin.extension.settings.setting.encapsulation.*
import kotlinx.coroutines.flow.Flow
import kotlin.properties.ReadWriteProperty

/**
 * The base class for delegating getting/setting a [Setting]
 */
abstract class DelegateSetting<T>(protected val wrapper: Setting<T>) : ReadWriteProperty<Any?, T?>, Exists, Initialize<T>, Removable, SafeObserver<T>, NullObserver<T?> {
    val key = wrapper.key
    val defaultValue = wrapper.defaultValue
    override fun exists(): Boolean = wrapper.exists()
    override fun initialize(value: T): Boolean = wrapper.initialize(value)
    override fun remove() = wrapper.remove()
    override fun observe(): Flow<T> = wrapper.observe()
    override fun observeOrNull(): Flow<T?> = wrapper.observeOrNull()
}