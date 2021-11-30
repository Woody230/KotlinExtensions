package com.bselzer.library.kotlin.extension.settings.setting.delegate

import com.bselzer.library.kotlin.extension.settings.setting.Setting
import com.bselzer.library.kotlin.extension.settings.setting.encapsulation.NullObserve
import com.bselzer.library.kotlin.extension.settings.setting.encapsulation.SafeObserve
import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KProperty

/**
 * A wrapper for delegating a non-null value.
 */
class SafeSetting<T>(wrapper: Setting<T>) : DelegateSetting<T>(wrapper), SafeObserve<T>, NullObserve<T?> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): T = wrapper.get()
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) = if (value == null) wrapper.remove() else wrapper.put(value)
    override fun observe(): Flow<T> = wrapper.observe()
    override fun observeOrNull(): Flow<T?> = wrapper.observe()
}