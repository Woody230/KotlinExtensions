package com.bselzer.ktx.livedata.immutable.nullsafe

/**
 * Null-safe live data for strings.
 * @param defaultValue the initial value to store. It is also the value to set upon resetting the instance.
 */
open class SafeStringLiveData(defaultValue: String = "") : SafeImmutableLiveData<String>(defaultValue)