package com.bselzer.library.kotlin.extension.function.collection

/**
 * Adds an element if the condition [block] is true using the passed in [item].
 */
fun <T> Collection<T>.plusElementIf(item: T, block: T.() -> Boolean): Collection<T>
{
    return when
    {
        block(item) -> plusElement(item)
        else -> this
    }
}

/**
 * Fills the collection with new objects from the [create] block in order to meet the [capacity].
 * @return the collection
 */
fun <T> Collection<T>.fill(capacity: Int, create: () -> T): Collection<T> = apply {
    val collection = this.toMutableList()

    // Fill the remaining space if the items do not exist.
    while (collection.size < capacity)
    {
        collection.add(create())
    }

    return collection
}