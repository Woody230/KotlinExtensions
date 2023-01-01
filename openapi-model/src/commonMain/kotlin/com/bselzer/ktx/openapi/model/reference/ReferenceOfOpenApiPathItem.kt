package com.bselzer.ktx.openapi.model.reference

import com.bselzer.ktx.openapi.model.path.OpenApiPathItem
import com.bselzer.ktx.openapi.model.reference.path.PathItemReferencePath

class ReferenceOfOpenApiPathItem : OpenApiReferenceOf<OpenApiPathItem, PathItemReferencePath> {
    /**
     * Initializes a new instance of the [ReferenceOfOpenApiPathItem] class.
     *
     * @param value the path item
     */
    constructor(value: OpenApiPathItem) : super(value)

    /**
     * Initializes a new instance of the [ReferenceOfOpenApiPathItem] class.
     *
     * @param reference a reference to a path item
     */
    constructor(reference: OpenApiReference<PathItemReferencePath>) : super(reference)
}