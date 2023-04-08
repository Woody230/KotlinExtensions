package com.bselzer.ktx.openapi.model.schema

import com.bselzer.ktx.openapi.model.OpenApiDescription
import com.bselzer.ktx.openapi.model.OpenApiExtensions
import com.bselzer.ktx.openapi.model.OpenApiExternalDocumentation
import com.bselzer.ktx.openapi.model.path.OpenApiEncodingName
import com.bselzer.ktx.openapi.model.path.OpenApiMediaTypeName
import com.bselzer.ktx.openapi.model.reference.ReferenceOfOpenApiSchema
import com.bselzer.ktx.openapi.model.value.OpenApiValue

data class OpenApiSchema(
    // Common
    @Deprecated("The example property has been deprecated in favor of the JSON Schema examples keyword. Use of example is discouraged, and later versions of this specification may remove it.")
    override val example: OpenApiValue? = null,
    override val examples: List<OpenApiValue> = emptyList(),
    override val title: String? = null,
    override val description: OpenApiDescription? = null,
    override val readOnly: Boolean? = null,
    override val writeOnly: Boolean? = null,
    override val default: OpenApiValue? = null,
    override val deprecated: Boolean? = null,
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
    override val items: ReferenceOfOpenApiSchema? = null,
    override val prefixItems: List<ReferenceOfOpenApiSchema> = emptyList(),
    override val contains: ReferenceOfOpenApiSchema? = null,
    override val minContains: Int? = null,
    override val maxContains: Int? = null,
    override val minItems: Int? = null,
    override val maxItems: Int? = null,
    override val uniqueItems: Boolean? = null,

    // Object
    override val discriminator: OpenApiDiscriminator? = null,
    override val xml: OpenApiXml? = null,
    override val properties: Map<OpenApiPropertyName, ReferenceOfOpenApiSchema> = emptyMap(),
    override val patternProperties: Map<OpenApiPropertyName, ReferenceOfOpenApiSchema> = emptyMap(),
    override val additionalProperties: ReferenceOfOpenApiSchema? = null,
    override val unevaluatedProperties: Boolean? = null,
    override val required: Set<OpenApiPropertyName> = emptySet(),
    override val dependentRequired: Map<OpenApiPropertyName, Set<OpenApiPropertyName>> = emptyMap(),
    override val dependentSchemas: Map<OpenApiPropertyName, ReferenceOfOpenApiSchema> = emptyMap(),
    override val propertyNames: ReferenceOfOpenApiSchema? = null,
    override val minProperties: Int? = null,
    override val maxProperties: Int? = null,

    // String
    override val minLength: Int? = null,
    override val maxLength: Int? = null,
    override val pattern: String? = null,
    override val contentMediaType: OpenApiMediaTypeName? = null,
    override val contentEncoding: OpenApiEncodingName? = null,

    // Numeric
    override val multipleOf: Number? = null,
    override val minimum: Number? = null,
    override val exclusiveMinimum: Number? = null,
    override val maximum: Number? = null,
    override val exclusiveMaximum: Number? = null,
) : OpenApiSchemaCore, OpenApiSchemaEnum<OpenApiValue>, OpenApiSchemaComposition, OpenApiSchemaArray, OpenApiSchemaObject, OpenApiSchemaString, OpenApiSchemaNumeric<Number> {
    override val isNullable: Boolean = types.contains(OpenApiSchemaType.NULL)

    /**
     * Returns a new schema that is a combination of this schema and the given [other] schema.
     *
     * [other] presentation's null or inherit properties are replaced with the non-null properties of this schema.
     * Another way to think of it is that the "missing" properties of the [other] presentation are filled by the properties of this schema
     */
    fun merge(other: OpenApiSchema) = copy(
        // Common
        example = other.example ?: this.example,
        examples = other.examples + this.examples,
        title = other.title ?: this.title,
        description = other.description ?: this.description,
        readOnly = other.readOnly ?: this.readOnly,
        writeOnly = other.writeOnly ?: this.writeOnly,
        default = other.default ?: this.default,
        deprecated = other.deprecated ?: this.deprecated,
        `$comment` = other.`$comment` ?: this.`$comment`,
        types = other.types + this.types,
        format = other.format ?: this.format,
        externalDocs = other.externalDocs ?: this.externalDocs,
        extensions = other.extensions + this.extensions,

        // Enum
        enum = other.enum + this.enum,
        const = other.const ?: this.const,

        // Composition
        allOf = other.allOf + this.allOf,
        anyOf = other.anyOf + this.anyOf,
        oneOf = other.oneOf + this.oneOf,
        not = other.not ?: this.not,
        `if` = other.`if` ?: this.`if`,
        then = other.then ?: this.then,
        `else` = other.`else` ?: this.`else`,

        // Array
        items = other.items ?: this.items,
        prefixItems = other.prefixItems + this.prefixItems,
        contains = other.contains ?: this.contains,
        minContains = other.minContains ?: this.minContains,
        maxContains = other.maxContains ?: this.maxContains,
        minItems = other.minItems ?: this.minItems,
        maxItems = other.maxItems ?: this.maxItems,
        uniqueItems = other.uniqueItems ?: this.uniqueItems,

        // Object
        discriminator = other.discriminator ?: this.discriminator,
        xml = other.xml ?: this.xml,
        properties = other.properties + this.properties,
        patternProperties = other.patternProperties + this.patternProperties,
        additionalProperties = other.additionalProperties ?: this.additionalProperties,
        unevaluatedProperties = other.unevaluatedProperties ?: this.unevaluatedProperties,
        required = other.required + this.required,
        dependentRequired = other.dependentRequired + this.dependentRequired,
        dependentSchemas = other.dependentSchemas + this.dependentSchemas,
        propertyNames = other.propertyNames ?: this.propertyNames,
        minProperties = other.minProperties ?: this.minProperties,
        maxProperties = other.maxProperties ?: this.maxProperties,

        // String
        minLength = other.minLength ?: this.minLength,
        maxLength = other.maxLength ?: this.maxLength,
        pattern = other.pattern ?: this.pattern,
        contentMediaType = other.contentMediaType ?: this.contentMediaType,
        contentEncoding = other.contentEncoding ?: this.contentEncoding,

        // Numeric
        multipleOf = other.multipleOf ?: this.multipleOf,
        minimum = other.minimum ?: this.minimum,
        exclusiveMinimum = other.exclusiveMinimum ?: this.exclusiveMinimum,
        maximum = other.maximum ?: this.maximum,
        exclusiveMaximum = other.exclusiveMaximum ?: this.exclusiveMaximum
    )
}