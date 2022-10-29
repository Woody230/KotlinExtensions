package com.bselzer.ktx.openapi.model.schema

import com.bselzer.ktx.openapi.model.OpenApiSchemaReference
import com.bselzer.ktx.openapi.model.OpenApiXml

/**
 * Objects are the mapping type in JSON. They map “keys” to “values”. In JSON, the “keys” must always be strings. Each of these pairs is conventionally referred to as a “property”.
 */
sealed interface OpenApiSchemaObject {
    /**
     * Adds support for polymorphism. The discriminator is an object name that is used to differentiate between other schemas which may satisfy the payload description. See Composition and Inheritance for more details.
     */
    val discriminator: OpenApiDiscriminator?

    /**
     * This MAY be used only on properties schemas. It has no effect on root schemas. Adds additional metadata to describe the XML representation of this property.
     */
    val xml: OpenApiXml?

    /**
     * The properties (key-value pairs) on an object are defined using the properties keyword. The value of properties is an object, where each key is the name of a property and each value is a schema used to validate that property. Any property that doesn’t match any of the property names in the properties keyword is ignored by this keyword.
     */
    val properties: Map<OpenApiPropertyName, OpenApiSchemaReference>

    /**
     * Sometimes you want to say that, given a particular kind of property name, the value should match a particular schema. That’s where patternProperties comes in: it maps regular expressions to schemas. If a property name matches the given regular expression, the property value must validate against the corresponding schema.
     */
    val patternProperties: Map<OpenApiPropertyName, OpenApiSchemaReference>

    /**
     * The additionalProperties keyword is used to control the handling of extra stuff, that is, properties whose names are not listed in the properties keyword or match any of the regular expressions in the patternProperties keyword. By default any additional properties are allowed.
     *
     * The value of the additionalProperties keyword is a schema that will be used to validate any properties in the instance that are not matched by properties or patternProperties. Setting the additionalProperties schema to false means no additional properties will be allowed.
     */
    val additionalProperties: OpenApiSchemaReference?

    /**
     * he unevaluatedProperties keyword is similar to additionalProperties except that it can recognize properties declared in subschemas. So, the example from the previous section can be rewritten without the need to redeclare properties.
     */
    val unevaluatedProperties: Boolean?

    /**
     * By default, the properties defined by the properties keyword are not required. However, one can provide a list of required properties using the required keyword.
     *
     * The required keyword takes an array of zero or more strings. Each of these strings must be unique.
     */
    val required: Set<OpenApiPropertyName>

    /**
     * The dependentRequired keyword conditionally requires that certain properties must be present if a given property is present in an object.
     */
    val dependentRequired: Map<OpenApiPropertyName, Set<OpenApiPropertyName>>

    /**
     * The dependentSchemas keyword conditionally applies a subschema when a given property is present.
     */
    val dependentSchemas: Map<OpenApiPropertyName, OpenApiSchemaReference>

    /**
     * The names of properties can be validated against a schema, irrespective of their values. This can be useful if you don’t want to enforce specific properties, but you want to make sure that the names of those properties follow a specific convention. You might, for example, want to enforce that all names are valid ASCII tokens so they can be used as attributes in a particular programming language.
     */
    val propertyNames: OpenApiSchemaReference?

    /**
     * The number of properties on an object can be restricted using the minProperties and maxProperties keywords. Each of these must be a non-negative integer.
     */
    val minProperties: Int?

    /**
     * The number of properties on an object can be restricted using the minProperties and maxProperties keywords. Each of these must be a non-negative integer.
     */
    val maxProperties: Int?
}