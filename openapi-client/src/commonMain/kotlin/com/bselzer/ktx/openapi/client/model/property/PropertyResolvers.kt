package com.bselzer.ktx.openapi.client.model.property

import com.bselzer.ktx.openapi.model.schema.OpenApiSchema

class PropertyResolvers(
    private val resolvers: List<PropertyResolver>
) {
    fun resolver(schema: OpenApiSchema, references: Map<String, OpenApiSchema>): PropertyResolver {
        fun canResolve(handler: PropertyResolver) = handler.canResolve(schema, references)
        return resolvers.firstOrNull(::canResolve) ?: throw NotImplementedError("Unable to resolve the schema: $schema")
    }
}