package com.bselzer.ktx.openapi.model.schema

import com.bselzer.ktx.openapi.model.OpenApiDescription
import com.bselzer.ktx.openapi.model.OpenApiExampleValue
import com.bselzer.ktx.openapi.model.OpenApiExtensions
import com.bselzer.ktx.openapi.model.OpenApiExternalDocumentation

data class OpenApiSchemaInteger(
    @Deprecated("The example property has been deprecated in favor of the JSON Schema examples keyword. Use of example is discouraged, and later versions of this specification may remove it.")
    override val example: OpenApiExampleValue? = null,
    override val examples: List<OpenApiExampleValue> = emptyList(),
    override val title: String? = null,
    override val description: OpenApiDescription? = null,
    override val readOnly: Boolean = false,
    override val writeOnly: Boolean = false,
    override val default: Int? = null,
    override val deprecated: Boolean = false,
    override val `$comment`: String? = null,
    override val isNullable: Boolean = false,
    override val format: String? = null,
    override val externalDocs: OpenApiExternalDocumentation? = null,
    override val extensions: OpenApiExtensions = emptyMap(),

    override val multipleOf: Int? = null,
    override val minimum: Int? = null,
    override val exclusiveMinimum: Int? = null,
    override val maximum: Int? = null,
    override val exclusiveMaximum: Int? = null,

    override val enum: List<Int> = emptyList(),
    override val const: Int? = null,
) : OpenApiSchema, OpenApiSchemaEnum<Int>, OpenApiSchemaNumeric<Int> {
    override val types: Set<OpenApiSchemaType> = setOf(OpenApiSchemaType.INTEGER) + if (isNullable) setOf(OpenApiSchemaType.NULL) else emptySet()
}