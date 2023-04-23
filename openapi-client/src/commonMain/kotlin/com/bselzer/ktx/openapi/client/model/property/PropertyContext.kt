package com.bselzer.ktx.openapi.client.model.property

import com.bselzer.ktx.openapi.model.schema.OpenApiSchema

data class PropertyContext(
    val schema: OpenApiSchema,
    val references: Map<String, OpenApiSchema>,
    val name: String
)