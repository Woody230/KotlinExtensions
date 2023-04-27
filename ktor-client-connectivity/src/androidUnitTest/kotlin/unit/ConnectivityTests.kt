package unit

import android.content.pm.ProviderInfo
import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bselzer.ktx.client.connectivity.Connectivity
import com.bselzer.ktx.client.connectivity.ConnectivityConfiguration
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.*
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.annotation.Config
import kotlin.test.assertFalse
import kotlin.test.assertTrue


@RunWith(AndroidJUnit4::class)
class ConnectivityTests {
    private lateinit var connectivity: Connectivity

    @Before
    fun setup() {
        val info = ProviderInfo().apply {
            authority = "authority"
        }

        connectivity = Robolectric
            .buildContentProvider(Connectivity::class.java)
            .create(info)
            .get()
    }

    /**
     * Verifies that when status code validation is off then a bad status code is ignored.
     */
    @Test
    @Config(sdk = [Build.VERSION_CODES.M])
    fun isActive_WithoutStatusCodeValidation_IgnoresStatus() {
        // Arrange
        val client = HttpClient(MockEngine) {
            engine {
                addHandler { respondBadRequest() }
            }
        }

        val configuration = ConnectivityConfiguration(statusValidation = false)

        connectivity.apply {
            this.configuration = configuration
            this.httpClient = client
            onCreate()
        }

        // Act
        val result = runBlocking { connectivity.isActive() }

        // Assert
        assertTrue(result)
    }

    /**
     * Verifies that when status code validation is enabled then a bad status code fails.
     */
    @Test
    @Config(sdk = [Build.VERSION_CODES.M])
    fun isActive_WithStatusCodeValidation_RequiresSuccess() {
        // Arrange
        val client = HttpClient(MockEngine) {
            engine {
                addHandler { respondBadRequest() }
            }
        }

        val configuration = ConnectivityConfiguration(statusValidation = true)

        connectivity.apply {
            this.configuration = configuration
            this.httpClient = client
            onCreate()
        }

        // Act
        val result = runBlocking { connectivity.isActive() }

        // Assert
        assertFalse(result)
    }

    /**
     * Verifies that when the request takes too much time, then there is no active connection.
     */
    @Test
    @Config(sdk = [Build.VERSION_CODES.M])
    fun isActive_FailsOnTimeout() {
        // Arrange
        val client = HttpClient(MockEngine) {
            engine {
                addHandler { awaitCancellation() }
            }
        }

        val configuration = ConnectivityConfiguration(
            timeout = HttpTimeout.HttpTimeoutCapabilityConfiguration(
                requestTimeoutMillis = 1
            )
        )

        connectivity.apply {
            this.configuration = configuration
            this.httpClient = client
            onCreate()
        }

        // Act
        val result = runBlocking { connectivity.isActive() }

        // Assert
        assertFalse(result)
    }
}