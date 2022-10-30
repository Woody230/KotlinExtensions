package com.bselzer.ktx.openapi.model.schema

sealed interface OpenApiSchema : OpenApiSchemaCore, OpenApiSchemaEnum<Any>, OpenApiSchemaComposition, OpenApiSchemaArray, OpenApiSchemaObject, OpenApiSchemaString,
    OpenApiSchemaNumeric<Double>, OpenApiSchemaStructure