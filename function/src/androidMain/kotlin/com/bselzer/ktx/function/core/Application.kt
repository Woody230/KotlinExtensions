package com.bselzer.ktx.function.core

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.*
import androidx.core.content.getSystemService

/**
 * @return whether the device is currently connected to the internet
 */
fun Context.hasInternet(): Boolean {
    val connectivity = this.getSystemService<ConnectivityManager>() ?: return false
    val network = connectivity.activeNetwork ?: return false
    val capabilities = connectivity.getNetworkCapabilities(network) ?: return false

    return when {
        capabilities.hasTransport(TRANSPORT_WIFI) -> true
        capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
        capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
        else -> false
    }
}