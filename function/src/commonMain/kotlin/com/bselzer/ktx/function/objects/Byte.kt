package com.bselzer.ktx.function.objects

import kotlin.experimental.or

/**
 * Extracts the bits from the byte in left to right order.
 * @return the bits as a collection of boolean
 */
fun Byte.toBits(): List<Boolean>
{
    val flags = mutableListOf<Boolean>()
    (7 downTo 0).forEach { index ->
        // Mask with 1 to compare against the bit only and get a remainder of either 0 or 1.
        val bit = (this.toInt() shr index) and 1
        flags.add(bit.toBoolean())
    }
    return flags
}

/**
 * Combine bits into a byte in left to right order.
 * @return a byte of flags represented by a boolean array
 */
fun BooleanArray.toByte(): Byte = toList().toByte()

/**
 * Combine bits into a byte in left to right order.
 * @return a byte of flags represented by a collection of boolean
 */
fun Collection<Boolean>.toByte(): Byte
{
    var byte: Byte = 0
    var shift = 7
    forEach { flag ->
        byte = byte or (flag.toInt() shl shift).toByte()

        // Move the shifting to the right to apply the next flag.
        shift -= 1
    }
    return byte
}