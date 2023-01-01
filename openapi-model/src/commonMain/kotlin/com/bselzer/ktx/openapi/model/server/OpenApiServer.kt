package com.bselzer.ktx.openapi.model.server

import com.bselzer.ktx.openapi.model.OpenApiDescription
import com.bselzer.ktx.openapi.model.OpenApiExtensible
import com.bselzer.ktx.openapi.model.OpenApiExtensions
import com.bselzer.ktx.openapi.model.OpenApiUrl

/**
 * An object representing a Server.
 */
data class OpenApiServer(
    /**
     * REQUIRED. A URL to the target host. This URL supports Server Variables and MAY be relative, to indicate that the host location is relative to the location where the OpenAPI document is being served. Variable substitutions will be made when a variable is named in {brackets}.
     */
    val url: OpenApiUrl,

    /**
     * An optional string describing the host designated by the URL. CommonMark syntax MAY be used for rich text representation.
     */
    val description: OpenApiDescription? = null,

    /**
     * A map between a variable name and its value. The value is used for substitution in the server’s URL template.
     */
    val variables: Map<String, OpenApiServerVariable> = emptyMap(),

    override val extensions: OpenApiExtensions = emptyMap()
) : OpenApiExtensible