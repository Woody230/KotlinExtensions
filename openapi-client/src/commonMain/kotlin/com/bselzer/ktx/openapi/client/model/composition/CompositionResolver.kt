package com.bselzer.ktx.openapi.client.model.composition

import com.bselzer.ktx.openapi.model.schema.OpenApiSchema

interface CompositionResolver {
    fun canResolve(context: CompositionContext): Boolean
    fun resolve(context: CompositionContext): OpenApiSchema
}