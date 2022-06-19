package com.bselzer.ktx.ktor.client.connectivity

enum class TransportType(val code: Int) {
    CELLULAR(0),
    WIFI(1),
    BLUETOOTH(2),
    ETHERNET(3),
    VPN(4),
    WIFI_AWARE(5),
    LOWPAN(6)
}