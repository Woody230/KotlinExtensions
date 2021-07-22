package com.bselzer.library.kotlin.extension.common.comparator

/**
 * Compare nullable strings.
 */
open class StringComparator(private val ascending: Boolean = true) : Comparator<String?>
{
    override fun compare(a: String?, b: String?): Int
    {
        return when
        {
            a == null && b == null -> 0
            a == null -> -1
            b == null -> 1
            ascending -> a.compareTo(b)
            else -> b.compareTo(a)
        }
    }
}