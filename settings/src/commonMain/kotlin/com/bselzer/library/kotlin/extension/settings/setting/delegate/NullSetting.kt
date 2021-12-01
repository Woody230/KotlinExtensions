package com.bselzer.library.kotlin.extension.settings.setting.delegate

import com.bselzer.library.kotlin.extension.settings.setting.Setting
import kotlin.reflect.KProperty

/**
 * A wrapper for delegating a nullable value.
 */
class NullSetting<T>(wrapper: Setting<T>) : DelegateSetting<T>(wrapper) {
    override fun getValue(thisRef: Any?, property: KProperty<*>): T? = wrapper.getOrNull()
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) = if (value == null) wrapper.remove() else wrapper.put(value)
}