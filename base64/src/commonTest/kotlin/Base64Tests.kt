import com.bselzer.ktx.base64.decodeBase64ToByteArray
import com.bselzer.ktx.base64.encodeBase64ToString
import com.bselzer.ktx.function.collection.toByteArray
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class Base64Tests
{
    @Test
    fun encode()
    {
        // Arrange
        val input = byteArrayOf(1) + 10203.toByteArray()

        // Act
        val output = input.encodeBase64ToString()

        // Assert
        assertEquals("AdsnAAA=", output)
    }

    @Test
    fun decode()
    {
        // Arrange
        val input = "AdsnAAA="

        // Act
        val output = input.decodeBase64ToByteArray()

        // Assert
        assertContentEquals(byteArrayOf(1, 219.toByte(), 39, 0, 0), output)
    }
}