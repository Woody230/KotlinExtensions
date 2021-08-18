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