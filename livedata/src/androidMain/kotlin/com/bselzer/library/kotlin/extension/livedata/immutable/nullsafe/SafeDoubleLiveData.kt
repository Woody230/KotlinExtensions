package com.bselzer.library.kotlin.extension.livedata.immutable.nullsafe

/**
 * Null-safe live data for doubles.
 * @param defaultValue the initial value to store. It is also the value to set upon resetting the instance.
 */
open class SafeDoubleLiveData(defaultValue: Double = 0.0) : SafeImmutableLiveData<Double>(defaultValue)