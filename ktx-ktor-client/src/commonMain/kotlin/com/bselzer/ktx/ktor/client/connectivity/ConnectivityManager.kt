package com.bselzer.ktx.ktor.client.connectivity

interface ConnectivityManager {
    /**
     * Whether connectivity is able to be actively established.
     */
    suspend fun isActive(): Boolean
}