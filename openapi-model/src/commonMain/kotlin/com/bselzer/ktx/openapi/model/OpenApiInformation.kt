package com.bselzer.ktx.openapi.model

import com.bselzer.ktx.openapi.model.base.OpenApiExtensible

/**
 * The object provides metadata about the API. The metadata MAY be used by the clients if needed, and MAY be presented in editing or documentation generation tools for convenience.
 */
data class OpenApiInformation(
    /**
     * REQUIRED. The title of the API.
     */
    val title: String,

    /**
     * A short summary of the API.
     */
    val summary: String? = null,

    /**
     * A description of the API. CommonMark syntax MAY be used for rich text representation.
     */
    val description: OpenApiDescription? = null,

    /**
     * A URL to the Terms of Service for the API. This MUST be in the form of a URL.
     */
    val termsOfService: String? = null,

    /**
     * The contact information for the exposed API.
     */
    val contact: OpenApiContact? = null,

    /**
     * The license information for the exposed API.
     */
    val license: OpenApiLicense? = null,

    /**
     * REQUIRED. The version of the OpenAPI document (which is distinct from the OpenAPI Specification version or the API implementation version).
     */
    val version: String,

    override val extensions: OpenApiExtensions = emptyMap()
) : OpenApiExtensible