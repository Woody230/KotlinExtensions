package com.bselzer.ktx.serialization

import com.bselzer.ktx.serialization.json.JsonContext
import com.bselzer.ktx.serialization.serializer.DelimitedListSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonPrimitive
import nl.adaptivity.xmlutil.serialization.XML
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import kotlin.test.Test
import kotlin.test.assertEquals

class DelimitedListSerializerTests {
    @Serializable
    private enum class TestEnum {
        @SerialName("Foo")
        A,

        @SerialName("Bar")
        B,

        @SerialName("Baz")
        C
    }

    @Serializable
    @XmlSerialName(value = "Configuration", namespace = "", prefix = "")
    private data class Configuration(
        @Serializable(with = DelimitedListSerializer::class)
        @XmlSerialName(value = "enums", namespace = "", prefix = "")
        val enums: List<TestEnum> = emptyList()
    )

    private val xml = XML {}

    @Test
    fun enums() {
        val delimited = "Bar|Foo|Baz"
        val strings = listOf("Bar", "Foo", "Baz")
        val enums = listOf(TestEnum.B, TestEnum.A, TestEnum.C)
        val configXml = "<Configuration enums=\"$delimited\"/>"
        val config = Configuration(enums = enums)

        with(JsonContext) {
            assertEquals(enums, strings.decode())
        }

        assertEquals(config, xml.decodeFromString(configXml))
        assertEquals(configXml, xml.encodeToString(config))
    }

    @Test
    fun ints() {
        val delimited = "65|10295|5109"
        val ints = listOf(65, 10295, 5109)
        val serializer = DelimitedListSerializer(Int.serializer())

        assertEquals(delimited, Json.encodeToJsonElement(serializer, ints).jsonPrimitive.content)
        assertEquals(ints, Json.decodeFromJsonElement(serializer, JsonPrimitive(delimited)))
    }
}