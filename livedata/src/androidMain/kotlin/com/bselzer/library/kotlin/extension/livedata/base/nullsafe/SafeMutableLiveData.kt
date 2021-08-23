package com.bselzer.library.kotlin.extension.livedata.base.nullsafe

/**
 * A null-safe version of live data that exposes the ability to set values.
 * @param Value the type of the value to store
 * @param initialValue the initial value to store to replace the default null
 * @param defaultValue the default value to set upon reset
 */
open class SafeMutableLiveData<Value>(initialValue: Value, defaultValue: Value) :
    SafeLiveData<Value>(initialValue, defaultValue)
{
    public override fun setValue(value: Value)
    {
        super.setValue(value)
    }

    public override fun postValue(value: Value)
    {
        super.postValue(value)
    }
}
