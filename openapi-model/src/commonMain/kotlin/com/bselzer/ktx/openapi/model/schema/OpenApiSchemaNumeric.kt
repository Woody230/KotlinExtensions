package com.bselzer.ktx.openapi.model.schema

sealed interface OpenApiSchemaNumeric<T : Number> {
    /**
     * Numbers can be restricted to a multiple of a given number, using the multipleOf keyword. It may be set to any positive number.
     */
    val multipleOf: T?

    /**
     * Ranges of numbers are specified using a combination of the minimum and maximum keywords, (or exclusiveMinimum and exclusiveMaximum for expressing exclusive range).
     */
    val minimum: T?

    /**
     * Ranges of numbers are specified using a combination of the minimum and maximum keywords, (or exclusiveMinimum and exclusiveMaximum for expressing exclusive range).
     */
    val exclusiveMinimum: T?

    /**
     * Ranges of numbers are specified using a combination of the minimum and maximum keywords, (or exclusiveMinimum and exclusiveMaximum for expressing exclusive range).
     */
    val maximum: T?

    /**
     * Ranges of numbers are specified using a combination of the minimum and maximum keywords, (or exclusiveMinimum and exclusiveMaximum for expressing exclusive range).
     */
    val exclusiveMaximum: T?
}