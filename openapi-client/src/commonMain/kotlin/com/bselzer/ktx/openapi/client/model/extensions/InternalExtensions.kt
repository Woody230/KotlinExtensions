package com.bselzer.ktx.openapi.client.model.extensions

import com.bselzer.ktx.codegen.model.annotation.Annotation
import com.bselzer.ktx.codegen.model.annotation.SerialName
import com.bselzer.ktx.openapi.model.schema.OpenApiSchema

internal fun OpenApiSchema.annotations(): Collection<Annotation> = buildList {
    title?.let { title -> add(SerialName(title)) }
}