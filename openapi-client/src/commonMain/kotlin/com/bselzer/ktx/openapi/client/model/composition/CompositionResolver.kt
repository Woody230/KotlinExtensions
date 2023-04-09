package com.bselzer.ktx.openapi.client.model.composition

import com.bselzer.ktx.openapi.model.schema.OpenApiSchema

interface CompositionResolver {
    fun canResolve(schema: OpenApiSchema, references: Map<String, OpenApiSchema>): Boolean
    fun resolve(schema: OpenApiSchema, references: Map<String, OpenApiSchema>): OpenApiSchema
}