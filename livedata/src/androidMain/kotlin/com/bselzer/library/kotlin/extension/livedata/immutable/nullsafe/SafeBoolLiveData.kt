package com.bselzer.library.kotlin.extension.livedata.immutable.nullsafe

/**
 * Null-safe live data for booleans.
 * @param defaultValue the initial value to store. It is also the value to set upon resetting the instance.
 */
open class SafeBoolLiveData(defaultValue: Boolean = false) : SafeImmutableLiveData<Boolean>(defaultValue)