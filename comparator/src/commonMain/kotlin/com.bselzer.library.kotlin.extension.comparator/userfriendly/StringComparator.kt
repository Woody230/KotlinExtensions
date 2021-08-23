package com.bselzer.library.kotlin.extension.comparator.userfriendly

import com.bselzer.library.kotlin.extension.comparator.StringComparator
import com.bselzer.library.kotlin.extension.function.objects.userFriendly

/**
 * Compare strings based on their user friendly name.
 */
open class StringComparator : StringComparator()
{
    override fun compare(a: String?, b: String?): Int
    {
        return super.compare(a?.userFriendly(), b?.userFriendly())
    }
}