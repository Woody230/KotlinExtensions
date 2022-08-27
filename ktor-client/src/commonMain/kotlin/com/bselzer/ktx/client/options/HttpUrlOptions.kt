package com.bselzer.ktx.client.options

import com.bselzer.ktx.client.internal.replacedPathSegments
import io.ktor.http.*

interface HttpUrlOptions {
    /**
     * The path segments.
     *
     * The names of [pathParameters] should be enclosed by curly brackets `{ }`.
     */
    val pathSegments: List<String>

    /**
     * The parameters to replace in the [pathSegments].
     */
    val pathParameters: Map<String, String>

    /**
     * The parameters to append to the path.
     */
    val queryParameters: Map<String, String>

    /**
     * URL protocol (scheme).
     */
    val protocol: URLProtocol?

    /**
     * The name without the port (domain).
     */
    val host: String?

    /**
     * The port identifier.
     */
    val port: Int?

    /**
     * The username.
     */
    val user: String?

    /**
     * The password.
     */
    val password: String?

    /**
     * The fragment.
     */
    val fragment: String?

    /**
     * Whether to keep trailing question character even if there are no query parameters
     */
    val trailingQuery: Boolean?

    val url: Url
        get() = URLBuilder().apply {
            appendPathSegments(replacedPathSegments())
            queryParameters.forEach { query -> parameters.append(query.key, query.value) }
            this@HttpUrlOptions.protocol?.let { protocol = it }
            this@HttpUrlOptions.host?.let { host = it }
            this@HttpUrlOptions.port?.let { port = it }
            user = this@HttpUrlOptions.user
            password = this@HttpUrlOptions.password
            this@HttpUrlOptions.fragment?.let { fragment = it }
            this@HttpUrlOptions.trailingQuery?.let { trailingQuery = it }
        }.build()

    companion object : HttpUrlOptions by UrlOptions()

    /**
     * Takes the [other] [protocol], [host], [port], [fragment], [user], [password], and [trailingQuery] over these options.
     * Appends the [other] [pathSegments] to these segments.
     * Adds or replaces the [other] [pathParameters] and [queryParameters] to these parameters.
     */
    fun merge(other: HttpUrlOptions): HttpUrlOptions = UrlOptions(
        protocol = other.protocol ?: protocol,
        host = other.host ?: host,
        port = other.port ?: port,
        pathSegments = pathSegments + other.pathSegments,
        pathParameters = pathParameters + other.pathParameters,
        queryParameters = queryParameters + other.queryParameters,
        fragment = other.fragment ?: fragment,
        user = other.user ?: user,
        password = other.password ?: password,
        trailingQuery = other.trailingQuery ?: trailingQuery
    )
}

