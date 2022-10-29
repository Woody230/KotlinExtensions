package com.bselzer.ktx.openapi.model.security.scheme

import com.bselzer.ktx.openapi.model.OpenApiDescription

data class HttpSecurityScheme(
    override val description: OpenApiDescription? = null,

    /**
     * REQUIRED. The name of the HTTP Authorization scheme to be used in the Authorization header as defined in [RFC7235]. The values used SHOULD be registered in the IANA Authentication Scheme registry.
     */
    val scheme: String,

    /**
     * A hint to the client to identify how the bearer token is formatted. Bearer tokens are usually generated by an authorization server, so this information is primarily for documentation purposes.
     */
    val bearerFormat: String? = null,
) : OpenApiSecurityScheme {
    override val type: OpenApiSecuritySchemeType = OpenApiSecuritySchemeType.HTTP
}