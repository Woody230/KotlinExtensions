package com.bselzer.library.kotlin.extension.livedata.collection

import com.bselzer.library.kotlin.extension.livedata.base.nullsafe.SafeMutableLiveData

/**
 * Null-safe live data for collections.
 * @param Element the type of value stored in the collection
 * @param Enumerable the type of collection
 * @param initialValue the initial value to store
 * @param defaultValue the default value to set upon reset
 */
open class CollectionLiveData<Element, Enumerable : Collection<Element>>(
    initialValue: Enumerable,
    defaultValue: Enumerable
) :
    SafeMutableLiveData<Enumerable>(initialValue, defaultValue)