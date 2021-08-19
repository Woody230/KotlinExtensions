import com.bselzer.library.kotlin.extension.function.common.collection.toInt
import kotlin.test.Test
import kotlin.test.assertEquals

class ByteArrayTests
{
    @Test
    fun byteArrayToInt()
    {
        // Arrange
        val oneByte = byteArrayOf(15)
        val fourByte = byteArrayOf(219.toByte(), 39, 0, 0)

        // Act
        val oneInt = oneByte.toInt()
        val fourInt = fourByte.toInt()

        // Assert
        assertEquals(15, oneInt)
        assertEquals(10203, fourInt)
    }
}