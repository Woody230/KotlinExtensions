package com.bselzer.ktx.openapi.model.security.scheme

import com.bselzer.ktx.openapi.model.OpenApiDescription
import com.bselzer.ktx.openapi.serialization.OpenApiSecuritySchemeSerializer

/**
 * Defines a security scheme that can be used by the operations.
 */
@kotlinx.serialization.Serializable(OpenApiSecuritySchemeSerializer::class)
sealed interface OpenApiSecurityScheme {
    /**
     * REQUIRED. The type of the security scheme. Valid values are "apiKey", "http", "mutualTLS", "oauth2", "openIdConnect".
     */
    val type: OpenApiSecuritySchemeType

    /**
     * A description for security scheme. CommonMark syntax MAY be used for rich text representation.
     */
    val description: OpenApiDescription?
}
