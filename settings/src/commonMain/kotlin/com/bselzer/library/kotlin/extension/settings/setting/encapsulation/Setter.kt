package com.bselzer.library.kotlin.extension.settings.setting.encapsulation

interface Setter<T> {
    suspend fun put(value: T)
}