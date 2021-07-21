package com.bselzer.kotlin.extension.common.library.objects

/**
 * @return a user friendly string for enum names
 */
fun Enum<*>.userFriendly(): String = this.name.userFriendly()

/**
 * @return whether or not the enum exists and is one of the [enums]
 */
fun <E : Enum<E>> E?.isOneOf(vararg enums: E): Boolean
{
    this ?: return false
    return enums.contains(this)
}