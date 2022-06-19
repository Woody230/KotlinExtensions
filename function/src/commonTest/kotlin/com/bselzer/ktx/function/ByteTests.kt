package com.bselzer.ktx.function

import com.bselzer.ktx.function.objects.toBits
import com.bselzer.ktx.function.objects.toByte
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class ByteTests
{
    @Test
    fun byteToBits()
    {
        // Arrange
        val input: Byte = 187.toByte()

        // Act
        val output = input.toBits()

        // Assert
        assertContentEquals(listOf(true, false, true, true, true, false, true, true), output)
    }

    @Test
    fun bitsToByte()
    {
        // Arrange
        val input = listOf(true, false, true, true, true, false, true, true)

        // Act
        val output = input.toByte()

        // Assert
        assertEquals(187.toByte(), output)
    }
}