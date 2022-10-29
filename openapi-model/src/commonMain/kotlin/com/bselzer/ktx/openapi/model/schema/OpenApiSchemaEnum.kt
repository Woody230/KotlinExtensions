package com.bselzer.ktx.openapi.model.schema

sealed interface OpenApiSchemaEnum<T> {
    /**
     * The enum keyword is used to restrict a value to a fixed set of values where each element is unique.
     */
    val enum: List<T>?

    /**
     * The const keyword is used to restrict a value to a single value.
     */
    val const: T?
}