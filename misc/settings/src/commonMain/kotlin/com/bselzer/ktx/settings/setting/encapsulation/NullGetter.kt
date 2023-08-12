package com.bselzer.ktx.settings.setting.encapsulation

interface NullGetter<T> {
    suspend fun getOrNull(): T?
}