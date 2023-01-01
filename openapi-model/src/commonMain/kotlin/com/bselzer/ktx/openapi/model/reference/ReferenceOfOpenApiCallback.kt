package com.bselzer.ktx.openapi.model.reference

import com.bselzer.ktx.openapi.model.path.OpenApiCallback
import com.bselzer.ktx.openapi.model.reference.path.CallbackReferencePath

class ReferenceOfOpenApiCallback : OpenApiReferenceOf<OpenApiCallback, CallbackReferencePath> {
    /**
     * Initializes a new instance of the [ReferenceOfOpenApiCallback] class.
     *
     * @param value the callback
     */
    constructor(value: OpenApiCallback) : super(value)

    /**
     * Initializes a new instance of the [ReferenceOfOpenApiCallback] class.
     *
     * @param reference a reference to a callback
     */
    constructor(reference: OpenApiReference<CallbackReferencePath>) : super(reference)
}