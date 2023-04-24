package com.bselzer.ktx.openapi.client.model.property

import com.bselzer.ktx.codegen.model.property.Property

class PropertyResolvers(
    private val resolvers: Collection<PropertyResolver>
) : PropertyResolver {
    override fun canResolve(context: PropertyContext): Boolean {
        fun canResolve(handler: PropertyResolver) = handler.canResolve(context)
        return resolvers.any(::canResolve)
    }

    override fun resolve(context: PropertyContext): Property {
        fun canResolve(handler: PropertyResolver) = handler.canResolve(context)
        val resolver = resolvers.firstOrNull(::canResolve) ?: throw NotImplementedError("Unable to resolve schema ${context.name}: ${context.schema}")
        return resolver.resolve(context)
    }
}