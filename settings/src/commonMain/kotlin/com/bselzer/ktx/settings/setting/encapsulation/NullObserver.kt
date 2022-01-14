package com.bselzer.ktx.settings.setting.encapsulation

import kotlinx.coroutines.flow.Flow

interface NullObserver<T> {
    fun observeOrNull(): Flow<T?>
}