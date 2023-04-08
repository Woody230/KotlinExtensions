package com.bselzer.ktx.openapi.client.property

import com.bselzer.ktx.openapi.model.schema.OpenApiSchema

interface PropertyHandler {
    fun canResolve(schema: OpenApiSchema, references: Map<String, OpenApiSchema>): Boolean
    fun resolve(schema: OpenApiSchema, references: Map<String, OpenApiSchema>): PropertyOutput
}