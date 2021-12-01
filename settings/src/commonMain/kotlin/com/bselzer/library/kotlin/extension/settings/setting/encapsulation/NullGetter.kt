package com.bselzer.library.kotlin.extension.settings.setting.encapsulation

interface NullGetter<T> {
    fun getOrNull(): T?
}