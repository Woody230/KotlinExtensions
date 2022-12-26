package com.bselzer.ktx.openapi.model.schema

import com.bselzer.ktx.openapi.model.*
import com.bselzer.ktx.openapi.model.path.OpenApiEncodingName
import com.bselzer.ktx.openapi.model.path.OpenApiMediaTypeName
import com.bselzer.ktx.openapi.model.value.OpenApiValue

data class OpenApiSchemaComposite(
    // Common
    @Deprecated("The example property has been deprecated in favor of the JSON Schema examples keyword. Use of example is discouraged, and later versions of this specification may remove it.")
    override val example: OpenApiValue? = null,
    override val examples: List<OpenApiValue> = emptyList(),
    override val title: String? = null,
    override val description: OpenApiDescription? = null,
    override val readOnly: Boolean = false,
    override val writeOnly: Boolean = false,
    override val default: OpenApiValue? = null,
    override val deprecated: Boolean = false,
    override val `$comment`: String? = null,
    override val types: Set<OpenApiSchemaType> = emptySet(),
    override val format: String? = null,
    override val externalDocs: OpenApiExternalDocumentation? = null,
    override val extensions: OpenApiExtensions = emptyMap(),

    // Enum
    override val enum: List<OpenApiValue> = emptyList(),
    override val const: OpenApiValue? = null,

    // Composition
    override val allOf: List<OpenApiSchema> = emptyList(),
    override val anyOf: List<OpenApiSchema> = emptyList(),
    override val oneOf: List<OpenApiSchema> = emptyList(),
    override val not: OpenApiSchema? = null,
    override val `if`: OpenApiSchema? = null,
    override val then: OpenApiSchema? = null,
    override val `else`: OpenApiSchema? = null,

    // Array
    override val items: OpenApiSchemaReference? = null,
    override val prefixItems: List<OpenApiSchemaReference> = emptyList(),
    override val contains: OpenApiSchemaReference? = null,
    override val minContains: Int? = null,
    override val maxContains: Int? = null,
    override val minItems: Int? = null,
    override val maxItems: Int? = null,
    override val uniqueItems: Boolean? = null,

    // Object
    override val discriminator: OpenApiDiscriminator? = null,
    override val xml: OpenApiXml? = null,
    override val properties: Map<OpenApiPropertyName, OpenApiSchemaReference> = emptyMap(),
    override val patternProperties: Map<OpenApiPropertyName, OpenApiSchemaReference> = emptyMap(),
    override val additionalProperties: OpenApiSchemaReference? = null,
    override val unevaluatedProperties: Boolean? = null,
    override val required: Set<OpenApiPropertyName> = emptySet(),
    override val dependentRequired: Map<OpenApiPropertyName, Set<OpenApiPropertyName>> = emptyMap(),
    override val dependentSchemas: Map<OpenApiPropertyName, OpenApiSchemaReference> = emptyMap(),
    override val propertyNames: OpenApiSchemaReference? = null,
    override val minProperties: Int? = null,
    override val maxProperties: Int? = null,

    // String
    override val minLength: Int? = null,
    override val maxLength: Int? = null,
    override val pattern: String? = null,
    override val contentMediaType: OpenApiMediaTypeName? = null,
    override val contentEncoding: OpenApiEncodingName? = null,

    // Numeric
    override val multipleOf: Double? = null,
    override val minimum: Double? = null,
    override val exclusiveMinimum: Double? = null,
    override val maximum: Double? = null,
    override val exclusiveMaximum: Double? = null,

    // Structure
    override val `$id`: OpenApiReferenceIdentifier? = null,
    override val `$anchor`: OpenApiReferenceIdentifier? = null,
    override val `$defs`: Map<OpenApiReferenceIdentifier, OpenApiSchema> = emptyMap()
) : OpenApiSchema {
    override val isNullable: Boolean = types.contains(OpenApiSchemaType.NULL)
}