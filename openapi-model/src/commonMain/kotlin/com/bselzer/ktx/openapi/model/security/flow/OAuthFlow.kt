package com.bselzer.ktx.openapi.model.security.flow

import com.bselzer.ktx.openapi.model.base.OpenApiExtensible
import com.bselzer.ktx.openapi.model.security.scheme.OpenApiSecurityScope

sealed interface OAuthFlow : OpenApiExtensible {
    /**
     * The URL to be used for obtaining refresh tokens. This MUST be in the form of a URL. The OAuth2 standard requires the use of TLS.
     */
    val refreshUrl: String?

    /**
     * REQUIRED. The available scopes for the OAuth2 security scheme. A map between the scope name and a short description for it. The map MAY be empty.
     */
    val scopes: Map<OpenApiSecurityScope, String>
}