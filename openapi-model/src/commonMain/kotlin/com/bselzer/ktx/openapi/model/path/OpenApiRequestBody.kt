package com.bselzer.ktx.openapi.model.path

import com.bselzer.ktx.openapi.model.OpenApiContent
import com.bselzer.ktx.openapi.model.OpenApiDescription
import com.bselzer.ktx.openapi.model.OpenApiExtensible
import com.bselzer.ktx.openapi.model.OpenApiExtensions
import com.bselzer.ktx.openapi.serialization.OpenApiRequestBodySerializer

@kotlinx.serialization.Serializable(OpenApiRequestBodySerializer::class)
data class OpenApiRequestBody(
    /**
     * A brief description of the request body. This could contain examples of use. CommonMark syntax MAY be used for rich text representation.
     */
    val description: OpenApiDescription? = null,

    /**
     * REQUIRED. The content of the request body. The key is a media type or media type range and the value describes it. For requests that match multiple keys, only the most specific key is applicable. e.g. text/plain overrides text/`*
     */
    val content: OpenApiContent,

    /**
     * Determines if the request body is required in the request. Defaults to false.
     */
    val required: Boolean = false,

    override val extensions: OpenApiExtensions = emptyMap()
) : OpenApiExtensible