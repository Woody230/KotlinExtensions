package com.bselzer.ktx.client.connectivity

import android.content.Context
import android.net.NetworkCapabilities
import android.os.Build
import com.bselzer.ktx.intent.AndroidIntent
import io.ktor.client.*

actual class Connectivity actual constructor(
    internal var configuration: ConnectivityConfiguration,
    internal var httpClient: HttpClient
) : AndroidIntent(), ConnectivityManager {
    private lateinit var ktor: KtorConnectivityManager

    override fun onCreate(): Boolean {
        ktor = KtorConnectivityManager(configuration, httpClient)
        return super.onCreate()
    }

    /**
     * The connectivity service associated with the context.
     */
    private val connectivityManager: android.net.ConnectivityManager
        get() = requireApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE) as android.net.ConnectivityManager

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
        return ktor.isActive()
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