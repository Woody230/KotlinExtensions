package com.bselzer.ktx.openapi.model.reference

import com.bselzer.ktx.openapi.model.parameter.OpenApiParameter
import com.bselzer.ktx.openapi.model.reference.path.ParameterReferencePath

class ReferenceOfOpenApiParameter : OpenApiReferenceOf<OpenApiParameter, ParameterReferencePath> {
    /**
     * Initializes a new instance of the [ReferenceOfOpenApiParameter] class.
     *
     * @param value the parameter
     */
    constructor(value: OpenApiParameter) : super(value)

    /**
     * Initializes a new instance of the [ReferenceOfOpenApiParameter] class.
     *
     * @param reference a reference to a parameter
     */
    constructor(reference: OpenApiReference<ParameterReferencePath>) : super(reference)
}