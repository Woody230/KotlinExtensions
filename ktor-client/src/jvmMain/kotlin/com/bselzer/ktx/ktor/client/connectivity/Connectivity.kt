package com.bselzer.ktx.ktor.client.connectivity

import io.ktor.client.*

actual class Connectivity actual constructor(
    configuration: ConnectivityConfiguration,
    httpClient: HttpClient
) : ConnectivityManager(configuration, httpClient)