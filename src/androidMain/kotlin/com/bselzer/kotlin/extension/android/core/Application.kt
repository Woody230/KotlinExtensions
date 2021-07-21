package com.bselzer.kotlin.extension.android.core

import android.app.Application
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.*
import androidx.core.content.getSystemService

/**
 * @return whether or not the device is currently connected to the internet
 */
fun Application.hasInternet(): Boolean
{
    val connectivity = this.getSystemService<ConnectivityManager>() ?: return false
    val network = connectivity.activeNetwork ?: return false
    val capabilities = connectivity.getNetworkCapabilities(network) ?: return false

    return when
    {
        capabilities.hasTransport(TRANSPORT_WIFI) -> true
        capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
        capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
        else -> false
    }
}