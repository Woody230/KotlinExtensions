package com.bselzer.ktx.openapi.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class OpenApiParameterIn {
    /**
     * Parameters that are appended to the URL. For example, in /items?id=###, the query parameter is id.
     */
    @SerialName("query")
    QUERY,

    /**
     * Custom headers that are expected as part of the request. Note that RFC7230 states header names are case insensitive.
     */
    @SerialName("header")
    HEADER,

    /**
     * Used together with Path Templating, where the parameter value is actually part of the operation’s URL. This does not include the host or base path of the API. For example, in /items/{itemId}, the path parameter is itemId.
     */
    @SerialName("path")
    PATH,

    /**
     * Used to pass a specific cookie value to the API.
     */
    @SerialName("cookie")
    COOKIE
}