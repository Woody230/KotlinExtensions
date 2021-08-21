package com.bselzer.library.kotlin.extension.serialization.common

import com.bselzer.library.kotlin.extension.serialization.common.function.enumValueOrNull
import com.bselzer.library.kotlin.extension.serialization.common.function.validEnumValues
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class EnumTests
{
    @Serializable
    private enum class TestEnum
    {
        A,

        @SerialName("Z")
        B,

        @SerialName("C")
        C
    }

    @Test
    fun map_StringToEnum()
    {
        // Arrange
        val map1 = mapOf("C" to 3, "B" to 2, "E" to 5, "D" to 4, "A" to 1, "F" to 6, "Z" to 10)
        val map2 = mapOf("A" to "4", "C" to "7")

        // Act
        val output1 = map1.validEnumValues<TestEnum, Int>()
        val output2 = map2.validEnumValues<TestEnum, String>()

        // Assert
        assertEquals(3, output1.count())
        assertEquals(1, output1[TestEnum.A])
        assertEquals(10, output1[TestEnum.B])
        assertEquals(3, output1[TestEnum.C])

        assertEquals(2, output2.count())
        assertEquals("4", output2[TestEnum.A])
        assertEquals("7", output2[TestEnum.C])
    }

    @Test
    fun stringToEnum()
    {
        // Arrange
        val correct = "Z"
        val exact = "B"
        val insensitive = "z"
        val exactInsensitive = "b"

        // Act / Assert
        assertEquals(TestEnum.B, correct.enumValueOrNull())
        assertNull(exact.enumValueOrNull<TestEnum>())
        assertNull(insensitive.enumValueOrNull<TestEnum>())
        assertNull(exactInsensitive.enumValueOrNull<TestEnum>())
    }
}