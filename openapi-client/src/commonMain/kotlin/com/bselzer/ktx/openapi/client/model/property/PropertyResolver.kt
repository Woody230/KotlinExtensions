package com.bselzer.ktx.openapi.client.model.property

import com.bselzer.ktx.codegen.model.property.CopyableProperty

interface PropertyResolver {
    fun canResolve(input: PropertyInput): Boolean
    fun resolve(input: PropertyInput): CopyableProperty
}