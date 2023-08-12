package com.bselzer.ktx.client.connectivity

import io.ktor.client.*

actual class Connectivity actual constructor(
    configuration: ConnectivityConfiguration,
    httpClient: HttpClient
) : ConnectivityManager by KtorConnectivityManager(configuration, httpClient)