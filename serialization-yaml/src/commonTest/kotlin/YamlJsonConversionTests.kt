import com.bselzer.ktx.serialization.context.toJsonElement
import com.bselzer.ktx.serialization.context.toYamlElement
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
    fun jsonToYamlToJson() {
        // Arrange
        val json = JsonObject(
            mapOf(
                "Property1" to JsonPrimitive("value"),
                "Property2" to JsonObject(
                    mapOf(
                        "SubProperty1" to JsonPrimitive(1),
                        "SubProperty2" to JsonArray(listOf(JsonPrimitive(2.0), JsonPrimitive(Long.MAX_VALUE)))
                    )
                ),
                "Property3" to JsonNull,
                "Property4" to JsonNull,
                "Property5" to JsonArray(listOf(JsonPrimitive(5), JsonPrimitive(6)))
            )
        )

        val expectedYaml = YamlMap(
            mapOf(
                "Property1" to YamlLiteral("value"),
                "Property2" to YamlMap(
                    mapOf(
                        "SubProperty1" to YamlLiteral("1"),
                        "SubProperty2" to YamlList(listOf(YamlLiteral("2.0"), YamlLiteral("9223372036854775807")))
                    )
                ),
                "Property3" to YamlNull,
                "Property4" to YamlNull,
                "Property5" to YamlList(listOf(YamlLiteral("5"), YamlLiteral("6")))
            )
        )

        // Act
        val jsonToYaml = json.toYamlElement()
        val yamlToJson = jsonToYaml.toJsonElement()

        // Assert
        assertEquals(expectedYaml, jsonToYaml)
        assertEquals(json, yamlToJson)
    }
}