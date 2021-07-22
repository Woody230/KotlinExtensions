package com.bselzer.library.kotlin.extension.comparator.common.userfriendly

import com.bselzer.library.kotlin.extension.function.common.objects.userFriendly

/**
 * Compare strings based on their user friendly name.
 */
open class StringComparator : com.bselzer.library.kotlin.extension.comparator.common.StringComparator()
{
    override fun compare(a: String?, b: String?): Int
    {
        return super.compare(a?.userFriendly(), b?.userFriendly())
    }
}