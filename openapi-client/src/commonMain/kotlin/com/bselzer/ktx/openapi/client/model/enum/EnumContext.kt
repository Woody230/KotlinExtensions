package com.bselzer.ktx.openapi.client.model.enum

import com.bselzer.ktx.openapi.model.schema.OpenApiSchema

data class EnumContext(
    val schema: OpenApiSchema,
    val references: Map<String, OpenApiSchema>,
    val name: String
)