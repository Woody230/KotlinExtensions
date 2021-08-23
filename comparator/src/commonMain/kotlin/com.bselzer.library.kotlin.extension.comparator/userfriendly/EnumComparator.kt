package com.bselzer.library.kotlin.extension.comparator.userfriendly

import com.bselzer.library.kotlin.extension.function.objects.userFriendly

/**
 * Compare enums based on their user friendly name.
 */
open class EnumComparator<E : Enum<E>> : Comparator<E>
{
    /**
     * A string comparator.
     */
    private val comparator = StringComparator()

    override fun compare(a: E, b: E): Int
    {
        return comparator.compare(a.userFriendly(), b.userFriendly())
    }
}