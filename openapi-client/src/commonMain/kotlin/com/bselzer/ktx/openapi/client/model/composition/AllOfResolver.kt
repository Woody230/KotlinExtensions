package com.bselzer.ktx.openapi.client.model.composition

import com.bselzer.ktx.openapi.model.schema.OpenApiSchema

class AllOfResolver : CompositionResolver {
    override fun canResolve(context: CompositionContext): Boolean = with(context) {
        schema.allOf.any()
    }

    override fun resolve(context: CompositionContext): OpenApiSchema = with(context) {
        schema.allOf.fold(schema, OpenApiSchema::merge)
    }
}