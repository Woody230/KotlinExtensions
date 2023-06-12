import com.bselzer.ktx.serialization.context.YamlContext.Default.merge
import com.bselzer.ktx.serialization.merge.ArrayMergeHandling
import com.bselzer.ktx.serialization.merge.NullMergeHandling
import com.bselzer.ktx.serialization.merge.YamlMergeOptions
import net.mamoe.yamlkt.YamlList
import net.mamoe.yamlkt.YamlLiteral
import net.mamoe.yamlkt.YamlMap
import net.mamoe.yamlkt.YamlNull
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class YamlMergeTests {
    @Test
    fun mergeArraySelf() {
        // Arrange
        val sut = YamlList(
            listOf(
                YamlLiteral("1"),
                YamlLiteral("2")
            )
        )

        val options = YamlMergeOptions(
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
        val sut = YamlMap(
            mapOf(
                "1" to YamlLiteral("1"),
                "2" to YamlLiteral("2")
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
        val sut = YamlList(
            listOf(
                YamlLiteral("1"),
                YamlLiteral("2")
            )
        )

        val expected = YamlList(
            listOf(
                YamlLiteral("3"),
                YamlLiteral("4")
            )
        )

        val options = YamlMergeOptions(
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
        val sut = YamlList(
            listOf(
                YamlLiteral("1"),
                YamlLiteral("2")
            )
        )

        val merge = YamlList(
            listOf(
                YamlLiteral("2"),
                YamlLiteral("3"),
                YamlLiteral("4")
            )
        )

        val expected = YamlList(
            listOf(
                YamlLiteral("1"),
                YamlLiteral("2"),
                YamlLiteral("2"),
                YamlLiteral("3"),
                YamlLiteral("4")
            )
        )

        val options = YamlMergeOptions(
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
        val sut = YamlList(
            listOf(
                YamlLiteral("1"),
                YamlLiteral("2")
            )
        )

        val merge = YamlList(
            listOf(
                YamlLiteral("2"),
                YamlLiteral("3"),
                YamlLiteral("4")
            )
        )

        val expected = YamlList(
            listOf(
                YamlLiteral("1"),
                YamlLiteral("2"),
                YamlLiteral("3"),
                YamlLiteral("4")
            )
        )

        val options = YamlMergeOptions(
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
        val sut = YamlList(
            listOf(
                YamlLiteral("1"),
                YamlLiteral("2")
            )
        )

        val merge = YamlList(
            listOf(
                YamlLiteral("2"),
            )
        )

        val expected = YamlList(
            listOf(
                YamlLiteral("2"),
                YamlLiteral("2"),
            )
        )

        val options = YamlMergeOptions(
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
        val sut = YamlMap(
            mapOf(
                "a" to YamlLiteral("1")
            )
        )

        val merge = YamlMap(
            mapOf(
                "a" to YamlNull
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
        val sut = YamlMap(
            mapOf(
                "Property1" to YamlLiteral("1"),
            )
        )

        val merge = YamlMap(
            mapOf(
                "Property2" to YamlLiteral("2")
            )
        )

        val expected = YamlMap(
            mapOf(
                "Property1" to YamlLiteral("1"),
                "Property2" to YamlLiteral("2")
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
        val sut = YamlMap(
            mapOf(
                "Property1" to YamlMap(
                    mapOf(
                        "SubProperty1" to YamlLiteral("1")
                    )
                ),
            )
        )

        val merge = YamlMap(
            mapOf(
                "Property1" to YamlMap(
                    mapOf(
                        "SubProperty2" to YamlLiteral("2")
                    )
                ),
            )
        )

        val expected = YamlMap(
            mapOf(
                "Property1" to YamlMap(
                    mapOf(
                        "SubProperty1" to YamlLiteral("1"),
                        "SubProperty2" to YamlLiteral("2")
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
        val sut = YamlList(
            listOf(
                YamlMap(
                    mapOf(
                        "Property1" to YamlLiteral("1")
                    )
                ),
                YamlMap(
                    mapOf(
                        "Property1" to YamlLiteral("1")
                    )
                ),
            )
        )

        val expected = YamlMap(
            mapOf(
                "Property1" to YamlMap(
                    mapOf(
                        "SubProperty1" to YamlLiteral("1")
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
        val sut = YamlMap(
            mapOf(
                "Property1" to YamlMap(
                    mapOf(
                        "SubProperty1" to YamlLiteral("1")
                    )
                ),
            )
        )

        val merge = YamlMap(
            mapOf(
                "Property1" to YamlMap(
                    mapOf(
                        "SubProperty2" to YamlLiteral("2")
                    )
                ),
                "Property2" to YamlLiteral("2")
            )
        )

        val expected = YamlMap(
            mapOf(
                "Property1" to YamlMap(
                    mapOf(
                        "SubProperty1" to YamlLiteral("1"),
                        "SubProperty2" to YamlLiteral("2")
                    )
                ),
                "Property2" to YamlLiteral("2")
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
        val sut = YamlMap(
            mapOf(
                "Array1" to YamlList(
                    listOf(
                        YamlMap(
                            mapOf(
                                "Property1" to YamlMap(
                                    mapOf(
                                        "Property1" to YamlLiteral("1"),
                                        "Property2" to YamlLiteral("2"),
                                        "Property3" to YamlLiteral("3"),
                                        "Property4" to YamlLiteral("4"),
                                        "Property5" to YamlNull,
                                    )
                                )
                            )
                        ),
                        YamlMap(emptyMap()),
                        YamlLiteral("3"),
                        YamlNull,
                        YamlLiteral("5"),
                        YamlNull
                    )
                ),
            )
        )

        val merge = YamlMap(
            mapOf(
                "Array1" to YamlList(
                    listOf(
                        YamlMap(
                            mapOf(
                                "Property1" to YamlMap(
                                    mapOf(
                                        "Property1" to YamlNull,
                                        "Property2" to YamlLiteral("3"),
                                        "Property3" to YamlMap(emptyMap()),
                                        "Property5" to YamlNull,
                                    )
                                )
                            )
                        ),
                        YamlNull,
                        YamlNull,
                        YamlLiteral("4"),
                        YamlLiteral("5.1"),
                        YamlNull,
                        YamlMap(
                            mapOf(
                                "Property1" to YamlLiteral("1")
                            )
                        )
                    )
                ),
            )
        )

        val expected = YamlMap(
            mapOf(
                "Array1" to YamlList(
                    listOf(
                        YamlMap(
                            mapOf(
                                "Property1" to YamlMap(
                                    mapOf(
                                        "Property1" to YamlLiteral("1"),
                                        "Property2" to YamlLiteral("3"),
                                        "Property3" to YamlMap(emptyMap()),
                                        "Property4" to YamlLiteral("4"),
                                        "Property5" to YamlNull,
                                    )
                                )
                            )
                        ),
                        YamlMap(emptyMap()),
                        YamlLiteral("3"),
                        YamlLiteral("4"),
                        YamlLiteral("5.1"),
                        YamlNull,
                        YamlMap(
                            mapOf(
                                "Property1" to YamlLiteral("1")
                            )
                        )
                    )
                ),
            )
        )

        val options = YamlMergeOptions(
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
        val sut = YamlMap(
            mapOf(
                "Array1" to YamlList(
                    listOf(
                        YamlMap(
                            mapOf(
                                "Property1" to YamlLiteral("1")
                            )
                        ),
                        YamlMap(
                            mapOf(
                                "Property1" to YamlLiteral("1")
                            )
                        )
                    )
                ),
            )
        )

        val merge = YamlMap(
            mapOf(
                "Array1" to YamlList(
                    listOf(
                        YamlMap(
                            mapOf(
                                "Property1" to YamlLiteral("1")
                            )
                        ),
                        YamlMap(
                            mapOf(
                                "Property2" to YamlLiteral("2")
                            )
                        ),
                        YamlMap(
                            mapOf(
                                "Property3" to YamlLiteral("3")
                            )
                        )
                    )
                ),
            )
        )

        val expected = YamlMap(
            mapOf(
                "Array1" to YamlList(
                    listOf(
                        YamlMap(
                            mapOf(
                                "Property1" to YamlLiteral("1")
                            )
                        ),
                        YamlMap(
                            mapOf(
                                "Property1" to YamlLiteral("1")
                            )
                        ),
                        YamlMap(
                            mapOf(
                                "Property1" to YamlLiteral("1")
                            )
                        ),
                        YamlMap(
                            mapOf(
                                "Property2" to YamlLiteral("2")
                            )
                        ),
                        YamlMap(
                            mapOf(
                                "Property3" to YamlLiteral("3")
                            )
                        )
                    )
                ),
            )
        )

        val options = YamlMergeOptions(
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
        val sut = YamlList(
            listOf(
                YamlLiteral("true"),
                YamlNull,
                YamlMap(
                    mapOf(
                        "Property1" to YamlLiteral("1")
                    ),
                ),
                YamlList(
                    listOf(
                        YamlLiteral("1")
                    )
                ),
                YamlMap(
                    mapOf(
                        "Property1" to YamlLiteral("1")
                    ),
                ),
                YamlLiteral("1"),
                YamlList(
                    listOf(
                        YamlLiteral("1")
                    )
                ),
            )
        )

        val merge = YamlList(
            listOf(
                YamlLiteral("1"),
                YamlLiteral("5"),
                YamlList(
                    listOf(
                        YamlLiteral("1")
                    )
                ),
                YamlMap(
                    mapOf(
                        "Property1" to YamlLiteral("1")
                    ),
                ),
                YamlLiteral("true"),
                YamlMap(
                    mapOf(
                        "Property1" to YamlLiteral("1")
                    ),
                ),
                YamlNull
            )
        )

        val expected = YamlList(
            listOf(
                YamlLiteral("1"),
                YamlLiteral("5"),
                YamlList(
                    listOf(
                        YamlLiteral("1")
                    )
                ),
                YamlMap(
                    mapOf(
                        "Property1" to YamlLiteral("1")
                    ),
                ),
                YamlLiteral("true"),
                YamlMap(
                    mapOf(
                        "Property1" to YamlLiteral("1")
                    ),
                ),
                YamlList(
                    listOf(
                        YamlLiteral("1")
                    )
                ),
            )
        )

        val options = YamlMergeOptions(
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
        val sut = YamlMap(
            mapOf(
                "Property1" to YamlList(
                    listOf(
                        YamlLiteral("1")
                    )
                ),
                "Property2" to YamlList(
                    listOf(
                        YamlLiteral("1")
                    )
                ),
                "Property3" to YamlLiteral("true"),
                "Property4" to YamlLiteral("true")
            )
        )

        val merge = YamlMap(
            mapOf(
                "Property1" to YamlMap(
                    mapOf(
                        "Nested" to YamlLiteral("true")
                    )
                ),
                "Property2" to YamlLiteral("true"),
                "Property3" to YamlList(
                    listOf(
                        YamlLiteral("1")
                    )
                ),
                "Property4" to YamlNull
            )
        )

        val expected = YamlMap(
            mapOf(
                "Property1" to YamlMap(
                    mapOf(
                        "Nested" to YamlLiteral("true")
                    )
                ),
                "Property2" to YamlLiteral("true"),
                "Property3" to YamlList(
                    listOf(
                        YamlLiteral("1")
                    )
                ),
                "Property4" to YamlLiteral("true")
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
        val sut = YamlMap(
            mapOf(
                "Array1" to YamlList(
                    listOf(
                        YamlLiteral("1"),
                        YamlLiteral("2"),
                        YamlLiteral("3")
                    )
                ),
            )
        )

        val expected = YamlMap(
            mapOf(
                "Array1" to YamlList(
                    listOf(
                        YamlLiteral("4"),
                        YamlLiteral("5"),
                    )
                ),
            )
        )

        val options = YamlMergeOptions(
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
        val sut = YamlList(
            listOf(
                YamlLiteral("1"),
                YamlLiteral("2"),
                YamlLiteral("3")
            )
        )

        val expected = YamlList(
            listOf(
                YamlLiteral("4"),
                YamlLiteral("5"),
            )
        )

        val options = YamlMergeOptions(
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
        val sut = YamlMap(
            mapOf(
                "Array1" to YamlList(
                    listOf(
                        YamlMap(
                            mapOf(
                                "Property1" to YamlLiteral("1")
                            )
                        ),
                        YamlMap(
                            mapOf(
                                "Property1" to YamlLiteral("1")
                            )
                        ),
                    )
                ),
            )
        )

        val merge = YamlMap(
            mapOf(
                "Array1" to YamlList(
                    listOf(
                        YamlMap(
                            mapOf(
                                "Property1" to YamlLiteral("1")
                            )
                        ),
                        YamlMap(
                            mapOf(
                                "Property2" to YamlLiteral("2")
                            )
                        ),
                        YamlMap(
                            mapOf(
                                "Property3" to YamlLiteral("3")
                            )
                        )
                    )
                ),
            )
        )

        val expected = YamlMap(
            mapOf(
                "Array1" to YamlList(
                    listOf(
                        YamlMap(
                            mapOf(
                                "Property1" to YamlLiteral("1")
                            )
                        ),
                        YamlMap(
                            mapOf(
                                "Property2" to YamlLiteral("2")
                            )
                        ),
                        YamlMap(
                            mapOf(
                                "Property3" to YamlLiteral("3")
                            )
                        )
                    )
                ),
            )
        )

        val options = YamlMergeOptions(
            arrayHandling = ArrayMergeHandling.UNION
        )

        // Act
        val actual = sut.merge(merge, options)

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun mergeNull_Ignore() {
        // Arrange
        val sut = YamlMap(
            mapOf(
                "Property1" to YamlLiteral("value"),
                "Property2" to YamlMap(emptyMap()),
                "Property3" to YamlNull,
                "Property4" to YamlNull,
                "Property5" to YamlList(emptyList())
            )
        )

        val merge = YamlMap(
            mapOf(
                "Property1" to YamlNull,
                "Property2" to YamlNull,
                "Property3" to YamlNull,
                "Property4" to YamlNull,
                "Property5" to YamlNull
            )
        )

        val expected = YamlMap(
            mapOf(
                "Property1" to YamlLiteral("value"),
                "Property2" to YamlMap(emptyMap()),
                "Property3" to YamlNull,
                "Property4" to YamlNull,
                "Property5" to YamlList(emptyList())
            )
        )

        val options = YamlMergeOptions(
            nullHandling = NullMergeHandling.IGNORE
        )

        // Act
        val actual = sut.merge(merge, options)

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun mergeNull_Merge() {
        // Arrange
        val sut = YamlMap(
            mapOf(
                "Property1" to YamlLiteral("value"),
                "Property2" to YamlMap(emptyMap()),
                "Property3" to YamlNull,
                "Property4" to YamlNull,
                "Property5" to YamlList(emptyList())
            )
        )

        val expected = YamlMap(
            mapOf(
                "Property1" to YamlNull,
                "Property2" to YamlNull,
                "Property3" to YamlNull,
                "Property4" to YamlNull,
                "Property5" to YamlNull
            )
        )

        val options = YamlMergeOptions(
            nullHandling = NullMergeHandling.MERGE
        )

        // Act
        val actual = sut.merge(expected, options)

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun mergeNull_Merge_WithArray() {
        // Arrange
        val sut = YamlMap(
            mapOf(
                "Bar" to YamlList(
                    listOf(
                        YamlLiteral("a"),
                        YamlLiteral("b"),
                        YamlLiteral("c")
                    )
                )
            )
        )

        val expected = YamlMap(
            mapOf(
                "Bar" to YamlNull
            )
        )

        val options = YamlMergeOptions(
            nullHandling = NullMergeHandling.MERGE
        )

        // Act
        val actual = sut.merge(expected, options)

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun mergeNull_Merge_WithObject() {
        // Arrange
        val sut = YamlMap(
            mapOf(
                "Bar" to YamlMap(emptyMap())
            )
        )

        val expected = YamlMap(
            mapOf(
                "Bar" to YamlNull
            )
        )

        val options = YamlMergeOptions(
            nullHandling = NullMergeHandling.MERGE
        )

        // Act
        val actual = sut.merge(expected, options)

        // Assert
        assertEquals(expected, actual)
    }
}