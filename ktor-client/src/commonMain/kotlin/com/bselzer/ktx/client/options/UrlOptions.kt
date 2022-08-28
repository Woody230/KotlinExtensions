package com.bselzer.ktx.client.options

import io.ktor.http.*

class UrlOptions(
    private val protocol: URLProtocol? = null,
    private val host: String? = null,
    private val port: Int? = null,

    /**
     * The names of [pathParameters] should be enclosed by curly brackets `{ }`.
     */
    private val pathSegments: List<String> = emptyList(),

    /**
     * The parameters to replace in the [pathSegments].
     */
    val pathParameters: Map<String, String> = emptyMap(),

    /**
     * The parameters to append to the path.
     */
    val queryParameters: Map<String, String> = emptyMap(),

    private val fragment: String? = null,
    private val user: String? = null,
    private val password: String? = null,
    private val trailingQuery: Boolean? = null
) {
    val url: Url
        get() = URLBuilder().apply {
            appendPathSegments(replacedPathSegments)
            queryParameters.forEach { query -> parameters.append(query.key, query.value) }
            this@UrlOptions.protocol?.let { protocol = it }
            this@UrlOptions.host?.let { host = it }
            this@UrlOptions.port?.let { port = it }
            user = this@UrlOptions.user
            password = this@UrlOptions.password
            this@UrlOptions.fragment?.let { fragment = it }
            this@UrlOptions.trailingQuery?.let { trailingQuery = it }
        }.build()

    /**
     * Get the first parameter that uses the [key] from either the [pathParameters] or the [queryParameters], or null if it does not exist.
     */
    fun parameterOrNull(key: String): String? = pathParameters[key] ?: queryParameters[key]

    /**
     * Takes the [other] [protocol], [host], [port], [fragment], [user], [password], and [trailingQuery] over these options.
     * Appends the [other] [pathSegments] to these segments.
     * Adds [other] [pathParameters] and [queryParameters] to these parameters, replacing existing keys if there is a duplicate.
     */
    fun merge(other: UrlOptions): UrlOptions = UrlOptions(
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

    /**
     * Takes the other [protocol], [host], [port], [fragment], [user], [password], and [trailingQuery] over these options.
     * Appends the other [pathSegments] to these segments.
     * Adds the other [pathParameters] and [queryParameters] to these parameters, replacing existing keys if there is a duplicate.
     */
    fun merge(vararg others: UrlOptions): UrlOptions = others.fold(initial = this) { current, next -> current.merge(next) }

    companion object {
        val Default = UrlOptions()
        private val replacementPattern = Regex("\\{[^{}]*}")
    }

    /**
     * Replaces the names of path parameters with the associated value.
     *
     * Names must be enclosed by curly brackets `{ }`.
     */
    private val replacedPathSegments: List<String>
        get() = pathSegments.map { segment ->
            segment.replace(replacementPattern) { match ->
                val name = match.value.substring(1, match.range.last)
                pathParameters.getValue(name)
            }
        }
}