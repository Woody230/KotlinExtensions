import com.bselzer.ktx.client.options.DefaultUrlOptions
import com.bselzer.ktx.client.options.UrlOptions
import io.ktor.http.*
import kotlin.test.*

class UrlOptionsTests {
    @Test
    fun merge_WithDefault() {
        // Arrange
        val default = DefaultUrlOptions(
            protocol = URLProtocol.HTTPS,
            host = "api.guildwars2.com",
            port = 80,
            pathSegments = listOf("v2"),
            pathParameters = mapOf("continentId" to "1"),
            queryParameters = mapOf("access_token" to "my_key"),
            fragment = "defaultFragment",
            user = "defaultUser",
            password = "defaultPassword",
            trailingQuery = true,
        )

        val intermediary = DefaultUrlOptions(
            protocol = URLProtocol.HTTP,
            host = "intermediaryHost",
            port = 99,
            pathSegments = listOf("continents", "{continentId}", "floors", "{floorId}", "regions", "{regionId}"),
            pathParameters = mapOf("floorId" to "2", "regionId" to "3"),
            queryParameters = mapOf("lang" to "en"),
            fragment = "intermediaryFragment",
            user = "intermediaryUser",
            password = "intermediaryPassword",
            trailingQuery = false,
        )

        val preferred = DefaultUrlOptions(
            pathSegments = listOf("maps", "{mapId}", "tasks"),
            pathParameters = mapOf("mapId" to "4"),
            queryParameters = mapOf("page_size" to "25")
        )

        // Act
        val order = listOf(default, intermediary, preferred)
        val merged = order.fold<UrlOptions, UrlOptions>(UrlOptions) { current, next -> current.merge(next) }

        // Assert
        val pathSegments = listOf("v2", "continents", "{continentId}", "floors", "{floorId}", "regions", "{regionId}", "maps", "{mapId}", "tasks")
        val pathParameters = mapOf("continentId" to "1", "floorId" to "2", "regionId" to "3", "mapId" to "4")
        val queryParameters = mapOf("access_token" to "my_key", "lang" to "en", "page_size" to "25")

        assertEquals(URLProtocol.HTTP, merged.protocol)
        assertEquals("intermediaryHost", merged.host)
        assertEquals(99, merged.port)
        assertContentEquals(pathSegments, merged.pathSegments)
        assertEquals(pathParameters, merged.pathParameters)
        assertEquals(queryParameters, merged.queryParameters)
        assertEquals("intermediaryFragment", merged.fragment)
        assertEquals("intermediaryUser", merged.user)
        assertEquals("intermediaryPassword", merged.password)
        assertEquals(false, merged.trailingQuery)
        assertEquals(
            "http://intermediaryUser:intermediaryPassword@intermediaryHost:99/v2/continents/1/floors/2/regions/3/maps/4/tasks?access_token=my_key&lang=en&page_size=25#intermediaryFragment",
            merged.url.toString()
        )
    }

    @Test
    fun merge_NoDefault() {
        // Arrange
        val default = UrlOptions

        val intermediary = DefaultUrlOptions(
            protocol = URLProtocol.HTTP,
            host = "intermediaryHost",
            port = 99,
            pathSegments = listOf("continents", "{continentId}", "floors", "{floorId}", "regions", "{regionId}"),
            pathParameters = mapOf("continentId" to "1", "floorId" to "2", "regionId" to "3"),
            queryParameters = mapOf("lang" to "en"),
            fragment = "intermediaryFragment",
            user = "intermediaryUser",
            password = "intermediaryPassword",
            trailingQuery = false,
        )

        // Act
        val merged = default.merge(intermediary)
        val url = runCatching { merged.url }

        // Assert
        val pathSegments = listOf("continents", "{continentId}", "floors", "{floorId}", "regions", "{regionId}")
        val pathParameters = mapOf("continentId" to "1", "floorId" to "2", "regionId" to "3")
        val queryParameters = mapOf("lang" to "en")

        assertEquals(URLProtocol.HTTP, merged.protocol)
        assertEquals("intermediaryHost", merged.host)
        assertEquals(99, merged.port)
        assertContentEquals(pathSegments, merged.pathSegments)
        assertEquals(pathParameters, merged.pathParameters)
        assertEquals(queryParameters, merged.queryParameters)
        assertEquals("intermediaryFragment", merged.fragment)
        assertEquals("intermediaryUser", merged.user)
        assertEquals("intermediaryPassword", merged.password)
        assertEquals(false, merged.trailingQuery)
        assertNull(url.exceptionOrNull())
    }

    @Test
    fun missingPathParameter_ThrowsException() {
        // Arrange
        val options = DefaultUrlOptions(
            pathSegments = listOf("continents", "{continentId}", "floors", "{floorId}", "regions", "{regionId}"),
            pathParameters = mapOf("floorId" to "2"),
        )

        // Act
        val exception = runCatching { options.url }.exceptionOrNull()

        // Assert
        assertNotNull(exception)
    }
}