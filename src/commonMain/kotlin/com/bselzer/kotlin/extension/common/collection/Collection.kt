package com.bselzer.kotlin.extension.common.collection

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