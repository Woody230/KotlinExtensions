package com.bselzer.ktx.openapi.client.model.property

import com.bselzer.ktx.codegen.model.property.CopyableProperty

interface PropertyResolver {
    fun canResolve(input: PropertyContext): Boolean
    fun resolve(input: PropertyContext): CopyableProperty
}