package com.bselzer.ktx.openapi.model.schema

sealed interface OpenApiSchemaComposition {
    /**
     * Must be valid against all of the subschemas.
     */
    val allOf: List<OpenApiSchema>

    /**
     * Must be valid against any of the subschemas
     */
    val anyOf: List<OpenApiSchema>

    /**
     * Must be valid against exactly one of the subschemas
     */
    val oneOf: List<OpenApiSchema>

    /**
     * Must not be valid against the given schema
     */
    val not: OpenApiSchema?
}