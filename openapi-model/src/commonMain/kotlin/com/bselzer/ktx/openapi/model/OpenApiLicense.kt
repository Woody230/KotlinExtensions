package com.bselzer.ktx.openapi.model

import com.bselzer.ktx.openapi.model.base.OpenApiExtensible

/**
 * License information for the exposed API.
 */
data class OpenApiLicense(
    /**
     * REQUIRED. The license name used for the API.
     */
    val name: String,

    /**
     * An SPDX license expression for the API. The identifier field is mutually exclusive of the url field.
     */
    val identifier: String? = null,

    /**
     * A URL to the license used for the API. This MUST be in the form of a URL. The url field is mutually exclusive of the identifier field.
     */
    val url: OpenApiUrl? = null,

    override val extensions: OpenApiExtensions = emptyMap()
) : OpenApiExtensible