package com.bselzer.ktx.function.objects

import com.bselzer.ktx.function.collection.decodeBase64
import com.bselzer.ktx.function.collection.encodeBase64
import kotlin.reflect.KClass

/**
 * @return a user friendly string
 */
fun String.userFriendly(): String
{
    // Use lowercase characters and replace underscores with spaces.
    val name = lowercase().replace("_", " ")
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
fun KClass<*>.userFriendly(): String {
    // Add spaces between capital letters.
    return this.simpleName?.replace(Regex("(\\p{Ll})(\\p{Lu})"), "$1 $2").toString()
}

fun String.capitalize(): String = replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }

/**
 * Decodes symbols using base64.
 */
fun String.decodeBase64(): String = this.encodeToByteArray().decodeBase64().decodeToString()

/**
 * Encodes symbols using base64.
 */
fun String.encodeBase64(): String = this.encodeToByteArray().encodeBase64().decodeToString()

/**
 * Decodes symbols using base64.
 */
fun String.decodeBase64ToByteArray(): ByteArray = this.encodeToByteArray().decodeBase64()