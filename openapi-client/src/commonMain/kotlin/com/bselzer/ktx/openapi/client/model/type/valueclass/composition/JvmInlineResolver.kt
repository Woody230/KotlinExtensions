package com.bselzer.ktx.openapi.client.model.type.valueclass.composition

import com.bselzer.ktx.codegen.model.annotation.JvmInline
import com.bselzer.ktx.codegen.model.type.`class`.value.ValueClass

class JvmInlineResolver : ValueClassCompositionResolver {
    override fun canResolve(context: ValueClassCompositionContext): Boolean = true
    override fun resolve(context: ValueClassCompositionContext): ValueClass = with(context) {
        valueClass.copy(
            annotations = valueClass.annotations + JvmInline()
        )
    }
}