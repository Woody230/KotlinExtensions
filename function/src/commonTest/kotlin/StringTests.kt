import com.bselzer.library.kotlin.extension.function.common.objects.userFriendly
import kotlin.test.Test
import kotlin.test.assertEquals

class StringTests
{
    @Test
    fun stringToUserFriendly()
    {
        // Arrange
        val input = "PHRASE_SPLIT_BY_UNDERSCORE"
        val expected = "Phrase Split By Underscore"

        // Act
        val result = input.userFriendly()

        // Assert
        assertEquals(result, expected)
    }

    @Test
    fun classToUserFriendly()
    {
        // Arrange
        val input = this::class
        val expected = "String Tests"

        // Act
        val result = input.userFriendly()

        // Assert
        assertEquals(result, expected)
    }
}