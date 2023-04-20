package com.bselzer.ktx.openapi.client.model.composition

import com.bselzer.ktx.openapi.model.schema.OpenApiSchema

data class CompositionInput(
    val schema: OpenApiSchema,
    val references: Map<String, OpenApiSchema>,
)