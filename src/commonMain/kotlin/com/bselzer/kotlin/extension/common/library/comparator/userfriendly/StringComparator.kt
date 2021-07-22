package com.bselzer.kotlin.extension.common.library.comparator.userfriendly

import com.bselzer.kotlin.extension.common.library.comparator.StringComparator
import com.bselzer.kotlin.extension.common.library.objects.userFriendly

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