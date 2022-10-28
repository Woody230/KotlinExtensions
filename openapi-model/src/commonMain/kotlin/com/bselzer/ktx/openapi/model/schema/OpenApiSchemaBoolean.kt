package com.bselzer.ktx.openapi.model.schema

import com.bselzer.ktx.openapi.model.OpenApiDescription
import com.bselzer.ktx.openapi.model.OpenApiExampleValue
import com.bselzer.ktx.openapi.model.OpenApiExtensions
import com.bselzer.ktx.openapi.model.OpenApiExternalDocumentation

data class OpenApiSchemaBoolean(
    @Deprecated("The example property has been deprecated in favor of the JSON Schema examples keyword. Use of example is discouraged, and later versions of this specification may remove it.")
    override val example: OpenApiExampleValue? = null,
    override val examples: List<OpenApiExampleValue> = emptyList(),
    override val title: String? = null,
    override val description: OpenApiDescription? = null,
    override val default: Boolean? = null,
    override val deprecated: Boolean = false,
    override val `$comment`: String? = null,
    override val allOf: List<OpenApiSchema> = emptyList(),
    override val anyOf: List<OpenApiSchema> = emptyList(),
    override val oneOf: List<OpenApiSchema> = emptyList(),
    override val not: OpenApiSchema? = null,
    override val isNullable: Boolean = false,
    override val format: String? = null,
    override val externalDocs: OpenApiExternalDocumentation? = null,
    override val enum: List<Boolean> = emptyList(),
    override val const: Boolean? = null,
    override val extensions: OpenApiExtensions = emptyMap(),
) : OpenApiSchema {
    override val types: Set<OpenApiSchemaType> = setOf(OpenApiSchemaType.BOOLEAN) + if (isNullable) setOf(OpenApiSchemaType.NULL) else emptySet()
}