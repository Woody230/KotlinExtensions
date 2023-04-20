package com.bselzer.ktx.openapi.client.model.composition

import com.bselzer.ktx.openapi.model.schema.OpenApiSchema

class AllOfResolver : CompositionResolver {
    override fun canResolve(input: CompositionInput): Boolean = with(input) {
        schema.allOf.any()
    }

    override fun resolve(input: CompositionInput): OpenApiSchema = with(input) {
        schema.allOf.fold(schema, OpenApiSchema::merge)
    }
}