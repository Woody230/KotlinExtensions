package com.bselzer.ktx.function.collection

/**
 * Insert all of the elements starting at index 0, keeping the order intact.
 */
fun <T> MutableList<T>.pushAll(items: Collection<T>)
{
    (0..items.size).forEach { i ->
        add(i, elementAt(i))
    }
}

/**
 * @return a list of [size]. New items are created with [create] if there are too few.
 */
fun <T> List<T>.exactSize(size: Int, create: () -> T): List<T>
{
    return when
    {
        this.size == size -> this

        // Too many elements, get the exact amount needed.
        this.size > size -> this.take(size)

        // Too few elements, create new ones.
        else -> this.fill(size, create).toList()
    }
}