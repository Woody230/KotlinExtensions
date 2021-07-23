package com.bselzer.library.kotlin.extension.livedata.android.immutable.nullable

/**
 * Nullable live data for strings.
 * @param defaultValue the initial value to store. It is also the value to set upon resetting the instance.
 */
open class NullStringLiveData(defaultValue: String? = null) : NullImmutableLiveData<String>(defaultValue)