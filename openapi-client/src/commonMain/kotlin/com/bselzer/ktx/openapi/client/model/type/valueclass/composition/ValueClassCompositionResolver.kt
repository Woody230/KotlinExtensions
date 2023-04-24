package com.bselzer.ktx.openapi.client.model.type.valueclass.composition

import com.bselzer.ktx.codegen.model.type.`class`.value.ValueClass

interface ValueClassCompositionResolver {
    fun canResolve(context: ValueClassCompositionContext): Boolean
    fun resolve(context: ValueClassCompositionContext): ValueClass
}