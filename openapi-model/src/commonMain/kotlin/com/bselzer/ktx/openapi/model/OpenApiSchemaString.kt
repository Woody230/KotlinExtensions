package com.bselzer.ktx.openapi.model

/**
 * The string type is used for strings of text. It may contain Unicode characters.
 */
data class OpenApiSchemaString(
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
    override val types: Set<OpenApiSchemaType> = setOf(OpenApiSchemaType.STRING),
    override val format: String? = null,
    override val externalDocs: OpenApiExternalDocumentation? = null,
    override val enum: List<Any> = emptyList(),
    override val const: Any? = null,
    override val extensions: OpenApiExtensions = emptyMap(),

    /**
     * The length of a string can be constrained using the minLength and maxLength keywords. For both keywords, the value must be a non-negative number.
     */
    val minLength: Int? = null,

    /**
     * The length of a string can be constrained using the minLength and maxLength keywords. For both keywords, the value must be a non-negative number.
     */
    val maxLength: Int? = null,

    /**
     * The pattern keyword is used to restrict a string to a particular regular expression. The regular expression syntax is the one defined in JavaScript (ECMA 262 specifically) with Unicode support. See [Regular Expressions](https://json-schema.org/understanding-json-schema/reference/regular_expressions.html#regular-expressions) for more information.
     */
    val pattern: String? = null
) : OpenApiSchema