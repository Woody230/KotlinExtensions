package com.bselzer.ktx.openapi.model.security.flow

import com.bselzer.ktx.openapi.model.OpenApiExtensions

data class PasswordOAuthFlow(
    /**
     * REQUIRED. The token URL to be used for this flow. This MUST be in the form of a URL. The OAuth2 standard requires the use of TLS.
     */
    val tokenUrl: String,

    override val refreshUrl: String? = null,
    override val scopes: Map<String, String> = emptyMap(),
    override val extensions: OpenApiExtensions = emptyMap()
) : OAuthFlow