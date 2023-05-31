import com.bselzer.ktx.serialization.toJsonElement
import com.bselzer.ktx.serialization.toYamlElement
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import net.mamoe.yamlkt.YamlList
import net.mamoe.yamlkt.YamlLiteral
import net.mamoe.yamlkt.YamlMap
import net.mamoe.yamlkt.YamlNull
import kotlin.test.Test
import kotlin.test.assertEquals

class YamlJsonConversionTests {
    @Test
    fun jsonToYaml() {
        // Arrange
        val json = JsonObject(
            mapOf(
                "Property1" to JsonPrimitive("value"),
                "Property2" to JsonObject(
                    mapOf(
                        "SubProperty1" to JsonPrimitive(1),
                        "SubProperty2" to JsonArray(listOf(JsonPrimitive(2.5), JsonPrimitive(Long.MAX_VALUE)))
                    )
                ),
                "Property3" to JsonNull,
                "Property4" to JsonNull,
                "Property5" to JsonArray(listOf(JsonPrimitive(true), JsonPrimitive("92233720368547758071")))
            )
        )

        val expectedYaml = YamlMap(
            mapOf(
                "Property1" to YamlLiteral("value"),
                "Property2" to YamlMap(
                    mapOf(
                        "SubProperty1" to YamlLiteral("1"),
                        "SubProperty2" to YamlList(listOf(YamlLiteral("2.5"), YamlLiteral("9223372036854775807")))
                    )
                ),
                "Property3" to YamlNull,
                "Property4" to YamlNull,
                "Property5" to YamlList(listOf(YamlLiteral("true"), YamlLiteral("92233720368547758071")))
            )
        )

        // Act
        val jsonToYaml = json.toYamlElement()

        // Assert
        assertEquals(expectedYaml, jsonToYaml)
    }

    @Test
    fun yamlToJson() {
        // Arrange
        val yaml = YamlMap(
            mapOf(
                "Property1" to YamlLiteral("value"),
                "Property2" to YamlMap(
                    mapOf(
                        "SubProperty1" to YamlLiteral("1"),
                        "SubProperty2" to YamlList(listOf(YamlLiteral("2.5"), YamlLiteral("9223372036854775807")))
                    )
                ),
                "Property3" to YamlNull,
                "Property4" to YamlNull,
                "Property5" to YamlList(listOf(YamlLiteral("true"), YamlLiteral("92233720368547758071")))
            )
        )


        val expectedJson = JsonObject(
            mapOf(
                "Property1" to JsonPrimitive("value"),
                "Property2" to JsonObject(
                    mapOf(
                        "SubProperty1" to JsonPrimitive(1),
                        "SubProperty2" to JsonArray(listOf(JsonPrimitive(2.5), JsonPrimitive(Long.MAX_VALUE)))
                    )
                ),
                "Property3" to JsonNull,
                "Property4" to JsonNull,
                "Property5" to JsonArray(listOf(JsonPrimitive(true), JsonPrimitive(9.223372036854776E19)))
            )
        )

        // Act
        val yamlToJson = yaml.toJsonElement()

        // Assert
        assertEquals(expectedJson, yamlToJson)
    }
}