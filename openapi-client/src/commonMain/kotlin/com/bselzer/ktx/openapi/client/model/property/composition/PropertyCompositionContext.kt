package com.bselzer.ktx.openapi.client.model.property.composition

import com.bselzer.ktx.codegen.model.property.Property
import com.bselzer.ktx.openapi.model.schema.OpenApiSchema

data class PropertyCompositionContext(
    val property: Property,
    val schema: OpenApiSchema,
    val references: Map<String, OpenApiSchema>,
)