package com.bselzer.ktx.openapi.client.model.property.composition

import com.bselzer.ktx.codegen.model.annotation.SerialName
import com.bselzer.ktx.codegen.model.property.Property

class SerialNameResolver : PropertyCompositionResolver {
    override fun canResolve(context: PropertyCompositionContext): Boolean {
        // TODO if already applied, do not add another
        return context.schema.title != null
    }

    override fun resolve(context: PropertyCompositionContext): Property {
        val title = context.schema.title
        checkNotNull(title)

        return context.property.copy(
            annotations = context.property.annotations + SerialName(title)
        )
    }
}