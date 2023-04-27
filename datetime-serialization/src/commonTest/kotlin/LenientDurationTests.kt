import com.bselzer.ktx.serialization.serializer.DateTimeModules
import com.bselzer.ktx.serialization.serializer.LenientDurationSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

class LenientDurationTests {
    private val json = Json {
        serializersModule = DateTimeModules.ALL
    }

    /**
     * Verifies that because there is a default Duration serializer, contextual serialization provided by the module is not used.
     * Consequently, the stricter default Duration serializer throws an error.
     */
    @Test
    fun deserialize_WithContext_ThrowsError() {
        val string = "\"5h 15m\""
        val exception = runCatching { json.decodeFromString<Duration>(string) }.exceptionOrNull()
        assertNotNull(exception)
    }

    @Test
    fun deserialize_WithSerializer_Decodes() {
        val string = "\"5h 15m\""
        val duration = Json.decodeFromString(LenientDurationSerializer(), string)
        assertEquals(5.hours + 15.minutes, duration)
    }
}