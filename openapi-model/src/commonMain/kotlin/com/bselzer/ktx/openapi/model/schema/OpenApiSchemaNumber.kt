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
    override val default: Double? = null,
    override val deprecated: Boolean = false,
    override val `$comment`: String? = null,
    override val allOf: List<OpenApiSchema> = emptyList(),
    override val anyOf: List<OpenApiSchema> = emptyList(),
    override val oneOf: List<OpenApiSchema> = emptyList(),
    override val not: OpenApiSchema? = null,
    override val isNullable: Boolean = false,
    override val format: String? = null,
    override val externalDocs: OpenApiExternalDocumentation? = null,
    override val enum: List<Double> = emptyList(),
    override val const: Double? = null,
    override val extensions: OpenApiExtensions = emptyMap(),

    /**
     * Numbers can be restricted to a multiple of a given number, using the multipleOf keyword. It may be set to any positive number.
     */
    val multipleOf: Double? = null,

    /**
     * Ranges of numbers are specified using a combination of the minimum and maximum keywords, (or exclusiveMinimum and exclusiveMaximum for expressing exclusive range).
     */
    val minimum: Double? = null,

    /**
     * Ranges of numbers are specified using a combination of the minimum and maximum keywords, (or exclusiveMinimum and exclusiveMaximum for expressing exclusive range).
     */
    val exclusiveMinimum: Double? = null,

    /**
     * Ranges of numbers are specified using a combination of the minimum and maximum keywords, (or exclusiveMinimum and exclusiveMaximum for expressing exclusive range).
     */
    val maximum: Double? = null,

    /**
     * Ranges of numbers are specified using a combination of the minimum and maximum keywords, (or exclusiveMinimum and exclusiveMaximum for expressing exclusive range).
     */
    val exclusiveMaximum: Double? = null
) : OpenApiSchema {
    override val types: Set<OpenApiSchemaType> = setOf(OpenApiSchemaType.NUMBER) + if (isNullable) setOf(OpenApiSchemaType.NULL) else emptySet()
}