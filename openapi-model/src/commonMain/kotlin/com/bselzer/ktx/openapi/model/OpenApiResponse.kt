package com.bselzer.ktx.openapi.model

import com.bselzer.ktx.openapi.model.base.OpenApiExtensible

/**
 * Describes a single response from an API Operation, including design-time, static links to operations based on the response.
 */
data class OpenApiResponse(
    /**
     * REQUIRED. A description of the response. CommonMark syntax MAY be used for rich text representation.
     */
    val description: OpenApiDescription,

    /**
     * Maps a header name to its definition. RFC7230 states header names are case insensitive. If a response header is defined with the name "Content-Type", it SHALL be ignored.
     */
    val headers: OpenApiHeaders = emptyMap(),

    /**
     * A map containing descriptions of potential response payloads. The key is a media type or media type range and the value describes it. For responses that match multiple keys, only the most specific key is applicable. e.g. text/plain overrides text/`*
     */
    val content: OpenApiContent = emptyMap(),

    /**
     * A map of operations links that can be followed from the response. The key of the map is a short name for the link, following the naming constraints of the names for Component Objects.
     */
    val links: OpenApiLinks = emptyMap(),

    override val extensions: OpenApiExtensions = emptyMap()
) : OpenApiExtensible