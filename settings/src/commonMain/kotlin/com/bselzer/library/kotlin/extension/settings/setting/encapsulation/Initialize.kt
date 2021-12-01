package com.bselzer.library.kotlin.extension.settings.setting.encapsulation

interface Initialize<T> {
    fun initialize(value: T): Boolean
}