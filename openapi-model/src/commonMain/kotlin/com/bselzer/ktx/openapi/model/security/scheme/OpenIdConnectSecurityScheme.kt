package com.bselzer.ktx.openapi.model.security.scheme

import com.bselzer.ktx.openapi.model.OpenApiDescription
import com.bselzer.ktx.openapi.model.OpenApiUrl

data class OpenIdConnectSecurityScheme(
    override val description: OpenApiDescription? = null,

    /**
     * REQUIRED. OpenId Connect URL to discover OAuth2 configuration values. This MUST be in the form of a URL. The OpenID Connect standard requires the use of TLS.
     */
    val openIdConnectUrl: OpenApiUrl,
) : OpenApiSecurityScheme {
    override val type: OpenApiSecuritySchemeType = OpenApiSecuritySchemeType.OPEN_ID_CONNECT
}