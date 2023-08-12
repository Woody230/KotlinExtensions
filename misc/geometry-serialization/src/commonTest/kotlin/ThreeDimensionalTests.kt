import com.bselzer.ktx.geometry.dimension.tri.position.Point3D
import com.bselzer.ktx.serialization.serializer.GeometryModules
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class ThreeDimensionalTests {
    private val json = Json {
        serializersModule = GeometryModules.THREE_DIMENSIONAL
    }

    @Test
    fun deserializePoint3D() {
        val string = "[ 5.29, 16.31, 68 ]"
        val point = json.decodeFromString<Point3D>(string)
        assertEquals(Point3D(5.29, 16.31, 68.0), point)
    }
}