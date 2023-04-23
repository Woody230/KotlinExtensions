package com.bselzer.ktx.openapi.client.model.property

import com.bselzer.ktx.codegen.model.property.Property

class PropertyResolvers(
    private val resolvers: Collection<PropertyResolver>
) : PropertyResolver {
    override fun canResolve(input: PropertyContext): Boolean {
        fun canResolve(handler: PropertyResolver) = handler.canResolve(input)
        return resolvers.any(::canResolve)
    }

    override fun resolve(input: PropertyContext): Property {
        fun canResolve(handler: PropertyResolver) = handler.canResolve(input)
        val resolver = resolvers.firstOrNull(::canResolve) ?: throw NotImplementedError("Unable to resolve schema ${input.name}: ${input.schema}")
        return resolver.resolve(input)
    }
}