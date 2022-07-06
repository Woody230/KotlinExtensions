import com.bselzer.ktx.serialization.serializer.IntegerSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonPrimitive
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

/**
 * Represents tests for the [IntegerSerializer] class.
 */
class IntegerSerializerTests {
    private val serializer = IntegerSerializer()
    private val json = Json { isLenient = true }

    /**
     * Verifies that an integer can be deserialized.
     */
    @Test
    fun processInteger_Deserializes() {
        // Arrange
        val input = JsonPrimitive(15)

        // Act
        val output = json.decodeFromJsonElement(serializer, input)

        // Assert
        assertEquals(15, output)
    }

    /**
     * Verifies that a boolean can be deserialized.
     */
    @Test
    fun processBoolean_Deserializes()
    {
        // Arrange
        val input = JsonPrimitive(true)

        // Act
        val output = json.decodeFromJsonElement(serializer, input)

        // Assert
        assertEquals(1, output)
    }

    /**
     * Verifies that a numeric string can be deserialized.
     */
    @Test
    fun processNumericString_Deserializes()
    {
        // Arrange
        val input = JsonPrimitive("30")

        // Act
        val output = Json.decodeFromJsonElement(serializer, input)

        // Assert
        assertEquals(30, output)
    }

    /**
     * Verifies that an empty string cannot be deserialized.
     */
    @Test
    fun processEmptyString_ThrowsException()
    {
        // Arrange
        val input = JsonPrimitive("")

        // Act / Assert
        assertFailsWith(NumberFormatException::class) {
            Json.decodeFromJsonElement(serializer, input)
        }
    }
}