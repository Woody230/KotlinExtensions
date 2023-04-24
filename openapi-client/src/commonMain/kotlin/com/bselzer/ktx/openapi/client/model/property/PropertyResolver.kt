package com.bselzer.ktx.openapi.client.model.property

import com.bselzer.ktx.codegen.model.property.Property

interface PropertyResolver {
    fun canResolve(context: PropertyContext): Boolean
    fun resolve(context: PropertyContext): Property
}