package com.bselzer.library.kotlin.extension.settings.setting.encapsulation

import kotlinx.coroutines.flow.Flow

interface NullObserver<T> {
    fun observeOrNull(): Flow<T?>
}