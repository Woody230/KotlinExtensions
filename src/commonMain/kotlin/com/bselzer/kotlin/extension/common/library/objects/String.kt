package com.bselzer.kotlin.extension.common.library.objects

import kotlin.reflect.KClass

/**
 * @return a user friendly string
 */
fun String.userFriendly(): String
{
    // Use lowercase characters and replace underscores with spaces.
    val name = toLowerCase().replace("_", " ")
    val builder = StringBuilder(name.length)

    // Capitalize the first letter of words split by spaces.
    name.split(" ").forEach { string ->
        builder.append(string.capitalize()).append(" ")
    }

    val result = builder.toString()
    return result.substring(0, result.length - 1)
}

/**
 * @return a user friendly string
 */
fun KClass<*>.userFriendly(): String
{
    // Add spaces between capital letters.
    return this.simpleName?.replace(Regex("(\\p{Ll})(\\p{Lu})"), "$1 $2").toString()
}