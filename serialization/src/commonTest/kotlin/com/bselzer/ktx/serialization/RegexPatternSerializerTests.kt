package com.bselzer.ktx.serialization

import com.bselzer.ktx.serialization.serializer.RegexPatternSerializer
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class RegexPatternSerializerTests {
    private val serializer = RegexPatternSerializer()
    private val pattern = "^Foo Bar Baz&"

    @Test
    fun regex() {
        val regex = Regex(pattern)

        val encoded = Json.encodeToString(serializer, regex)
        assertEquals("\"$pattern\"", encoded)

        val decoded = Json.decodeFromString(serializer, encoded)
        assertEquals(regex.pattern, decoded.pattern)
    }
}