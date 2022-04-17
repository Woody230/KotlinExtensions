package com.bselzer.ktx.ktor.client.connectivity

import android.content.Context
import android.net.NetworkCapabilities
import android.os.Build
import io.ktor.client.*

actual class Connectivity actual constructor(
    configuration: ConnectivityConfiguration,
    httpClient: HttpClient
) : ConnectivityManager(configuration, httpClient) {
    /**
     * The context for retrieving the [android.net.ConnectivityManager].
     */
    lateinit var context: Context

    /**
     * The connectivity service associated with the context.
     */
    private val connectivityManager: android.net.ConnectivityManager
        get() = context.getSystemService(Context.CONNECTIVITY_SERVICE) as android.net.ConnectivityManager

    /**
     * The network capabilities associated with the active network for Marshmallow and up.
     */
    private val networkCapabilities: NetworkCapabilities?
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        } else {
            connectivityManager.allNetworks.firstNotNullOfOrNull { network -> connectivityManager.getNetworkCapabilities(network) }
        }

    override suspend fun isActive(): Boolean {
        // If the validated capability exists then a connection is known to have been established.
        if (hasCapability(CapabilityType.VALIDATED)) {
            return true
        }

        // Perform the head request.
        return super.isActive()
    }

    /**
     * Whether the [networkCapabilities] has the given [type] of capability.
     */
    fun hasCapability(type: CapabilityType): Boolean = networkCapabilities?.hasCapability(type.code) == true

    /**
     * Whether the [networkCapabilities] has the given [type] of transport.
     */
    fun hasTransport(type: TransportType): Boolean = networkCapabilities?.hasTransport(type.code) == true
}