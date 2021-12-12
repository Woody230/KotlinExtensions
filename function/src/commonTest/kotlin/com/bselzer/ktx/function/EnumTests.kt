package com.bselzer.ktx.function

import com.bselzer.ktx.function.objects.validEnumValues
import kotlin.test.Test
import kotlin.test.assertEquals

class EnumTests
{
    private enum class TestEnum
    {
        A,
        B,
        C
    }

    @Test
    fun map_StringToEnum()
    {
        // Arrange
        val map1 = mapOf("C" to 3, "B" to 2, "E" to 5, "D" to 4, "A" to 1, "F" to 6)
        val map2 = mapOf("A" to "4", "C" to "7")

        // Act
        val output1 = map1.validEnumValues<TestEnum, Int>()
        val output2 = map2.validEnumValues<TestEnum, String>()

        // Assert
        assertEquals(3, output1.count())
        assertEquals(1, output1[TestEnum.A])
        assertEquals(2, output1[TestEnum.B])
        assertEquals(3, output1[TestEnum.C])

        assertEquals(2, output2.count())
        assertEquals("4", output2[TestEnum.A])
        assertEquals("7", output2[TestEnum.C])
    }
}