package com.bselzer.library.kotlin.extension.common.comparator.userfriendly

import com.bselzer.library.kotlin.extension.common.comparator.StringComparator
import com.bselzer.library.kotlin.extension.common.objects.userFriendly

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