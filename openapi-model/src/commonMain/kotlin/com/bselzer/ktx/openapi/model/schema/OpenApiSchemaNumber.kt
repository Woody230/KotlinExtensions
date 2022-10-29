package com.bselzer.ktx.openapi.model.schema

import com.bselzer.ktx.openapi.model.OpenApiDescription
import com.bselzer.ktx.openapi.model.OpenApiExampleValue
import com.bselzer.ktx.openapi.model.OpenApiExtensions
import com.bselzer.ktx.openapi.model.OpenApiExternalDocumentation

data class OpenApiSchemaNumber(
    @Deprecated("The example property has been deprecated in favor of the JSON Schema examples keyword. Use of example is discouraged, and later versions of this specification may remove it.")
    override val example: OpenApiExampleValue? = null,
    override val examples: List<OpenApiExampleValue> = emptyList(),
    override val title: String? = null,
    override val description: OpenApiDescription? = null,
    override val readOnly: Boolean = false,
    override val writeOnly: Boolean = false,
    override val default: Double? = null,
    override val deprecated: Boolean = false,
    override val `$comment`: String? = null,
    override val isNullable: Boolean = false,
    override val format: String? = null,
    override val externalDocs: OpenApiExternalDocumentation? = null,
    override val extensions: OpenApiExtensions = emptyMap(),

    override val multipleOf: Double? = null,
    override val minimum: Double? = null,
    override val exclusiveMinimum: Double? = null,
    override val maximum: Double? = null,
    override val exclusiveMaximum: Double? = null,

    override val enum: List<Double> = emptyList(),
    override val const: Double? = null,
) : OpenApiSchema, OpenApiSchemaEnum<Double>, OpenApiSchemaNumeric<Double> {
    override val types: Set<OpenApiSchemaType> = setOf(OpenApiSchemaType.NUMBER) + if (isNullable) setOf(OpenApiSchemaType.NULL) else emptySet()
}