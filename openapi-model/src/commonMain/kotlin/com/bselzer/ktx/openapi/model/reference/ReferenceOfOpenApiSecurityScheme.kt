package com.bselzer.ktx.openapi.model.reference

import com.bselzer.ktx.openapi.model.reference.path.SecuritySchemeReferencePath
import com.bselzer.ktx.openapi.model.security.scheme.OpenApiSecurityScheme

class ReferenceOfOpenApiSecurityScheme : OpenApiReferenceOf<OpenApiSecurityScheme, SecuritySchemeReferencePath> {
    /**
     * Initializes a new instance of the [ReferenceOfOpenApiSecurityScheme] class.
     *
     * @param value the security scheme
     */
    constructor(value: OpenApiSecurityScheme) : super(value)

    /**
     * Initializes a new instance of the [ReferenceOfOpenApiSecurityScheme] class.
     *
     * @param reference a reference to a security scheme
     */
    constructor(reference: OpenApiReference<SecuritySchemeReferencePath>) : super(reference)
}