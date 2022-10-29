package com.bselzer.ktx.openapi.model.schema

import com.bselzer.ktx.openapi.model.OpenApiDescription
import com.bselzer.ktx.openapi.model.OpenApiExampleValue
import com.bselzer.ktx.openapi.model.OpenApiExtensions
import com.bselzer.ktx.openapi.model.OpenApiExternalDocumentation

/**
 * The string type is used for strings of text. It may contain Unicode characters.
 */
data class OpenApiSchemaString(
    @Deprecated("The example property has been deprecated in favor of the JSON Schema examples keyword. Use of example is discouraged, and later versions of this specification may remove it.")
    override val example: OpenApiExampleValue? = null,
    override val examples: List<OpenApiExampleValue> = emptyList(),
    override val title: String? = null,
    override val description: OpenApiDescription? = null,
    override val default: String? = null,
    override val deprecated: Boolean = false,
    override val `$comment`: String? = null,
    override val isNullable: Boolean = false,
    override val format: String? = null,
    override val externalDocs: OpenApiExternalDocumentation? = null,
    override val enum: List<String> = emptyList(),
    override val const: String? = null,
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
) : OpenApiSchema, OpenApiSchemaEnum<String> {
    override val types: Set<OpenApiSchemaType> = setOf(OpenApiSchemaType.STRING) + if (isNullable) setOf(OpenApiSchemaType.NULL) else emptySet()
}