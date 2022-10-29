package com.bselzer.ktx.openapi.model.schema

import com.bselzer.ktx.openapi.model.*

data class OpenApiSchemaComposite(
    // Common
    @Deprecated("The example property has been deprecated in favor of the JSON Schema examples keyword. Use of example is discouraged, and later versions of this specification may remove it.")
    override val example: OpenApiExampleValue? = null,
    override val examples: List<OpenApiExampleValue> = emptyList(),
    override val title: String? = null,
    override val description: OpenApiDescription? = null,
    override val readOnly: Boolean = false,
    override val writeOnly: Boolean = false,
    override val default: Any? = null,
    override val deprecated: Boolean = false,
    override val `$comment`: String? = null,
    override val types: Set<OpenApiSchemaType> = emptySet(),
    override val format: String? = null,
    override val externalDocs: OpenApiExternalDocumentation? = null,
    override val extensions: OpenApiExtensions = emptyMap(),

    // Enum
    override val enum: List<Any> = emptyList(),
    override val const: Any? = null,

    // Composition
    override val allOf: List<OpenApiSchema> = emptyList(),
    override val anyOf: List<OpenApiSchema> = emptyList(),
    override val oneOf: List<OpenApiSchema> = emptyList(),
    override val not: OpenApiSchema? = null,

    // Array
    override val items: OpenApiReferenceOf<OpenApiSchema>? = null,
    override val prefixItems: List<OpenApiReferenceOf<OpenApiSchema>> = emptyList(),
    override val contains: OpenApiReferenceOf<OpenApiSchema>? = null,
    override val minContains: Int? = null,
    override val maxContains: Int? = null,
    override val minItems: Int? = null,
    override val maxItems: Int? = null,
    override val uniqueItems: Boolean? = false,

    // Object
    override val discriminator: OpenApiDiscriminator? = null,
    override val xml: OpenApiXml? = null,
    override val properties: Map<OpenApiPropertyName, OpenApiReferenceOf<OpenApiSchema>> = emptyMap(),
    override val patternProperties: Map<OpenApiPropertyName, OpenApiReferenceOf<OpenApiSchema>> = emptyMap(),
    override val additionalProperties: OpenApiReferenceOf<OpenApiSchema>? = null,
    override val unevaluatedProperties: Boolean? = null,
    override val required: Set<OpenApiPropertyName> = emptySet(),
    override val dependentRequired: Map<OpenApiPropertyName, Set<OpenApiPropertyName>> = emptyMap(),
    override val dependentSchemas: Map<OpenApiPropertyName, OpenApiReferenceOf<OpenApiSchema>> = emptyMap(),
    override val propertyNames: Map<OpenApiPropertyName, String> = emptyMap(),
    override val minProperties: Int? = null,
    override val maxProperties: Int? = null,

    // String
    override val minLength: Int? = null,
    override val maxLength: Int? = null,
    override val pattern: String? = null,

    // Numeric
    override val multipleOf: Double? = null,
    override val minimum: Double? = null,
    override val exclusiveMinimum: Double? = null,
    override val maximum: Double? = null,
    override val exclusiveMaximum: Double? = null,
) : OpenApiSchema, OpenApiSchemaEnum<Any>, OpenApiSchemaComposition, OpenApiSchemaArray, OpenApiSchemaObject, OpenApiSchemaString, OpenApiSchemaNumeric<Double> {
    override val isNullable: Boolean = types.contains(OpenApiSchemaType.NULL)
}