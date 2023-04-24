package com.bselzer.ktx.openapi.client.model.property.composition

import com.bselzer.ktx.codegen.model.property.Property

interface PropertyCompositionResolver {
    fun canResolve(context: PropertyCompositionContext): Boolean
    fun resolve(context: PropertyCompositionContext): Property
}