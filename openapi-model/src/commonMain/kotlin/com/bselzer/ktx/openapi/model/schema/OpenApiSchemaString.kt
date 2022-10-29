package com.bselzer.ktx.openapi.model.schema

/**
 * The string type is used for strings of text. It may contain Unicode characters.
 */
sealed interface OpenApiSchemaString {
    /**
     * The length of a string can be constrained using the minLength and maxLength keywords. For both keywords, the value must be a non-negative number.
     */
    val minLength: Int?

    /**
     * The length of a string can be constrained using the minLength and maxLength keywords. For both keywords, the value must be a non-negative number.
     */
    val maxLength: Int?

    /**
     * The pattern keyword is used to restrict a string to a particular regular expression. The regular expression syntax is the one defined in JavaScript (ECMA 262 specifically) with Unicode support. See [Regular Expressions](https://json-schema.org/understanding-json-schema/reference/regular_expressions.html#regular-expressions) for more information.
     */
    val pattern: String?
}