package com.bselzer.ktx.openapi.model

import com.bselzer.ktx.openapi.model.base.OpenApiExtensible

/**
 * Allows referencing an external resource for extended documentation.
 */
data class OpenApiExternalDocumentation(
    /**
     * A description of the target documentation. CommonMark syntax MAY be used for rich text representation.
     */
    val description: OpenApiDescription? = null,

    /**
     * REQUIRED. The URL for the target documentation. This MUST be in the form of a URL.
     */
    val url: OpenApiUrl,

    override val extensions: OpenApiExtensions = emptyMap()
) : OpenApiExtensible