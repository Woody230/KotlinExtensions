package com.bselzer.ktx.openapi.model.reference

import com.bselzer.ktx.openapi.model.reference.path.LinkReferencePath
import com.bselzer.ktx.openapi.model.response.OpenApiLink

class ReferenceOfOpenApiLink : OpenApiReferenceOf<OpenApiLink, LinkReferencePath> {
    /**
     * Initializes a new instance of the [ReferenceOfOpenApiLink] class.
     *
     * @param value the link
     */
    constructor(value: OpenApiLink) : super(value)

    /**
     * Initializes a new instance of the [ReferenceOfOpenApiLink] class.
     *
     * @param reference a reference to a link
     */
    constructor(reference: OpenApiReference<LinkReferencePath>) : super(reference)
}