package com.bselzer.library.kotlin.extension.base64.common

/**
 * MIT License
 *
 * Copyright (c) 2019 jershell
 * Modifications Copyright (c) 2021 Brandon Selzer
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * @see <a href="https://github.com/jershell/kbase64">kbase64 by jershell</a>
 */

/**
 * The 64 characters available in the alphabet for encoding.
 */
private const val encodingAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"

/**
 * The 64 character conversions for decoding.
 */
private val decodingTable = intArrayOf(
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1,
    -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1,
    -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1
)

/**
 * @return the byte array with base64 encoding
 */
fun ByteArray.encodeBase64(): ByteArray
{
    val output = mutableListOf<Byte>()
    var padding = 0
    var position = 0
    while (position < this.size)
    {
        var b = this[position].toInt() and 0xFF shl 16 and 0xFFFFFF
        if (position + 1 < this.size) b = b or (this[position + 1].toInt() and 0xFF shl 8) else padding++
        if (position + 2 < this.size) b = b or (this[position + 2].toInt() and 0xFF) else padding++
        for (i in 0 until 4 - padding)
        {
            val c = b and 0xFC0000 shr 18
            output.add(encodingAlphabet[c].code.toByte())
            b = b shl 6
        }
        position += 3
    }
    for (i in 0 until padding)
    {
        output.add('='.code.toByte())
    }
    return output.toByteArray()
}

/**
 * @return the byte array without base encoding
 */
fun ByteArray.decodeBase64(): ByteArray
{
    val table = decodingTable

    val output = mutableListOf<Int>()
    var position = 0
    while (position < this.size)
    {
        var b: Int
        if (table[this[position].toInt()] != -1)
        {
            b = table[this[position].toInt()] and 0xFF shl 18
        } else
        {
            position++
            continue
        }
        var count = 0
        if (position + 1 < this.size && table[this[position + 1].toInt()] != -1)
        {
            b = b or (table[this[position + 1].toInt()] and 0xFF shl 12)
            count++
        }
        if (position + 2 < this.size && table[this[position + 2].toInt()] != -1)
        {
            b = b or (table[this[position + 2].toInt()] and 0xFF shl 6)
            count++
        }
        if (position + 3 < this.size && table[this[position + 3].toInt()] != -1)
        {
            b = b or (table[this[position + 3].toInt()] and 0xFF)
            count++
        }
        while (count > 0)
        {
            val c = b and 0xFF0000 shr 16
            output.add(c.toChar().code)
            b = b shl 8
            count--
        }
        position += 4
    }
    return output.map { it.toByte() }.toByteArray()
}

/**
 * @return the string without base64 encoding
 */
fun String.decodeBase64(): String
{
    return this.encodeToByteArray().decodeBase64().decodeToString()
}

/**
 * @return the string with base64 encoding
 */
fun String.encodeBase64(): String
{
    return this.encodeToByteArray().encodeBase64().decodeToString()
}

/**
 * @return a byte array from a base64 encoded string
 */
fun String.decodeBase64ToByteArray(): ByteArray
{
    return this.encodeToByteArray().decodeBase64()
}

/**
 * @return a string from a base64 encoded byte array
 */
fun ByteArray.decodeBase64ToString(): String
{
    return this.decodeBase64().decodeToString()
}

/**
 * @return a string with base64 encoding
 */
fun ByteArray.encodeBase64ToString(): String
{
    return this.encodeBase64().decodeToString()
}
