package com.bselzer.ktx.openapi.model

import com.bselzer.ktx.openapi.serialization.OpenApiTagSerializer

/**
 * Adds metadata to a single tag that is used by the Operation Object. It is not mandatory to have a Tag Object per tag defined in the Operation Object instances.
 */
@kotlinx.serialization.Serializable(OpenApiTagSerializer::class)
data class OpenApiTag(
    /**
     * REQUIRED. The name of the tag.
     */
    val name: OpenApiTagName,

    /**
     * A description for the tag. CommonMark syntax MAY be used for rich text representation.
     */
    val description: OpenApiDescription? = null,

    /**
     * Additional external documentation for this tag.
     */
    val externalDocs: OpenApiExternalDocumentation? = null,

    override val extensions: OpenApiExtensions = emptyMap()
) : OpenApiExtensible