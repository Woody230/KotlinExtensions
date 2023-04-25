package instrumented

import com.bselzer.ktx.client.connectivity.Connectivity
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertTrue

class ConnectivityTests {
    /**
     * Verifies that a connection is able to be established.
     */
    @Test
    fun isActive() {
        // Arrange
        val connectivity = Connectivity()

        // Act
        val result = runBlocking { connectivity.isActive() }

        // Assert
        assertTrue(result)
    }
}