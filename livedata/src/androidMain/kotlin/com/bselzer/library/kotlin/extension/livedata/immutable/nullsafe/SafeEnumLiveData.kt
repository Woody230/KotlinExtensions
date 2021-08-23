package com.bselzer.library.kotlin.extension.livedata.immutable.nullsafe

/**
 * Live data for enums.
 * @param E the type of enum to store
 * @param defaultValue the initial value to store. It is also the value to set upon resetting the instance.
 */
open class SafeEnumLiveData<E : Enum<E>>(defaultValue: E) : SafeImmutableLiveData<E>(defaultValue)