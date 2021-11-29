package com.bselzer.library.kotlin.extension.settings.setting

import kotlin.reflect.KProperty

/**
 * A wrapper for delegating a nullable value.
 */
class NullSetting<T>(private val wrapper: SettingWrapper<T>) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T? = wrapper.getOrNull()
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) = if (value == null) wrapper.remove() else wrapper.put(value)
}