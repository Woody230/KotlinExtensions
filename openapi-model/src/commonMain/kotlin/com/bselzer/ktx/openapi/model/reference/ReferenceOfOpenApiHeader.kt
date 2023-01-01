package com.bselzer.ktx.openapi.model.reference

import com.bselzer.ktx.openapi.model.path.OpenApiHeader
import com.bselzer.ktx.openapi.model.reference.path.HeaderReferencePath

class ReferenceOfOpenApiHeader : OpenApiReferenceOf<OpenApiHeader, HeaderReferencePath> {
    /**
     * Initializes a new instance of the [ReferenceOfOpenApiHeader] class.
     *
     * @param value the header
     */
    constructor(value: OpenApiHeader) : super(value)

    /**
     * Initializes a new instance of the [ReferenceOfOpenApiHeader] class.
     *
     * @param reference a reference to a header
     */
    constructor(reference: OpenApiReference<HeaderReferencePath>) : super(reference)
}