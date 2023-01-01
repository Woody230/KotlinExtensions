package com.bselzer.ktx.openapi.model.reference

import com.bselzer.ktx.openapi.model.OpenApiExample
import com.bselzer.ktx.openapi.model.reference.path.ExampleReferencePath

class ReferenceOfOpenApiExample : OpenApiReferenceOf<OpenApiExample, ExampleReferencePath> {
    /**
     * Initializes a new instance of the [ReferenceOfOpenApiExample] class.
     *
     * @param value the example
     */
    constructor(value: OpenApiExample) : super(value)

    /**
     * Initializes a new instance of the [ReferenceOfOpenApiExample] class.
     *
     * @param reference a reference to an example
     */
    constructor(reference: OpenApiReference<ExampleReferencePath>) : super(reference)
}