package com.bselzer.ktx.client.connectivity

import io.ktor.client.plugins.*
import io.ktor.http.*

data class ConnectivityConfiguration(
    /**
     * The url to connect to.
     */
    val url: Url = Url("http://clients3.google.com/generate_204"),

    /**
     * Whether to validate the status code of the connection.
     */
    val statusValidation: Boolean = false,

    /**
     * The request timeout.
     */
    val timeout: HttpTimeoutConfig = HttpTimeoutConfig(
        requestTimeoutMillis = defaultTimeout,
        connectTimeoutMillis = defaultTimeout,
        socketTimeoutMillis = defaultTimeout
    )
) {
    companion object {
        private const val defaultTimeout: Long = 3000
    }
}