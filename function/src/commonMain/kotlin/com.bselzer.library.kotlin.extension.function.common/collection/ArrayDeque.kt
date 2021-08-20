package com.bselzer.library.kotlin.extension.function.common.collection

/**
 * Removes the first [take] number of [T] objects.
 * @throws NoSuchElementException if this deque does not have enough elements
 */
fun <T> ArrayDeque<T>.removeFirst(take: Int): Collection<T>
{
    val list = mutableListOf<T>()
    (1..take).forEach { _ -> list.add(removeFirst()) }
    return list
}