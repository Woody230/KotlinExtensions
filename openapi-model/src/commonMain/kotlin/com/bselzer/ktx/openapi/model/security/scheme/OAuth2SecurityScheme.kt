package com.bselzer.ktx.openapi.model.security.scheme

import com.bselzer.ktx.openapi.model.OpenApiDescription
import com.bselzer.ktx.openapi.model.security.flow.OpenApiOAuthFlows

data class OAuth2SecurityScheme(
    override val description: OpenApiDescription? = null,

    /**
     * REQUIRED. An object containing configuration information for the flow types supported.
     */
    val flows: OpenApiOAuthFlows,
) : OpenApiSecurityScheme {
    override val type: OpenApiSecuritySchemeType = OpenApiSecuritySchemeType.OAUTH_2
}