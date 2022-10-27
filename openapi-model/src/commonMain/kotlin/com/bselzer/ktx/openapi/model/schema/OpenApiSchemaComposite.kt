package com.bselzer.ktx.openapi.model.schema

import com.bselzer.ktx.openapi.model.OpenApiDescription
import com.bselzer.ktx.openapi.model.OpenApiExampleValue
import com.bselzer.ktx.openapi.model.OpenApiExtensions
import com.bselzer.ktx.openapi.model.OpenApiExternalDocumentation

data class OpenApiSchemaComposite(
    override val example: OpenApiExampleValue? = null,
    override val examples: List<OpenApiExampleValue> = emptyList(),
    override val title: String? = null,
    override val description: OpenApiDescription? = null,
    override val default: Any? = null,
    override val readOnly: Boolean? = null,
    override val writeOnly: Boolean? = null,
    override val deprecated: Boolean? = null,
    override val `$comment`: String? = null,
    override val allOf: List<OpenApiSchema> = emptyList(),
    override val anyOf: List<OpenApiSchema> = emptyList(),
    override val oneOf: List<OpenApiSchema> = emptyList(),
    override val not: OpenApiSchema? = null,
    override val types: Set<OpenApiSchemaType> = emptySet(),
    override val format: String? = null,
    override val externalDocs: OpenApiExternalDocumentation? = null,
    override val enum: List<Any> = emptyList(),
    override val const: Any? = null,
    override val extensions: OpenApiExtensions = emptyMap(),
) : OpenApiSchema {
    override val isNullable: Boolean = types.contains(OpenApiSchemaType.NULL)
}