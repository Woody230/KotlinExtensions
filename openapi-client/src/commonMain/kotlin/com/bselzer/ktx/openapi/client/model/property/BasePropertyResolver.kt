package com.bselzer.ktx.openapi.client.model.property

import com.bselzer.ktx.codegen.model.annotation.Annotation
import com.bselzer.ktx.codegen.model.annotation.SerialName

abstract class BasePropertyResolver : PropertyResolver {
    internal fun extensionAnnotations(context: PropertyContext): Collection<Annotation> = with(context.schema) {
        buildList {
            title?.let { title -> add(SerialName(title)) }
        }
    }
}