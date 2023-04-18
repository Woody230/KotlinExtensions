package com.bselzer.ktx.openapi.client.model.property

class PropertyResolvers(
    private val resolvers: List<PropertyResolver>
) {
    fun resolver(input: PropertyInput): PropertyResolver {
        fun canResolve(handler: PropertyResolver) = handler.canResolve(input)
        return resolvers.firstOrNull(::canResolve) ?: throw NotImplementedError("Unable to resolve the schema ${input.name}: ${input.schema}")
    }
}