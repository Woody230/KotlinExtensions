import com.bselzer.ktx.serialization.context.YamlContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class EnumTests {
    @Serializable
    private enum class TestEnum {
        A,

        @SerialName("Z")
        B,

        @SerialName("C")
        C
    }

    @Test
    fun map_StringToEnum() {
        // Arrange
        val map1 = mapOf("C" to 3, "B" to 2, "E" to 5, "D" to 4, "A" to 1, "F" to 6, "Z" to 10)
        val map2 = mapOf("A" to "4", "C" to "7")

        // Act
        val output1 = with(YamlContext) { map1.decodeKeysOrEmpty<TestEnum, Int>() }
        val output2 = with(YamlContext) { map2.decodeKeysOrEmpty<TestEnum, String>() }

        // Assert
        assertEquals(3, output1.count())
        assertEquals(1, output1[TestEnum.A])
        assertEquals(10, output1[TestEnum.B])
        assertEquals(3, output1[TestEnum.C])

        assertEquals(2, output2.count())
        assertEquals("4", output2[TestEnum.A])
        assertEquals("7", output2[TestEnum.C])
    }

    @Test
    fun stringToEnum() {
        // Arrange
        val correct = "Z"
        val exact = "B"
        val insensitive = "z"
        val exactInsensitive = "b"

        // Act / Assert
        with(YamlContext) {
            assertEquals(TestEnum.B, correct.decodeOrNull())
            assertNull(exact.decodeOrNull<TestEnum>())
            assertNull(insensitive.decodeOrNull<TestEnum>())
            assertNull(exactInsensitive.decodeOrNull<TestEnum>())
        }
    }
}