package instrumented

import android.content.pm.ProviderInfo
import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bselzer.ktx.client.connectivity.Connectivity
import com.bselzer.ktx.client.connectivity.ConnectivityConfiguration
import io.ktor.http.*
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
     * Verifies that a connection is able to be established.
     */
    @Test
    @Config(sdk = [Build.VERSION_CODES.M])
    fun isActive_AtLeastMarshmallow() {
        // Act
        val result = runBlocking { connectivity.isActive() }

        // Assert
        assertTrue(result)
    }

    /**
     * Verifies that a connection is able to be established.
     */
    @Test
    @Config(sdk = [Build.VERSION_CODES.LOLLIPOP_MR1])
    fun isActive_BelowMarshmallow() {
        // Act
        val result = runBlocking { connectivity.isActive() }

        // Assert
        assertTrue(result)
    }

    /**
     * Verifies that when a connection is not able to be established then the connection is not considered active.
     */
    @Test
    @Config(sdk = [Build.VERSION_CODES.M])
    fun isActive_WithBadUrl_Fails() {
        // Arrange
        connectivity.apply {
            configuration = ConnectivityConfiguration(url = Url("Foo"))
            onCreate()
        }

        // Act
        val result = runBlocking { connectivity.isActive() }

        // Assert
        assertFalse(result)
    }
}