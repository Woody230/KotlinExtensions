package com.bselzer.library.kotlin.extension.function.common.objects

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

/**
 * @return the enum value of the string
 */
inline fun <reified E : Enum<E>> String.enumValue(): E = enumValueOf(this)

/**
 * @return the enum value of the string, or null if it is invalid
 */
inline fun <reified E : Enum<E>> String.enumValueOrNull(): E? = try
{
    enumValueOf<E>(this)
} catch (e: Exception)
{
    null
}

/**
 * @return the enum values of the collection of strings that can be converted
 */
inline fun <reified E : Enum<E>> Collection<String>.validEnumValues(): List<E> =
    mapNotNull { s -> s.enumValueOrNull<E>() }

/**
 * @return the enum values of the map of strings that can be converted
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified E : Enum<E>, T> Map<String, T>.validEnumValues(): Map<E, T> =
    mapKeys { e -> e.key.enumValueOrNull<E>() }.minus(null) as Map<E, T>