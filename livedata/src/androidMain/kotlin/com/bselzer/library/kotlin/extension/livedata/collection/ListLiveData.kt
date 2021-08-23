package com.bselzer.library.kotlin.extension.livedata.collection

/**
 * Null-safe live data for lists.
 * A [comparator] can be set to maintain a sorted list.
 * @param Element the type of value stored in the list
 * @param initialValue the initial value to store
 * @param defaultValue the default value to set upon reset
 * @param comparator the comparator used to sort the list
 */
open class ListLiveData<Element>(
    initialValue: List<Element> = emptyList(),
    defaultValue: List<Element> = emptyList(),
    var comparator: Comparator<Element>? = null
) :
    CollectionLiveData<Element, List<Element>>(initialValue.sort(comparator), defaultValue)
{
    companion object
    {
        /**
         * Sort the list with the [comparator] if it exists.
         * @return the sorted list
         */
        protected fun <Element> List<Element>.sort(comparator: Comparator<Element>?): List<Element>
        {
            return comparator?.let {
                this.sortedWith(it)
            } ?: this
        }
    }

    /**
     * Sorts the list with the [comparator] and then sets the [value]. If there are active observers, the value will be dispatched to them.
     *
     * This method must be called from the main thread. If you need set a value from a background
     * thread, you can use [postValue]
     *
     * @param value the new list
     */
    override fun setValue(value: List<Element>)
    {
        super.setValue(value.sort(comparator))
    }

    /**
     * Posts a task to a main thread to sort the list with the [comparator] and then set the [value]. So if you have the following code
     * executed on the main thread:
     *
     * liveData.postValue("a");
     *
     * liveData.setValue("b");
     *
     * The value "b" would be set at first and later the main thread would override it with
     * the value "a".
     * If you called this method multiple times before the main thread executed a posted task, only
     * the last value would be dispatched.
     *
     * @param value the new list
     */
    override fun postValue(value: List<Element>)
    {
        super.postValue(value.sort(comparator))
    }
}