package com.bselzer.library.kotlin.extension.function.common

import com.bselzer.library.kotlin.extension.function.common.collection.toByteArray
import com.bselzer.library.kotlin.extension.function.common.collection.toInt
import com.bselzer.library.kotlin.extension.function.common.collection.toShort
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class ByteArrayTests
{
    @Test
    fun byteArrayToInt()
    {
        // Arrange
        val oneByte = byteArrayOf(15)
        val fourByte = byteArrayOf(219.toByte(), 39, 0, 0)

        // Act
        val oneInt = oneByte.toInt()
        val fourInt = fourByte.toInt()

        // Assert
        assertEquals(15, oneInt)
        assertEquals(10203, fourInt)
    }

    @Test
    fun intToByteArray()
    {
        // Arrange
        val oneInt = 15
        val twoInt = 10203

        // Act
        val oneByte = oneInt.toByteArray()
        val twoByte = twoInt.toByteArray()

        // Assert
        assertContentEquals(byteArrayOf(15, 0, 0, 0), oneByte)
        assertContentEquals(byteArrayOf(219.toByte(), 39, 0, 0), twoByte)
    }

    @Test
    fun byteArrayToShort()
    {
        // Arrange
        val oneByte = byteArrayOf(15)
        val twoByte = byteArrayOf(219.toByte(), 39)

        // Act
        val oneShort = oneByte.toShort()
        val twoShort = twoByte.toShort()

        // Assert
        assertEquals(15, oneShort)
        assertEquals(10203, twoShort)
    }

    @Test
    fun shortToByteArray()
    {
        // Arrange
        val oneShort: Short = 15
        val twoShort: Short = 10203

        // Act
        val oneByte = oneShort.toByteArray()
        val twoByte = twoShort.toByteArray()

        // Assert
        assertContentEquals(byteArrayOf(15, 0), oneByte)
        assertContentEquals(byteArrayOf(219.toByte(), 39), twoByte)
    }

    @Test
    fun intToByteArrayCapacity()
    {
        // Arrange
        val input = 12297151

        // Act
        val output = input.toByteArray(take = 2, capacity = 4)

        // Assert
        assertContentEquals(byteArrayOf(191.toByte(), 163.toByte(), 0, 0), output)
    }
}