package com.bselzer.ktx.settings.setting.encapsulation

interface Setter<T> {
    suspend fun set(value: T)
}