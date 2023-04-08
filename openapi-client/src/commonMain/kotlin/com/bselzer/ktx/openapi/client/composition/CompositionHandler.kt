package com.bselzer.ktx.openapi.client.composition

import com.bselzer.ktx.openapi.model.schema.OpenApiSchema

interface CompositionHandler {
    fun canResolve(schema: OpenApiSchema, references: Map<String, OpenApiSchema>): Boolean
    fun resolve(schema: OpenApiSchema, references: Map<String, OpenApiSchema>): OpenApiSchema
}