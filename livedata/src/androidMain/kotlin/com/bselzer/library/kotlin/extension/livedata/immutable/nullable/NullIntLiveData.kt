package com.bselzer.library.kotlin.extension.livedata.immutable.nullable

/**
 * Nullable live data for integers.
 * @param defaultValue the initial value to store. It is also the value to set upon resetting the instance.
 */
open class NullIntLiveData(defaultValue: Int? = null) : NullImmutableLiveData<Int>(defaultValue)