package com.bselzer.ktx.openapi.model.security.flow

import com.bselzer.ktx.openapi.model.OpenApiExtensions
import com.bselzer.ktx.openapi.model.OpenApiUrl
import com.bselzer.ktx.openapi.model.security.scheme.OpenApiSecurityScope

data class ImplicitOAuthFlow(
    override val refreshUrl: OpenApiUrl? = null,
    override val scopes: Map<OpenApiSecurityScope, String> = emptyMap(),
    override val extensions: OpenApiExtensions = emptyMap(),

    /**
     * REQUIRED. The authorization URL to be used for this flow. This MUST be in the form of a URL. The OAuth2 standard requires the use of TLS.
     */
    val authorizationUrl: OpenApiUrl
) : OAuthFlow