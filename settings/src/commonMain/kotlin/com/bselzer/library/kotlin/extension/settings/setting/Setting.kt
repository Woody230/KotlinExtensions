package com.bselzer.library.kotlin.extension.settings.setting

import kotlinx.coroutines.flow.Flow

interface Setting<T> {
    fun get(): T
    fun getOrNull(): T?
    fun put(value: T)
    fun remove()
    fun exists(): Boolean
    suspend fun observe(): Flow<T>
    suspend fun observeOrNull(): Flow<T?>
}