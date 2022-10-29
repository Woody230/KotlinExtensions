package com.bselzer.ktx.openapi.model

import com.bselzer.ktx.openapi.model.base.OpenApiExtensible
import com.bselzer.ktx.openapi.model.schema.OpenApiSchema
import com.bselzer.ktx.openapi.model.security.scheme.OpenApiSecurityScheme
import com.bselzer.ktx.openapi.model.security.scheme.OpenApiSecuritySchemeName

/**
 * Holds a set of reusable objects for different aspects of the OAS. All objects defined within the components object will have no effect on the API unless they are explicitly referenced from properties outside the components object.
 */
data class OpenApiComponents(
    /**
     * An object to hold reusable Schema Objects.
     */
    val schemas: Map<String, OpenApiSchema> = emptyMap(),

    /**
     * An object to hold reusable Response Objects.
     */
    val responses: Map<String, OpenApiReferenceOf<OpenApiResponse>> = emptyMap(),

    /**
     * An object to hold reusable Parameter Objects.
     */
    val parameters: Map<OpenApiParameterName, OpenApiReferenceOf<OpenApiParameter>> = emptyMap(),

    /**
     * An object to hold reusable Example Objects.
     */
    val examples: OpenApiExamples = emptyMap(),

    /**
     * An object to hold reusable Request Body Objects.
     */
    val requestBodies: Map<String, OpenApiReferenceOf<OpenApiRequestBody>> = emptyMap(),

    /**
     * An object to hold reusable Header Objects.
     */
    val headers: OpenApiHeaders = emptyMap(),

    /**
     * An object to hold reusable Security Scheme Objects.
     */
    val securitySchemes: Map<OpenApiSecuritySchemeName, OpenApiReferenceOf<OpenApiSecurityScheme>> = emptyMap(),

    /**
     * An object to hold reusable Link Objects.
     */
    val links: OpenApiLinks = emptyMap(),

    /**
     * An object to hold reusable Callback Objects.
     */
    val callbacks: OpenApiCallbacks = emptyMap(),

    /**
     * An object to hold reusable Path Item Object.
     */
    val pathItems: Map<String, OpenApiReferenceOf<OpenApiPathItem>> = emptyMap(),

    override val extensions: OpenApiExtensions = emptyMap(),
) : OpenApiExtensible