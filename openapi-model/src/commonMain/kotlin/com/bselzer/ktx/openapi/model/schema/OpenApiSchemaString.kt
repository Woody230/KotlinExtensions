package com.bselzer.ktx.openapi.model.schema

import com.bselzer.ktx.openapi.model.path.OpenApiEncodingName
import com.bselzer.ktx.openapi.model.path.OpenApiMediaTypeName

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

    /**
     * The contentMediaType keyword specifies the MIME type of the contents of a string, as described in RFC 2046.
     */
    val contentMediaType: OpenApiMediaTypeName?

    /**
     * The contentEncoding keyword specifies the encoding used to store the contents, as specified in RFC 2054, part 6.1 and RFC 4648.
     */
    val contentEncoding: OpenApiEncodingName?
}