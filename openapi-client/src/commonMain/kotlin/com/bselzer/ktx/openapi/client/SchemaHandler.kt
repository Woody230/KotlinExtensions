package com.bselzer.ktx.openapi.client

import com.bselzer.ktx.openapi.model.schema.OpenApiSchema

sealed interface SchemaHandler {
    fun canResolve(schema: OpenApiSchema): Boolean
    fun resolve(schema: OpenApiSchema): SchemaOutput
}