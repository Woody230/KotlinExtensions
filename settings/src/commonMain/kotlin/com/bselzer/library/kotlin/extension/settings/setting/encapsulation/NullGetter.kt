package com.bselzer.library.kotlin.extension.settings.setting.encapsulation

interface NullGetter<T> {
    suspend fun getOrNull(): T?
}