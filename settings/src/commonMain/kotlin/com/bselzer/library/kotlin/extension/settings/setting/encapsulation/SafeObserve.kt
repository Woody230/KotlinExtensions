package com.bselzer.library.kotlin.extension.settings.setting.encapsulation

import kotlinx.coroutines.flow.Flow

interface SafeObserve<T> {
    fun observe(): Flow<T>
}