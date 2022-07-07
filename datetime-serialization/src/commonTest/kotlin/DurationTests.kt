import com.bselzer.ktx.serialization.serializer.DateTimeModules
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

class DurationTests {
    private val json = Json {
        serializersModule = DateTimeModules.ALL
    }

    @Test
    fun deserialize() {
        val string = "\"5h 15m\""
        val duration = json.decodeFromString<Duration>(string)
        assertEquals(5.hours + 15.minutes, duration)
    }
}