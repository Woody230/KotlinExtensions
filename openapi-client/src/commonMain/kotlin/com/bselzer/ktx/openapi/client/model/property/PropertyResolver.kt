package com.bselzer.ktx.openapi.client.model.property

import com.bselzer.ktx.codegen.model.property.Property

// TODO type property resolver for types that will be generated
interface PropertyResolver {
    fun canResolve(context: PropertyContext): Boolean
    fun resolve(context: PropertyContext): Property
}