package com.bselzer.library.kotlin.extension.livedata.immutable.nullsafe

/**
 * Null-safe live data for integers.
 * @param defaultValue the initial value to store. It is also the value to set upon resetting the instance.
 */
open class SafeIntLiveData(defaultValue: Int = 0) : SafeImmutableLiveData<Int>(defaultValue)