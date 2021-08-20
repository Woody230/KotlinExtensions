package com.bselzer.library.kotlin.extension.function.common.collection

/**
 * @return the integer as a byte array in little endian format
 */
fun Int.toByteArray(): ByteArray = byteArrayOf(
    this.toByte(),
    (this ushr 8).toByte(),
    (this ushr 16).toByte(),
    (this ushr 24).toByte(),
)

/**
 * @return the integers as a byte array in little endian format
 */
fun IntArray.toByteArray(): ByteArray
{
    var array = byteArrayOf()
    forEach { int -> array += int.toByteArray() }
    return array
}

/**
 * @return an integer from a byte array in little endian format
 */
fun ByteArray.toInt(): Int
{
    var int = 0
    forEachIndexed { index, byte -> int = int or (byte.toUByte().toInt() shl 8 * index) }
    return int
}

/**
 * @return an integer from a collection of bytes in little endian format
 */
fun Collection<Byte>.toInt(): Int = toByteArray().toInt()

/**
 * @return a short as a byte array in little endian format
 */
fun Short.toByteArray(): ByteArray = byteArrayOf(
    this.toByte(),
    (this.toInt() ushr 8).toByte(),
)

/**
 * @return a short from a byte array in little endian format
 */
fun ByteArray.toShort(): Short = toInt().toShort()

/**
 * @return a short from a byte array in little endian format
 */
fun Collection<Byte>.toShort(): Short = toInt().toShort()

/**
 * Take the first [take] number of bytes and fill the byte array to meet the [capacity].
 * @return an integer as a byte array in little endian format
 */
fun Int.toByteArray(take: Int, capacity: Int): ByteArray
{
    val bytes = this.toByteArray().take(take).toMutableList()

    // Fill the remaining slots with zero.
    return bytes.fill(capacity) { 0 }.toByteArray()
}