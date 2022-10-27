package com.bselzer.ktx.openapi.model

import com.bselzer.ktx.openapi.model.base.OpenApiExtensible

/**
 * This is the root object of the OpenAPI document.
 */
data class OpenApi(
    /**
     * REQUIRED. This string MUST be the version number of the OpenAPI Specification that the OpenAPI document uses. The openapi field SHOULD be used by tooling to interpret the OpenAPI document. This is not related to the API info.version string.
     */
    val openapi: String = "3.1.0",

    /**
     * REQUIRED. Provides metadata about the API. The metadata MAY be used by tooling as required.
     */
    val info: OpenApiInformation,

    /**
     * The default value for the $schema keyword within Schema Objects contained within this OAS document. This MUST be in the form of a URI.
     */
    val jsonSchemaDialect: String = "https://spec.openapis.org/oas/3.1/dialect/base",

    /**
     * An array of Server Objects, which provide connectivity information to a target server. If the servers property is not provided, or is an empty array, the default value would be a Server Object with a url value of /.
     */
    val servers: OpenApiServers = emptyList(),

    /**
     * The available paths and operations for the API.
     */
    val paths: OpenApiPaths? = null,

    override val extensions: OpenApiExtensions = emptyMap()
) : OpenApiExtensible