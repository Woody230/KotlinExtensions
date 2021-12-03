package com.bselzer.library.kotlin.extension.settings.setting.encapsulation

interface Setter<T> {
    suspend fun set(value: T)
}