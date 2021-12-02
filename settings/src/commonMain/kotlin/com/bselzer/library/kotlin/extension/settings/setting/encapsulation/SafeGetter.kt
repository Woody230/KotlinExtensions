package com.bselzer.library.kotlin.extension.settings.setting.encapsulation

interface SafeGetter<T> {
    suspend fun get(): T
}