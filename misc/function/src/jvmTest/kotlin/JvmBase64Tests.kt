import com.bselzer.ktx.function.collection.encodeBase64ToString
import com.bselzer.ktx.function.objects.decodeBase64ToByteArray
import org.junit.Assert.assertArrayEquals
import java.util.Base64
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * @see <a href="https://gist.github.com/MarkusKramer/4db02c9983c76efc6aa56cf0bdc75a5b">Markus Kramer's gist</a>
 */
class JvmBase64Tests {
    @Test
    fun encodeDecode() {
        val encoder = Base64.getEncoder()
        (0..100).forEach { i ->
            val input = Random.nextBytes(i * 10)
            val javaEncoded = encoder.encodeToString(input)
            val kotlinEncoded = input.encodeBase64ToString()
            assertEquals(javaEncoded, kotlinEncoded)
            assertArrayEquals(input, kotlinEncoded.decodeBase64ToByteArray())
        }
    }
}