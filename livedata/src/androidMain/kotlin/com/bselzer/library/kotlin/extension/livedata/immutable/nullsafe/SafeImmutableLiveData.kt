package com.bselzer.library.kotlin.extension.livedata.immutable.nullsafe

import com.bselzer.library.kotlin.extension.livedata.base.nullsafe.SafeMutableLiveData

/**
 * Null-safe live data for primitives and immutable objects.
 * @param Value the type of the value to store
 * @param defaultValue the initial value to store. It is also the value to set upon resetting the instance.
 */
open class SafeImmutableLiveData<Value>(defaultValue: Value) : SafeMutableLiveData<Value>(defaultValue, defaultValue)