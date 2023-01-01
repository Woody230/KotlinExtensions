package com.bselzer.ktx.openapi.model.reference

import com.bselzer.ktx.openapi.model.reference.path.SchemaReferencePath
import com.bselzer.ktx.openapi.model.schema.OpenApiSchema

class ReferenceOfOpenApiSchema : OpenApiReferenceOf<OpenApiSchema, SchemaReferencePath> {
    /**
     * Initializes a new instance of the [ReferenceOfOpenApiSchema] class.
     *
     * @param value the schema
     */
    constructor(value: OpenApiSchema) : super(value)

    /**
     * Initializes a new instance of the [ReferenceOfOpenApiSchema] class.
     *
     * @param reference a reference to a schema
     */
    constructor(reference: OpenApiReference<SchemaReferencePath>) : super(reference)
}