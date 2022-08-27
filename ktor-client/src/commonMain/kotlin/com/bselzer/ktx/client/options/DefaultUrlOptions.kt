package com.bselzer.ktx.client.options

import io.ktor.http.*

data class DefaultUrlOptions(
    override val protocol: URLProtocol? = null,
    override val host: String? = null,
    override val port: Int? = null,
    override val pathSegments: List<String> = emptyList(),
    override val pathParameters: Map<String, String> = emptyMap(),
    override val queryParameters: Map<String, String> = emptyMap(),
    override val fragment: String? = null,
    override val user: String? = null,
    override val password: String? = null,
    override val trailingQuery: Boolean? = null
) : UrlOptions