package com.bselzer.ktx.livedata.base.nullsafe

import androidx.lifecycle.LiveData
import com.bselzer.ktx.livedata.base.implement.Resettable

/**
 * A null-safe version of live data.
 * @param Value the type of the value to store
 * @param initialValue the initial value to store to replace the default null
 * @param defaultValue the default value to set upon reset
 */
open class SafeLiveData<Value>(initialValue: Value, protected val defaultValue: Value) : LiveData<Value>(initialValue),
    Resettable
{
    /**
     * Set the value to the default value.
     */
    override fun reset()
    {
        value = defaultValue
    }

    override fun getValue(): Value
    {
        val value = super.getValue()
        requireNotNull(value)
        return value
    }

    // Override with a non-nullable parameter.
    @Suppress
    override fun setValue(value: Value)
    {
        super.setValue(value)
    }

    // Override with a non-nullable parameter.
    @Suppress
    override fun postValue(value: Value)
    {
        super.postValue(value)
    }
}