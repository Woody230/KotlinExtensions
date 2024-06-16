package com.bselzer.ktx.function.collection

import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

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

/**
 * Decodes symbols using base64.
 */
@OptIn(ExperimentalEncodingApi::class)
fun ByteArray.decodeBase64(): ByteArray = Base64.decode(this)

/**
 * Decodes symbols using base64.
 */
fun ByteArray.decodeBase64ToString(): String = this.decodeBase64().decodeToString()

/**
 * Encodes symbols using base64.
 */
@OptIn(ExperimentalEncodingApi::class)
fun ByteArray.encodeBase64(): ByteArray = Base64.encodeToByteArray(this)

/**
 * Encodes symbols using base64.
 */
fun ByteArray.encodeBase64ToString(): String = this.encodeBase64().decodeToString()

