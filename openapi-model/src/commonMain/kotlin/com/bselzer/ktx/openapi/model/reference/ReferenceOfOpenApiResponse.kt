package com.bselzer.ktx.openapi.model.reference

import com.bselzer.ktx.openapi.model.reference.path.ResponseReferencePath
import com.bselzer.ktx.openapi.model.response.OpenApiResponse

class ReferenceOfOpenApiResponse : OpenApiReferenceOf<OpenApiResponse, ResponseReferencePath> {
    /**
     * Initializes a new instance of the [ReferenceOfOpenApiResponse] class.
     *
     * @param value the response
     */
    constructor(value: OpenApiResponse) : super(value)

    /**
     * Initializes a new instance of the [ReferenceOfOpenApiResponse] class.
     *
     * @param reference a reference to a response
     */
    constructor(reference: OpenApiReference<ResponseReferencePath>) : super(reference)
}