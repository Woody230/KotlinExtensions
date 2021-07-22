package com.bselzer.library.kotlin.extension.common.comparator.userfriendly

import com.bselzer.library.kotlin.extension.common.comparator.StringComparator
import com.bselzer.library.kotlin.extension.common.objects.userFriendly

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