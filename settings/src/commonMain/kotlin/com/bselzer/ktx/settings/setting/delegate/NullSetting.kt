package com.bselzer.ktx.settings.setting.delegate

import com.bselzer.ktx.settings.setting.Setting
import com.bselzer.ktx.settings.setting.encapsulation.NullGetter
import com.bselzer.ktx.settings.setting.encapsulation.NullObserver
import kotlinx.coroutines.flow.Flow

/**
 * A wrapper for delegating a nullable value.
 */
class NullSetting<T>(wrapper: Setting<T>) : DelegateSetting<T>(wrapper), NullGetter<T>, NullObserver<T> {
    override suspend fun getOrNull(): T? = wrapper.getOrNull()
    override fun observeOrNull(): Flow<T?> = wrapper.observeOrNull()
}