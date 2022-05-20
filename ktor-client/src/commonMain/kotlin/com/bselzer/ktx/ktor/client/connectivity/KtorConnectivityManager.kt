package com.bselzer.ktx.ktor.client.connectivity

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*

class KtorConnectivityManager(
    private val configuration: ConnectivityConfiguration = ConnectivityConfiguration(),
    httpClient: HttpClient = HttpClient()
) : ConnectivityManager {
    /**
     * The HTTP client to make requests with.
     */
    private val httpClient: HttpClient = httpClient.config { install(HttpTimeout) }

    /**
     * Whether connectivity is able to be actively established.
     */
    override suspend fun isActive(): Boolean = try {
        val response = httpClient.head(configuration.url) {
            setCapability(HttpTimeout, configuration.timeout)
        }

        // If status code validation is not enabled, then it is assumed that just making the connection within the timeout period is enough.
        if (configuration.statusValidation) response.status.isSuccess() else true
    } catch (ex: Exception) {
        false
    }
}