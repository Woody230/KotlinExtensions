package com.bselzer.ktx.settings.setting.encapsulation

interface Initialize<T> {
    suspend fun initialize(value: T): Boolean
}