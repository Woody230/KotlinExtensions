package com.bselzer.ktx.openapi.model.reference

import com.bselzer.ktx.openapi.model.path.OpenApiRequestBody
import com.bselzer.ktx.openapi.model.reference.path.RequestBodyReferencePath

class ReferenceOfOpenApiRequestBody : OpenApiReferenceOf<OpenApiRequestBody, RequestBodyReferencePath> {
    /**
     * Initializes a new instance of the [ReferenceOfOpenApiRequestBody] class.
     *
     * @param value the request body
     */
    constructor(value: OpenApiRequestBody) : super(value)

    /**
     * Initializes a new instance of the [ReferenceOfOpenApiRequestBody] class.
     *
     * @param reference a reference to a request body
     */
    constructor(reference: OpenApiReference<RequestBodyReferencePath>) : super(reference)
}