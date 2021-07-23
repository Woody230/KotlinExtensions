package com.bselzer.library.kotlin.extension.livedata.android.immutable.nullsafe

/**
 * Null-safe live data for longs.
 * @param defaultValue the initial value to store. It is also the value to set upon resetting the instance.
 */
open class SafeLongLiveData(defaultValue: Long = 0L) : SafeImmutableLiveData<Long>(defaultValue)