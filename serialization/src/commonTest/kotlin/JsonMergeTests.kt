import com.bselzer.ktx.serialization.context.ArrayMergeHandling
import com.bselzer.ktx.serialization.context.JsonContext.Default.merge
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class JsonMergeTests {
    @Test
    fun mergeArraySelf() {
        // Arrange
        val sut = JsonArray(
            listOf(
                JsonPrimitive("1"),
                JsonPrimitive("2")
            )
        )

        // Act
        val actual = sut.merge(sut, handling = ArrayMergeHandling.REPLACE)

        // Assert
        assertContentEquals(sut, actual)
    }

    @Test
    fun mergeObjectSelf() {
        // Arrange
        val sut = JsonObject(
            mapOf(
                "1" to JsonPrimitive(1),
                "2" to JsonPrimitive(2)
            )
        )

        // Act
        val actual = sut.merge(sut)

        // Assert
        assertEquals(sut, actual)
    }

    @Test
    fun mergeArrayIntoArray_Replace() {
        // Arrange
        val sut = JsonArray(
            listOf(
                JsonPrimitive("1"),
                JsonPrimitive("2")
            )
        )

        val expected = JsonArray(
            listOf(
                JsonPrimitive("3"),
                JsonPrimitive("4")
            )
        )

        // Act
        val actual = sut.merge(expected, handling = ArrayMergeHandling.REPLACE)

        // Assert
        assertContentEquals(expected, actual)
    }

    @Test
    fun mergeArrayIntoArray_Concat() {
        // Arrange
        val sut = JsonArray(
            listOf(
                JsonPrimitive("1"),
                JsonPrimitive("2")
            )
        )

        val merge = JsonArray(
            listOf(
                JsonPrimitive("2"),
                JsonPrimitive("3"),
                JsonPrimitive("4")
            )
        )

        val expected = JsonArray(
            listOf(
                JsonPrimitive("1"),
                JsonPrimitive("2"),
                JsonPrimitive("2"),
                JsonPrimitive("3"),
                JsonPrimitive("4")
            )
        )

        // Act
        val actual = sut.merge(merge, handling = ArrayMergeHandling.CONCAT)

        // Assert
        assertContentEquals(expected, actual)
    }

    @Test
    fun mergeArrayIntoArray_Union() {
        // Arrange
        val sut = JsonArray(
            listOf(
                JsonPrimitive("1"),
                JsonPrimitive("2")
            )
        )

        val merge = JsonArray(
            listOf(
                JsonPrimitive("2"),
                JsonPrimitive("3"),
                JsonPrimitive("4")
            )
        )

        val expected = JsonArray(
            listOf(
                JsonPrimitive("1"),
                JsonPrimitive("2"),
                JsonPrimitive("3"),
                JsonPrimitive("4")
            )
        )

        // Act
        val actual = sut.merge(merge, handling = ArrayMergeHandling.UNION)

        // Assert
        assertContentEquals(expected, actual)
    }

    @Test
    fun mergeArrayIntoArray_Merge() {
        // Arrange
        val sut = JsonArray(
            listOf(
                JsonPrimitive("1"),
                JsonPrimitive("2")
            )
        )

        val merge = JsonArray(
            listOf(
                JsonPrimitive("2"),
            )
        )

        val expected = JsonArray(
            listOf(
                JsonPrimitive("2"),
                JsonPrimitive("2"),
            )
        )

        // Act
        val actual = sut.merge(merge, handling = ArrayMergeHandling.MERGE)

        // Assert
        assertContentEquals(expected, actual)
    }

    @Test
    fun mergeNull() {
        // Arrange
        val sut = JsonObject(
            mapOf(
                "a" to JsonPrimitive(1)
            )
        )

        val merge = JsonObject(
            mapOf(
                "a" to JsonNull
            )
        )

        // Act
        val actual = sut.merge(merge)

        // Assert
        assertEquals(sut, actual)
    }
}