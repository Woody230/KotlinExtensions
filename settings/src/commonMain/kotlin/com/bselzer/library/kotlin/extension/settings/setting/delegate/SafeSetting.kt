package com.bselzer.library.kotlin.extension.settings.setting.delegate

import com.bselzer.library.kotlin.extension.settings.setting.Setting

/**
 * A wrapper for delegating a non-null value.
 */
class SafeSetting<T>(wrapper: Setting<T>) : DelegateSetting<T>(wrapper) {
    override suspend fun getOrNull(): T? = wrapper.get()
}