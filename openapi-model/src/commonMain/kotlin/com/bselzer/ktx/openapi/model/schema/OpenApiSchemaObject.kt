package com.bselzer.ktx.openapi.model.schema

import com.bselzer.ktx.openapi.model.*

/**
 * Objects are the mapping type in JSON. They map “keys” to “values”. In JSON, the “keys” must always be strings. Each of these pairs is conventionally referred to as a “property”.
 */
data class OpenApiSchemaObject(
    @Deprecated("The example property has been deprecated in favor of the JSON Schema examples keyword. Use of example is discouraged, and later versions of this specification may remove it.")
    override val example: OpenApiExampleValue? = null,
    override val examples: List<OpenApiExampleValue> = emptyList(),
    override val title: String? = null,
    override val description: OpenApiDescription? = null,
    override val default: Any? = null,
    override val deprecated: Boolean = false,
    override val `$comment`: String? = null,
    override val allOf: List<OpenApiSchema> = emptyList(),
    override val anyOf: List<OpenApiSchema> = emptyList(),
    override val oneOf: List<OpenApiSchema> = emptyList(),
    override val not: OpenApiSchema? = null,
    override val isNullable: Boolean = false,
    override val format: String? = null,
    override val externalDocs: OpenApiExternalDocumentation? = null,
    override val enum: List<Any> = emptyList(),
    override val const: Any? = null,
    override val extensions: OpenApiExtensions = emptyMap(),

    /**
     * Relevant only for Schema "properties" definitions. Declares the property as “read only”. This means that it MAY be sent as part of a response but SHOULD NOT be sent as part of the request. If the property is marked as readOnly being true and is in the required list, the required will take effect on the response only. A property MUST NOT be marked as both readOnly and writeOnly being true. Default value is false.
     */
    val readOnly: Boolean = false,

    /**
     * Relevant only for Schema "properties" definitions. Declares the property as “write only”. Therefore, it MAY be sent as part of a request but SHOULD NOT be sent as part of the response. If the property is marked as writeOnly being true and is in the required list, the required will take effect on the request only. A property MUST NOT be marked as both readOnly and writeOnly being true. Default value is false.
     */
    val writeOnly: Boolean = false,

    /**
     * Adds support for polymorphism. The discriminator is an object name that is used to differentiate between other schemas which may satisfy the payload description. See Composition and Inheritance for more details.
     */
    val discriminator: OpenApiDiscriminator? = null,

    /**
     * This MAY be used only on properties schemas. It has no effect on root schemas. Adds additional metadata to describe the XML representation of this property.
     */
    val xml: OpenApiXml? = null,

    /**
     * The properties (key-value pairs) on an object are defined using the properties keyword. The value of properties is an object, where each key is the name of a property and each value is a schema used to validate that property. Any property that doesn’t match any of the property names in the properties keyword is ignored by this keyword.
     */
    val properties: Map<OpenApiParameterName, OpenApiReferenceOf<OpenApiSchema>> = emptyMap(),

    /**
     * Sometimes you want to say that, given a particular kind of property name, the value should match a particular schema. That’s where patternProperties comes in: it maps regular expressions to schemas. If a property name matches the given regular expression, the property value must validate against the corresponding schema.
     */
    val patternProperties: Map<OpenApiParameterName, OpenApiReferenceOf<OpenApiSchema>> = emptyMap(),

    /**
     * The additionalProperties keyword is used to control the handling of extra stuff, that is, properties whose names are not listed in the properties keyword or match any of the regular expressions in the patternProperties keyword. By default any additional properties are allowed.
     *
     * The value of the additionalProperties keyword is a schema that will be used to validate any properties in the instance that are not matched by properties or patternProperties. Setting the additionalProperties schema to false means no additional properties will be allowed.
     */
    val additionalProperties: OpenApiReferenceOf<OpenApiSchema>? = null,

    /**
     * he unevaluatedProperties keyword is similar to additionalProperties except that it can recognize properties declared in subschemas. So, the example from the previous section can be rewritten without the need to redeclare properties.
     */
    val unevaluatedProperties: Boolean? = null,

    /**
     * By default, the properties defined by the properties keyword are not required. However, one can provide a list of required properties using the required keyword.
     *
     * The required keyword takes an array of zero or more strings. Each of these strings must be unique.
     */
    val required: Set<OpenApiParameterName> = emptySet(),

    /**
     * The dependentRequired keyword conditionally requires that certain properties must be present if a given property is present in an object.
     */
    val dependentRequired: Map<OpenApiParameterName, Set<OpenApiParameterName>> = emptyMap(),

    /**
     * The dependentSchemas keyword conditionally applies a subschema when a given property is present.
     */
    val dependentSchemas: Map<OpenApiParameterName, OpenApiReferenceOf<OpenApiSchema>> = emptyMap(),

    /**
     * The names of properties can be validated against a schema, irrespective of their values. This can be useful if you don’t want to enforce specific properties, but you want to make sure that the names of those properties follow a specific convention. You might, for example, want to enforce that all names are valid ASCII tokens so they can be used as attributes in a particular programming language.
     */
    val propertyNames: Map<OpenApiParameterName, String> = emptyMap(),

    /**
     * The number of properties on an object can be restricted using the minProperties and maxProperties keywords. Each of these must be a non-negative integer.
     */
    val minProperties: Int? = null,

    /**
     * The number of properties on an object can be restricted using the minProperties and maxProperties keywords. Each of these must be a non-negative integer.
     */
    val maxProperties: Int? = null,
) : OpenApiSchema {
    override val types: Set<OpenApiSchemaType> = setOf(OpenApiSchemaType.OBJECT) + if (isNullable) setOf(OpenApiSchemaType.NULL) else emptySet()
}