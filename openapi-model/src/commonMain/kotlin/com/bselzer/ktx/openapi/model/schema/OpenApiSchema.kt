package com.bselzer.ktx.openapi.model.schema

interface OpenApiSchema : OpenApiSchemaCore, OpenApiSchemaEnum<Any>, OpenApiSchemaComposition, OpenApiSchemaArray, OpenApiSchemaObject, OpenApiSchemaString,
    OpenApiSchemaNumeric<Double>, OpenApiSchemaStructure