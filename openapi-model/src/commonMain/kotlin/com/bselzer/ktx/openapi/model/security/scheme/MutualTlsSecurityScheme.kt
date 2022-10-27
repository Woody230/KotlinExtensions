package com.bselzer.ktx.openapi.model.security.scheme

import com.bselzer.ktx.openapi.model.OpenApiDescription

data class MutualTlsSecurityScheme(
    override val description: OpenApiDescription? = null
) : OpenApiSecurityScheme {
    override val type: OpenApiSecuritySchemeType = OpenApiSecuritySchemeType.MUTUAL_TLS
}