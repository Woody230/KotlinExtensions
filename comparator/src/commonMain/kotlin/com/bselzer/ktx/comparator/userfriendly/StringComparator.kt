package com.bselzer.ktx.comparator.userfriendly

import com.bselzer.ktx.comparator.StringComparator
import com.bselzer.ktx.function.objects.userFriendly

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