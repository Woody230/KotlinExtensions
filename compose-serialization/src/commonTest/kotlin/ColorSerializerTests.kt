import com.bselzer.ktx.compose.ui.graphics.color.Hex
import com.bselzer.ktx.compose.ui.graphics.color.color
import com.bselzer.ktx.serialization.serializer.ColorSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonPrimitive
import kotlin.test.Test
import kotlin.test.assertEquals

class ColorSerializerTests {
    @Test
    fun encode() {
        // Arrange
        val input = Hex("#cf7150").color()

        // Act
        val output = Json.encodeToString(ColorSerializer(), input)

        // Assert
        assertEquals("\"#ffcf7150\"", output)
    }

    @Test
    fun decode() {
        // Arrange
        val input = JsonPrimitive("#cf7150")

        // Act
        val output = Json.decodeFromJsonElement(ColorSerializer(), input)

        // Assert
        assertEquals(Hex("#cf7150").color(), output)
    }
}