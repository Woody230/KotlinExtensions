package com.bselzer.ktx.openapi.client.schema

import com.bselzer.ktx.openapi.model.schema.OpenApiSchema

class SchemaResolver(
    private val handlers: List<SchemaHandler>
) {
    fun handler(schema: OpenApiSchema, references: Map<String, OpenApiSchema>): SchemaHandler {
        fun canResolve(handler: SchemaHandler) = handler.canResolve(schema, references)
        return handlers.firstOrNull(::canResolve) ?: throw NotImplementedError("Unable to resolve the schema: $schema")
    }
}