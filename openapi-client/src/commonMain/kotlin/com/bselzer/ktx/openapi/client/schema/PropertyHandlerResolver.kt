package com.bselzer.ktx.openapi.client.schema

import com.bselzer.ktx.openapi.model.schema.OpenApiSchema

class PropertyHandlerResolver(
    private val handlers: List<PropertyHandler>
) {
    fun handler(schema: OpenApiSchema, references: Map<String, OpenApiSchema>): PropertyHandler {
        fun canResolve(handler: PropertyHandler) = handler.canResolve(schema, references)
        return handlers.firstOrNull(::canResolve) ?: throw NotImplementedError("Unable to resolve the schema: $schema")
    }
}