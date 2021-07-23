package com.bselzer.library.kotlin.extension.livedata.android.base.nullable

import androidx.lifecycle.MutableLiveData
import com.bselzer.library.kotlin.extension.livedata.android.base.implement.Resettable

/**
 * The base class for nullable mutable live data.
 * @param Value the type of the value to store
 * @param initialValue the initial value to store to replace the default null
 * @param defaultValue the default value to set upon reset
 */
open class NullMutableLiveData<Value>(initialValue: Value? = null, protected val defaultValue: Value? = null) :
    MutableLiveData<Value>(initialValue),
    Resettable
{
    /**
     * Set the value to the default value.
     */
    override fun reset()
    {
        value = defaultValue
    }
}