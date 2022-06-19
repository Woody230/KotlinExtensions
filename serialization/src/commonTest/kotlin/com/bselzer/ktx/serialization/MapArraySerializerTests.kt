package com.bselzer.ktx.serialization

import com.bselzer.ktx.serialization.serializer.MapArraySerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * Represents tests for the [MapArraySerializer] class.
 */
class MapArraySerializerTests
{
    @Serializable
    class CompilerTest
    {
        // Used to make sure that there is no compilation error. https://github.com/Kotlin/kotlinx.serialization/issues/1587
        @Serializable(with = MapArraySerializer::class)
        val a: Map<Int, Int> = emptyMap()
    }

    /**
     * Verifies that a json array of json arrays of integers can be deserialized into a map.
     */
    @Test
    fun processIntInt_Deserializes()
    {
        // Arrange
        val serializer = MapArraySerializer(Int.serializer(), Int.serializer())
        val input = "[\n" +
                "      [\n" +
                "        1,\n" +
                "        12343\n" +
                "      ],\n" +
                "      [\n" +
                "        2,\n" +
                "        12417\n" +
                "      ],\n" +
                "      [\n" +
                "        7,\n" +
                "        12371\n" +
                "      ]]"

        // Act
        val output = Json.decodeFromString(serializer, input)

        // Assert
        assertEquals(3, output.count())
        assertEquals(12343, output[1])
        assertEquals(12417, output[2])
        assertEquals(12371, output[7])
    }

    /**
     * Verifies that a map of integers can be deserialized into a json array of json arrays.
     */
    @Test
    fun processIntInt_Serializes()
    {
        // Arrange
        val serializer = MapArraySerializer(Int.serializer(), Int.serializer())
        val input = mapOf(1 to 3, 5 to 2, 9 to 11)

        // Act
        val output = Json.encodeToJsonElement(serializer, input)

        // Assert
        val root = output as JsonArray
        assertNotNull(root)
        assertEquals(3, root.count())
        assertPair(root[0], "1", "3")
        assertPair(root[1], "5", "2")
        assertPair(root[2], "9", "11")
    }

    /**
     * Asserts that the mapping pair exists in the element.
     */
    private fun assertPair(element: JsonElement, key: String, value: String)
    {
        val pair = element as JsonArray
        assertNotNull(pair)

        val keyContent = (pair.getOrNull(0) as JsonPrimitive?)?.content
        assertNotNull(keyContent)
        assertEquals(key, keyContent)

        val valueContent = (pair.getOrNull(1) as JsonPrimitive?)?.content
        assertNotNull(valueContent)
        assertEquals(value, valueContent)
    }
}