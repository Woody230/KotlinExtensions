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