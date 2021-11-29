package com.bselzer.library.kotlin.extension.settings.setting

import kotlin.reflect.KProperty

/**
 * A wrapper for delegating a non-null value.
 */
class SafeSetting<T>(private val wrapper: SettingWrapper<T>) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = wrapper.get()
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) = if (value == null) wrapper.remove() else wrapper.put(value)
}