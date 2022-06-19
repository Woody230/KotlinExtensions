package com.bselzer.ktx.ktor.client.connectivity

import io.ktor.client.*

expect class Connectivity(
    configuration: ConnectivityConfiguration = ConnectivityConfiguration(),
    httpClient: HttpClient = HttpClient()
) : ConnectivityManager