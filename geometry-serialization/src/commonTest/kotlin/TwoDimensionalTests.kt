import com.bselzer.ktx.geometry.dimension.bi.Dimension2D
import com.bselzer.ktx.geometry.dimension.bi.polygon.Digon
import com.bselzer.ktx.geometry.dimension.bi.polygon.Quadrilateral
import com.bselzer.ktx.geometry.dimension.bi.position.Point2D
import com.bselzer.ktx.serialization.serializer.Modules
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class TwoDimensionalTests {
    private val json = Json {
        serializersModule = Modules.TWO_DIMENSIONAL
    }


    @Test
    fun deserializeDigon() {
        val string = "[ [5.29, 16.31], [7.32, 249] ]"
        val digon = json.decodeFromString<Digon>(string)
        assertEquals(Digon(Point2D(5.29, 16.31), Point2D(7.32, 249.0)), digon)
    }

    @Test
    fun deserializeDimension2D() {
        val string = "[ 5.29, 16.31 ]"
        val dimension = json.decodeFromString<Dimension2D>(string)
        assertEquals(Dimension2D(5.29, 16.31), dimension)
    }

    @Test
    fun deserializePoint2D() {
        val string = "[ 5.29, 16.31 ]"
        val point = json.decodeFromString<Point2D>(string)
        assertEquals(Point2D(5.29, 16.31), point)
    }

    @Test
    fun deserializeQuadrilateral() {
        val string = "[ [5.29, 16.31], [7.32, 249], [9.14, 1], [-62, 91.2] ]"
        val quadrilateral = json.decodeFromString<Quadrilateral>(string)
        val expected = Quadrilateral(Point2D(5.29, 16.31), Point2D(7.32, 249.0), Point2D(9.14, 1.0), Point2D(-62.0, 91.2))
        assertEquals(expected, quadrilateral)
    }
}