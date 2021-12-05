package com.bselzer.library.kotlin.extension.settings.setting.encapsulation

import kotlinx.coroutines.flow.Flow

interface SafeObserver<T> {
    fun observe(): Flow<T>
}