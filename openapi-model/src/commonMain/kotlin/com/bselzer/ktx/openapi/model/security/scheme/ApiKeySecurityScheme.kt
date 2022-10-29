package com.bselzer.ktx.openapi.model.security.scheme

import com.bselzer.ktx.openapi.model.OpenApiDescription
import com.bselzer.ktx.openapi.model.parameter.OpenApiParameterIn
import com.bselzer.ktx.openapi.model.parameter.OpenApiParameterName

data class ApiKeySecurityScheme(
    override val description: OpenApiDescription? = null,

    /**
     * REQUIRED. The name of the header, query or cookie parameter to be used.
     */
    val name: OpenApiParameterName,

    /**
     * REQUIRED. The location of the API key. Valid values are "query", "header" or "cookie".
     */
    val `in`: OpenApiParameterIn,
) : OpenApiSecurityScheme {
    override val type: OpenApiSecuritySchemeType = OpenApiSecuritySchemeType.API_KEY
}