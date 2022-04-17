package com.bselzer.ktx.ktor.client.instrumented

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.bselzer.ktx.ktor.client.connectivity.Connectivity
import com.bselzer.ktx.ktor.client.connectivity.ConnectivityConfiguration
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@RunWith(AndroidJUnit4::class)
class ConnectivityTests {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    /**
     * Verifies that a connection is able to be established.
     */
    @Test
    @Config(sdk = [Build.VERSION_CODES.M])
    fun isActive_AtLeastMarshmallow() {
        // Arrange
        val connectivity = Connectivity().apply {
            this.context = this@ConnectivityTests.context
        }

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
        // Arrange
        val connectivity = Connectivity().apply {
            this.context = this@ConnectivityTests.context
        }

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
        val connectivity = Connectivity(configuration = ConnectivityConfiguration(url = Url("Foo"))).apply {
            this.context = this@ConnectivityTests.context
        }

        // Act
        val result = runBlocking { connectivity.isActive() }

        // Assert
        assertFalse(result)
    }
}