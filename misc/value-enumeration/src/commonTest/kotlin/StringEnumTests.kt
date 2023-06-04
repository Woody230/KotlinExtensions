import com.bselzer.ktx.value.enumeration.StringEnum
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class StringEnumTests {
    @Serializable
    private enum class TestEnum {
        @SerialName("Foo")
        A,

        @SerialName("1")
        B
    }

    @Serializable
    private data class TestClass(
        val wrapper: StringEnum<TestEnum>
    )

    private val lenientJson = Json { isLenient = true }

    @Test
    fun deserialize_WithValidEnum_ToEnum() {
        // Arrange
        val input = "\"Foo\""

        // Act
        val wrapper = Json.decodeFromString<StringEnum<TestEnum>>(input)
        val enum = wrapper.toEnum()

        // Assert
        assertEquals(TestEnum.A, enum)
        assertEquals("Foo", wrapper.toString())
    }

    @Test
    fun deserialize_WithNumeric_ToEnum() {
        // Arrange
        val input = "{ \"wrapper\": 1 }"

        // Act
        val wrapper = lenientJson.decodeFromString<TestClass>(input).wrapper
        val enum = wrapper.toEnum()

        // Assert
        assertEquals(TestEnum.B, enum)
        assertEquals("1", wrapper.toString())
    }

    @Test
    fun deserialize_WithInvalidEnum_ToEnum() {
        // Arrange
        val input = "\"Fizz\""

        // Act
        val wrapper = Json.decodeFromString<StringEnum<TestEnum>>(input)
        val exception = runCatching { wrapper.toEnum() }.exceptionOrNull()

        // Assert
        assertNotNull(exception)
        assertEquals("Fizz", wrapper.toString())
    }

    @Test
    fun deserialize_WithValidEnum_ToEnumOrNull() {
        // Arrange
        val input = "\"Foo\""

        // Act
        val wrapper = Json.decodeFromString<StringEnum<TestEnum>>(input)
        val enum = wrapper.toEnumOrNull()

        // Assert
        assertEquals(TestEnum.A, enum)
        assertEquals("Foo", wrapper.toString())
    }

    @Test
    fun deserialize_WithInvalidEnum_ToEnumOrNull() {
        // Arrange
        val input = "\"Fizz\""

        // Act
        val wrapper = Json.decodeFromString<StringEnum<TestEnum>>(input)
        val enum = wrapper.toEnumOrNull()

        // Assert
        assertNull(enum)
        assertEquals("Fizz", wrapper.toString())
    }

    @Test
    fun serialize() {
        // Arrange
        val enum = StringEnum("Foo", serializer<TestEnum>())

        // Act
        val output = Json.encodeToString(enum)

        // Assert
        assertEquals("\"Foo\"", output)
    }
}