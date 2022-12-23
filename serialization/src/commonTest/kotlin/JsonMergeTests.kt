import com.bselzer.ktx.serialization.context.ArrayMergeHandling
import com.bselzer.ktx.serialization.context.JsonContext.Default.merge
import com.bselzer.ktx.serialization.context.JsonMergeOptions
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

        val options = JsonMergeOptions(
            arrayHandling = ArrayMergeHandling.REPLACE
        )

        // Act
        val actual = sut.merge(sut, options)

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

        val options = JsonMergeOptions(
            arrayHandling = ArrayMergeHandling.REPLACE
        )

        // Act
        val actual = sut.merge(expected, options)

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

        val options = JsonMergeOptions(
            arrayHandling = ArrayMergeHandling.CONCAT
        )

        // Act
        val actual = sut.merge(merge, options)

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

        val options = JsonMergeOptions(
            arrayHandling = ArrayMergeHandling.UNION
        )

        // Act
        val actual = sut.merge(merge, options)

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

        val options = JsonMergeOptions(
            arrayHandling = ArrayMergeHandling.MERGE
        )

        // Act
        val actual = sut.merge(merge, options)

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

    @Test
    fun mergeObjectProperty() {
        // Arrange
        val sut = JsonObject(
            mapOf(
                "Property1" to JsonPrimitive(1),
            )
        )

        val merge = JsonObject(
            mapOf(
                "Property2" to JsonPrimitive(2)
            )
        )

        val expected = JsonObject(
            mapOf(
                "Property1" to JsonPrimitive(1),
                "Property2" to JsonPrimitive(2)
            )
        )

        // Act
        val actual = sut.merge(merge)

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun mergeChildObject() {
        // Arrange
        val sut = JsonObject(
            mapOf(
                "Property1" to JsonObject(
                    mapOf(
                        "SubProperty1" to JsonPrimitive(1)
                    )
                ),
            )
        )

        val merge = JsonObject(
            mapOf(
                "Property1" to JsonObject(
                    mapOf(
                        "SubProperty2" to JsonPrimitive(2)
                    )
                ),
            )
        )

        val expected = JsonObject(
            mapOf(
                "Property1" to JsonObject(
                    mapOf(
                        "SubProperty1" to JsonPrimitive(1),
                        "SubProperty2" to JsonPrimitive(2)
                    )
                )
            )
        )

        // Act
        val actual = sut.merge(merge)

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun mergeMismatchedTypesRoot() {
        // Arrange
        val sut = JsonArray(
            listOf(
                JsonObject(
                    mapOf(
                        "Property1" to JsonPrimitive(1)
                    )
                ),
                JsonObject(
                    mapOf(
                        "Property1" to JsonPrimitive(1)
                    )
                ),
            )
        )

        val expected = JsonObject(
            mapOf(
                "Property1" to JsonObject(
                    mapOf(
                        "SubProperty1" to JsonPrimitive(1)
                    )
                ),
            )
        )

        // Act
        val actual = sut.merge(expected)

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun mergeMultipleObjects() {
        // Arrange
        val sut = JsonObject(
            mapOf(
                "Property1" to JsonObject(
                    mapOf(
                        "SubProperty1" to JsonPrimitive(1)
                    )
                ),
            )
        )

        val merge = JsonObject(
            mapOf(
                "Property1" to JsonObject(
                    mapOf(
                        "SubProperty2" to JsonPrimitive(2)
                    )
                ),
                "Property2" to JsonPrimitive(2)
            )
        )

        val expected = JsonObject(
            mapOf(
                "Property1" to JsonObject(
                    mapOf(
                        "SubProperty1" to JsonPrimitive(1),
                        "SubProperty2" to JsonPrimitive(2)
                    )
                ),
                "Property2" to JsonPrimitive(2)
            )
        )

        // Act
        val actual = sut.merge(merge)

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun mergeArray() {
        // Arrange
        val sut = JsonObject(
            mapOf(
                "Array1" to JsonArray(
                    listOf(
                        JsonObject(
                            mapOf(
                                "Property1" to JsonObject(
                                    mapOf(
                                        "Property1" to JsonPrimitive(1),
                                        "Property2" to JsonPrimitive(2),
                                        "Property3" to JsonPrimitive(3),
                                        "Property4" to JsonPrimitive(4),
                                        "Property5" to JsonNull,
                                    )
                                )
                            )
                        ),
                        JsonObject(emptyMap()),
                        JsonPrimitive(3),
                        JsonNull,
                        JsonPrimitive(5),
                        JsonNull
                    )
                ),
            )
        )

        val merge = JsonObject(
            mapOf(
                "Array1" to JsonArray(
                    listOf(
                        JsonObject(
                            mapOf(
                                "Property1" to JsonObject(
                                    mapOf(
                                        "Property1" to JsonNull,
                                        "Property2" to JsonPrimitive(3),
                                        "Property3" to JsonObject(emptyMap()),
                                        "Property5" to JsonNull,
                                    )
                                )
                            )
                        ),
                        JsonNull,
                        JsonNull,
                        JsonPrimitive(4),
                        JsonPrimitive(5.1),
                        JsonNull,
                        JsonObject(
                            mapOf(
                                "Property1" to JsonPrimitive(1)
                            )
                        )
                    )
                ),
            )
        )

        val expected = JsonObject(
            mapOf(
                "Array1" to JsonArray(
                    listOf(
                        JsonObject(
                            mapOf(
                                "Property1" to JsonObject(
                                    mapOf(
                                        "Property1" to JsonPrimitive(1),
                                        "Property2" to JsonPrimitive(3),
                                        "Property3" to JsonObject(emptyMap()),
                                        "Property4" to JsonPrimitive(4),
                                        "Property5" to JsonNull,
                                    )
                                )
                            )
                        ),
                        JsonObject(emptyMap()),
                        JsonPrimitive(3),
                        JsonPrimitive(4),
                        JsonPrimitive(5.1),
                        JsonNull,
                        JsonObject(
                            mapOf(
                                "Property1" to JsonPrimitive(1)
                            )
                        )
                    )
                ),
            )
        )

        val options = JsonMergeOptions(
            arrayHandling = ArrayMergeHandling.MERGE
        )

        // Act
        val actual = sut.merge(merge, options)

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun concatArray() {
        // Arrange
        val sut = JsonObject(
            mapOf(
                "Array1" to JsonArray(
                    listOf(
                        JsonObject(
                            mapOf(
                                "Property1" to JsonPrimitive(1)
                            )
                        ),
                        JsonObject(
                            mapOf(
                                "Property1" to JsonPrimitive(1)
                            )
                        )
                    )
                ),
            )
        )

        val merge = JsonObject(
            mapOf(
                "Array1" to JsonArray(
                    listOf(
                        JsonObject(
                            mapOf(
                                "Property1" to JsonPrimitive(1)
                            )
                        ),
                        JsonObject(
                            mapOf(
                                "Property2" to JsonPrimitive(2)
                            )
                        ),
                        JsonObject(
                            mapOf(
                                "Property3" to JsonPrimitive(3)
                            )
                        )
                    )
                ),
            )
        )

        val expected = JsonObject(
            mapOf(
                "Array1" to JsonArray(
                    listOf(
                        JsonObject(
                            mapOf(
                                "Property1" to JsonPrimitive(1)
                            )
                        ),
                        JsonObject(
                            mapOf(
                                "Property1" to JsonPrimitive(1)
                            )
                        ),
                        JsonObject(
                            mapOf(
                                "Property1" to JsonPrimitive(1)
                            )
                        ),
                        JsonObject(
                            mapOf(
                                "Property2" to JsonPrimitive(2)
                            )
                        ),
                        JsonObject(
                            mapOf(
                                "Property3" to JsonPrimitive(3)
                            )
                        )
                    )
                ),
            )
        )

        val options = JsonMergeOptions(
            arrayHandling = ArrayMergeHandling.CONCAT
        )

        // Act
        val actual = sut.merge(merge, options)

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun mergeMismatchingTypesInArray() {
        // Arrange
        val sut = JsonArray(
            listOf(
                JsonPrimitive(true),
                JsonNull,
                JsonObject(
                    mapOf(
                        "Property1" to JsonPrimitive(1)
                    ),
                ),
                JsonArray(
                    listOf(
                        JsonPrimitive(1)
                    )
                ),
                JsonObject(
                    mapOf(
                        "Property1" to JsonPrimitive(1)
                    ),
                ),
                JsonPrimitive(1),
                JsonArray(
                    listOf(
                        JsonPrimitive(1)
                    )
                ),
            )
        )

        val merge = JsonArray(
            listOf(
                JsonPrimitive(1),
                JsonPrimitive(5),
                JsonArray(
                    listOf(
                        JsonPrimitive(1)
                    )
                ),
                JsonObject(
                    mapOf(
                        "Property1" to JsonPrimitive(1)
                    ),
                ),
                JsonPrimitive(true),
                JsonObject(
                    mapOf(
                        "Property1" to JsonPrimitive(1)
                    ),
                ),
                JsonNull
            )
        )

        val expected = JsonArray(
            listOf(
                JsonPrimitive(1),
                JsonPrimitive(5),
                JsonArray(
                    listOf(
                        JsonPrimitive(1)
                    )
                ),
                JsonObject(
                    mapOf(
                        "Property1" to JsonPrimitive(1)
                    ),
                ),
                JsonPrimitive(true),
                JsonObject(
                    mapOf(
                        "Property1" to JsonPrimitive(1)
                    ),
                ),
                JsonArray(
                    listOf(
                        JsonPrimitive(1)
                    )
                ),
            )
        )

        val options = JsonMergeOptions(
            arrayHandling = ArrayMergeHandling.MERGE
        )

        // Act
        val actual = sut.merge(merge, options)

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun mergeMismatchingTypesInObject() {
        // Arrange
        val sut = JsonObject(
            mapOf(
                "Property1" to JsonArray(
                    listOf(
                        JsonPrimitive(1)
                    )
                ),
                "Property2" to JsonArray(
                    listOf(
                        JsonPrimitive(1)
                    )
                ),
                "Property3" to JsonPrimitive(true),
                "Property4" to JsonPrimitive(true)
            )
        )

        val merge = JsonObject(
            mapOf(
                "Property1" to JsonObject(
                    mapOf(
                        "Nested" to JsonPrimitive(true)
                    )
                ),
                "Property2" to JsonPrimitive(true),
                "Property3" to JsonArray(
                    listOf(
                        JsonPrimitive(1)
                    )
                ),
                "Property4" to JsonNull
            )
        )

        val expected = JsonObject(
            mapOf(
                "Property1" to JsonObject(
                    mapOf(
                        "Nested" to JsonPrimitive(true)
                    )
                ),
                "Property2" to JsonPrimitive(true),
                "Property3" to JsonArray(
                    listOf(
                        JsonPrimitive(1)
                    )
                ),
                "Property4" to JsonPrimitive(true)
            )
        )

        // Act
        val actual = sut.merge(merge)

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun mergeArrayOverwrite_Nested() {
        // Arrange
        val sut = JsonObject(
            mapOf(
                "Array1" to JsonArray(
                    listOf(
                        JsonPrimitive(1),
                        JsonPrimitive(2),
                        JsonPrimitive(3)
                    )
                ),
            )
        )

        val expected = JsonObject(
            mapOf(
                "Array1" to JsonArray(
                    listOf(
                        JsonPrimitive(4),
                        JsonPrimitive(5),
                    )
                ),
            )
        )

        val options = JsonMergeOptions(
            arrayHandling = ArrayMergeHandling.REPLACE
        )

        // Act
        val actual = sut.merge(expected, options)

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun mergeArrayOverwrite_Root() {
        // Arrange
        val sut = JsonArray(
            listOf(
                JsonPrimitive(1),
                JsonPrimitive(2),
                JsonPrimitive(3)
            )
        )

        val expected = JsonArray(
            listOf(
                JsonPrimitive(4),
                JsonPrimitive(5),
            )
        )

        val options = JsonMergeOptions(
            arrayHandling = ArrayMergeHandling.REPLACE
        )

        // Act
        val actual = sut.merge(expected, options)

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun unionArrays() {
        // Arrange
        val sut = JsonObject(
            mapOf(
                "Array1" to JsonArray(
                    listOf(
                        JsonObject(
                            mapOf(
                                "Property1" to JsonPrimitive(1)
                            )
                        ),
                        JsonObject(
                            mapOf(
                                "Property1" to JsonPrimitive(1)
                            )
                        ),
                    )
                ),
            )
        )

        val merge = JsonObject(
            mapOf(
                "Array1" to JsonArray(
                    listOf(
                        JsonObject(
                            mapOf(
                                "Property1" to JsonPrimitive(1)
                            )
                        ),
                        JsonObject(
                            mapOf(
                                "Property2" to JsonPrimitive(2)
                            )
                        ),
                        JsonObject(
                            mapOf(
                                "Property3" to JsonPrimitive(3)
                            )
                        )
                    )
                ),
            )
        )

        val expected = JsonObject(
            mapOf(
                "Array1" to JsonArray(
                    listOf(
                        JsonObject(
                            mapOf(
                                "Property1" to JsonPrimitive(1)
                            )
                        ),
                        JsonObject(
                            mapOf(
                                "Property2" to JsonPrimitive(2)
                            )
                        ),
                        JsonObject(
                            mapOf(
                                "Property3" to JsonPrimitive(3)
                            )
                        )
                    )
                ),
            )
        )

        val options = JsonMergeOptions(
            arrayHandling = ArrayMergeHandling.UNION
        )

        // Act
        val actual = sut.merge(merge, options)

        // Assert
        assertEquals(expected, actual)
    }
}