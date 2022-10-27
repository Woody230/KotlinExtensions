package com.bselzer.ktx.openapi.model.schema

import com.bselzer.ktx.openapi.model.OpenApiDescription
import com.bselzer.ktx.openapi.model.OpenApiExampleValue
import com.bselzer.ktx.openapi.model.OpenApiExternalDocumentation
import com.bselzer.ktx.openapi.model.base.OpenApiExtensible

sealed interface OpenApiSchema : OpenApiExtensible {
    val example: OpenApiExampleValue?
    val examples: List<OpenApiExampleValue>
    val title: String?
    val description: OpenApiDescription?
    val default: Any?
    val readOnly: Boolean?
    val writeOnly: Boolean?
    val deprecated: Boolean?
    val `$comment`: String?

    val allOf: List<OpenApiSchema>
    val anyOf: List<OpenApiSchema>
    val oneOf: List<OpenApiSchema>
    val not: OpenApiSchema?

    val isNullable: Boolean
    val types: Set<OpenApiSchemaType>
    val format: String?
    val externalDocs: OpenApiExternalDocumentation?
    val enum: List<Any>
    val const: Any?
}