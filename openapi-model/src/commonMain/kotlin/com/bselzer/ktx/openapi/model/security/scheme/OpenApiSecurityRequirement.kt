package com.bselzer.ktx.openapi.model.security.scheme

import com.bselzer.ktx.openapi.serialization.OpenApiSecurityRequirementSerializer

/**
 * Lists the required security schemes to execute this operation. The name used for each property MUST correspond to a security scheme declared in the Security Schemes under the Components Object.
 *
 * Security Requirement Objects that contain multiple schemes require that all schemes MUST be satisfied for a request to be authorized. This enables support for scenarios where multiple query parameters or HTTP headers are required to convey security information.
 *
 * When a list of Security Requirement Objects is defined on the OpenAPI Object or Operation Object, only one of the Security Requirement Objects in the list needs to be satisfied to authorize the request.
 */
@kotlinx.serialization.Serializable(OpenApiSecurityRequirementSerializer::class)
data class OpenApiSecurityRequirement(
    /**
     * Each name MUST correspond to a security scheme which is declared in the Security Schemes under the Components Object. If the security scheme is of type "oauth2" or "openIdConnect", then the value is a list of scope names required for the execution, and the list MAY be empty if authorization does not require a specified scope. For other security scheme types, the array MAY contain a list of role names which are required for the execution, but are not otherwise defined or exchanged in-band.
     */
    val scopes: Map<OpenApiSecuritySchemeName, List<OpenApiSecurityScope>> = emptyMap()
)