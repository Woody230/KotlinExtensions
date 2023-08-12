package com.bselzer.ktx.settings.setting.encapsulation

interface SafeGetter<T> {
    suspend fun get(): T
}