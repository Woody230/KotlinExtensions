package com.bselzer.ktx.openapi.model.schema

import com.bselzer.ktx.openapi.serialization.OpenApiSchemaSerializer

@kotlinx.serialization.Serializable(OpenApiSchemaSerializer::class)
sealed interface OpenApiSchema : OpenApiSchemaCore, OpenApiSchemaEnum<Any>, OpenApiSchemaComposition, OpenApiSchemaArray, OpenApiSchemaObject, OpenApiSchemaString,
    OpenApiSchemaNumeric<Double>, OpenApiSchemaStructure