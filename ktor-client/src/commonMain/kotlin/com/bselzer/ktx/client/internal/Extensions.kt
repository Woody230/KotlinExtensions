package com.bselzer.ktx.client.internal

import com.bselzer.ktx.client.options.UrlOptions

/**
 * Replaces the names of path parameters with the associated value.
 *
 * Names must be enclosed by curly brackets `{ }`.
 */
internal fun UrlOptions.replacedPathSegments(): List<String> {
    val pattern = Regex("\\{[^{}]*}")
    return pathSegments.map { segment ->
        segment.replace(pattern) { match ->
            val name = match.value.substring(1, match.range.last)
            pathParameters.getValue(name)
        }
    }
}