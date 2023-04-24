package com.bselzer.ktx.openapi.client.model.type.valueclass

import com.bselzer.ktx.openapi.model.schema.OpenApiSchema

data class ValueClassContext(
    val schema: OpenApiSchema,
    val references: Map<String, OpenApiSchema>,
    val name: String
)