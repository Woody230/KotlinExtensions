package com.bselzer.ktx.openapi.client.model.composition

import com.bselzer.ktx.openapi.model.schema.OpenApiSchema

class AllOfResolver : CompositionResolver {
    override fun canResolve(schema: OpenApiSchema, references: Map<String, OpenApiSchema>): Boolean = schema.allOf.any()
    override fun resolve(schema: OpenApiSchema, references: Map<String, OpenApiSchema>): OpenApiSchema = schema.allOf.fold(schema, OpenApiSchema::merge)
}