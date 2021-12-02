package com.bselzer.library.kotlin.extension.settings.setting.encapsulation

interface Initialize<T> {
    suspend fun initialize(value: T): Boolean
}