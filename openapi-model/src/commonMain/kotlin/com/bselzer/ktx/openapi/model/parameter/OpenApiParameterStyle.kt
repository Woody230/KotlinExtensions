package com.bselzer.ktx.openapi.model.parameter

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * In order to support common ways of serializing simple parameters, a set of style values are defined.
 */
@Serializable
enum class OpenApiParameterStyle {
    /**
     * Path-style parameters defined by RFC6570.
     *
     * Data types: primitive, array, object
     *
     * In: path
     */
    @SerialName("matrix")
    MATRIX,

    /**
     * Label style parameters defined by RFC6570
     *
     * Data types: primitive, array, object
     *
     * In: path
     */
    @SerialName("label")
    LABEL,

    /**
     * Form style parameters defined by RFC6570. This option replaces collectionFormat with a csv (when explode is false) or multi (when explode is true) value from OpenAPI 2.0.
     *
     * Data types: primitive, array, object
     *
     * In: query, cookie
     */
    @SerialName("form")
    FORM,

    /**
     * Simple style parameters defined by RFC6570. This option replaces collectionFormat with a csv value from OpenAPI 2.0.
     *
     * Data types: array
     *
     * In: path, header
     */
    @SerialName("simple")
    SIMPLE,

    /**
     * Space separated array or object values. This option replaces collectionFormat equal to ssv from OpenAPI 2.0.
     *
     * Data types: array, object
     *
     * In: query
     */
    @SerialName("spaceDelimited")
    SPACE_DELIMITED,

    /**
     * Pipe separated array or object values. This option replaces collectionFormat equal to pipes from OpenAPI 2.0.
     *
     * Data types: array, object
     *
     * In: query
     */
    @SerialName("pipeDelimited")
    PIPE_DELIMITED,

    /**
     * Provides a simple way of rendering nested objects using form parameters.
     *
     * Data types: object
     *
     * In: query
     */
    @SerialName("deepObject")
    DEEP_OBJECT
}