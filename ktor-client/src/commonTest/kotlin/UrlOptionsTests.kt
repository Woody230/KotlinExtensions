import com.bselzer.ktx.client.options.UrlOptions
import io.ktor.http.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class UrlOptionsTests {
    @Test
    fun merge_WithDefault() {
        // Arrange
        val default = UrlOptions(
            protocol = URLProtocol.HTTPS,
            host = "api.guildwars2.com",
            port = 80,
            pathSegments = listOf("v2"),
            pathParameters = mapOf("continentId" to "1"),
            queryParameters = mapOf("access_token" to "my_key"),
            fragment = "defaultFragment",
            user = "defaultUser",
            password = "defaultPassword",
            trailingQuery = false,
        )

        val intermediary = UrlOptions(
            protocol = URLProtocol.HTTP,
            host = "intermediaryHost",
            port = 99,
            pathSegments = listOf("continents", "{continentId}", "floors", "{floorId}", "regions", "{regionId}"),
            pathParameters = mapOf("floorId" to "2", "regionId" to "3"),
            queryParameters = mapOf("lang" to "en"),
            fragment = "intermediaryFragment",
            user = "intermediaryUser",
            password = "intermediaryPassword",
            trailingQuery = true,
        )

        val preferred = UrlOptions(
            pathSegments = listOf("maps", "{mapId}", "tasks"),
            pathParameters = mapOf("mapId" to "4"),
            queryParameters = mapOf("page_size" to "25")
        )

        // Act
        val merged = default.merge(intermediary, preferred)
        val url = merged.url

        // Assert
        val pathParameters = mapOf("continentId" to "1", "floorId" to "2", "regionId" to "3", "mapId" to "4")
        val queryParameters = mapOf("access_token" to "my_key", "lang" to "en", "page_size" to "25")

        assertEquals(pathParameters, merged.pathParameters)
        assertEquals(queryParameters, merged.queryParameters)
        assertEquals(url.trailingQuery, true)
        assertEquals(
            "http://intermediaryUser:intermediaryPassword@intermediaryHost:99/v2/continents/1/floors/2/regions/3/maps/4/tasks?access_token=my_key&lang=en&page_size=25#intermediaryFragment",
            url.toString()
        )
    }

    @Test
    fun merge_NoDefault() {
        // Arrange
        val default = UrlOptions.Default

        val intermediary = UrlOptions(
            protocol = URLProtocol.HTTP,
            host = "intermediaryHost",
            port = 99,
            pathSegments = listOf("continents", "{continentId}", "floors", "{floorId}", "regions", "{regionId}"),
            pathParameters = mapOf("continentId" to "1", "floorId" to "2", "regionId" to "3"),
            queryParameters = mapOf("lang" to "en"),
            fragment = "intermediaryFragment",
            user = "intermediaryUser",
            password = "intermediaryPassword",

        )

        // Act
        val merged = default.merge(intermediary)
        val url = merged.url

        // Assert
        val pathParameters = mapOf("continentId" to "1", "floorId" to "2", "regionId" to "3")
        val queryParameters = mapOf("lang" to "en")

        assertEquals(pathParameters, merged.pathParameters)
        assertEquals(queryParameters, merged.queryParameters)
        assertEquals(url.trailingQuery, false)
        assertEquals(
            "http://intermediaryUser:intermediaryPassword@intermediaryHost:99/continents/1/floors/2/regions/3?lang=en#intermediaryFragment",
            merged.url.toString()
        )
    }

    @Test
    fun defaultUrl() {
        assertEquals(UrlOptions().url, Url(""))
    }

    @Test
    fun missingPathParameter_ThrowsException() {
        // Arrange
        val options = UrlOptions(
            pathSegments = listOf("continents", "{continentId}", "floors", "{floorId}", "regions", "{regionId}"),
            pathParameters = mapOf("floorId" to "2"),
        )

        // Act
        val exception = runCatching { options.url }.exceptionOrNull()

        // Assert
        assertNotNull(exception)
    }
}