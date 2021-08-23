package com.bselzer.library.kotlin.extension.livedata.immutable.nullable

/**
 * Nullable live data for enums.
 * @param E the type of enum to store
 * @param defaultValue the initial value to store. It is also the value to set upon resetting the instance.
 */
open class NullEnumLiveData<E : Enum<E>>(defaultValue: E? = null) : NullImmutableLiveData<E>(defaultValue)