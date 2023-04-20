package com.bselzer.ktx.openapi.client.model.composition

import com.bselzer.ktx.openapi.model.schema.OpenApiSchema

interface CompositionResolver {
    fun canResolve(input: CompositionInput): Boolean
    fun resolve(input: CompositionInput): OpenApiSchema
}