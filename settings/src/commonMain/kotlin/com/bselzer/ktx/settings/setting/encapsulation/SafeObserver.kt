package com.bselzer.ktx.settings.setting.encapsulation

import kotlinx.coroutines.flow.Flow

interface SafeObserver<T> {
    fun observe(): Flow<T>
}