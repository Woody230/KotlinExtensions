import com.bselzer.ktx.serialization.context.JsonContext
import com.bselzer.ktx.serialization.serializer.DelimitedListSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
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
        val enums: List<TestEnum> = emptyList(),

        @Serializable(with = DelimitedListSerializer::class)
        @XmlSerialName(value = "ints", namespace = "", prefix = "")
        val ints: List<Int> = emptyList()
    )

    private val xml = XML {}

    @Test
    fun config() {
        val enums = listOf(TestEnum.B, TestEnum.A, TestEnum.C)
        val encodedEnums = listOf("Bar", "Foo", "Baz")
        val delimitedEnums = "Bar|Foo|Baz"

        val ints = listOf(65, 10295, 5109)
        val encodedInts = listOf("65", "10295", "5109")
        val delimitedInts = "65|10295|5109"

        val configXml = "<Configuration enums=\"$delimitedEnums\" ints=\"$delimitedInts\"/>"
        val config = Configuration(enums = enums, ints = ints)

        with(JsonContext) {
            assertEquals(enums, encodedEnums.decode())
            assertEquals(ints, encodedInts.decode())
        }

        assertEquals(config, xml.decodeFromString(configXml))
        assertEquals(configXml, xml.encodeToString(config))
    }
}