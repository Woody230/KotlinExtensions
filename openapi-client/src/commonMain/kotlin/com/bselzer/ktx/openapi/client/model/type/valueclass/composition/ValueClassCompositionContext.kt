package com.bselzer.ktx.openapi.client.model.type.valueclass.composition

import com.bselzer.ktx.codegen.model.type.`class`.value.ValueClass
import com.bselzer.ktx.openapi.model.schema.OpenApiSchema

data class ValueClassCompositionContext(
    val valueClass: ValueClass,
    val schema: OpenApiSchema,
    val references: Map<String, OpenApiSchema>,
    val name: String
)