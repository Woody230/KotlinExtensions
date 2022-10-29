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

    /**
     * The if, then and else keywords allow the application of a subschema based on the outcome of another schema, much like the if/then/else constructs you’ve probably seen in traditional programming languages.
     *
     * If if is valid, then must also be valid (and else is ignored.) If if is invalid, else must also be valid (and then is ignored).
     *
     * If then or else is not defined, if behaves as if they have a value of true.
     *
     * If then and/or else appear in a schema without if, then and else are ignored.
     */
    val `if`: OpenApiSchema?

    /**
     * The if, then and else keywords allow the application of a subschema based on the outcome of another schema, much like the if/then/else constructs you’ve probably seen in traditional programming languages.
     *
     * If if is valid, then must also be valid (and else is ignored.) If if is invalid, else must also be valid (and then is ignored).
     *
     * If then or else is not defined, if behaves as if they have a value of true.
     *
     * If then and/or else appear in a schema without if, then and else are ignored.
     */
    val then: OpenApiSchema?

    /**
     * The if, then and else keywords allow the application of a subschema based on the outcome of another schema, much like the if/then/else constructs you’ve probably seen in traditional programming languages.
     *
     * If if is valid, then must also be valid (and else is ignored.) If if is invalid, else must also be valid (and then is ignored).
     *
     * If then or else is not defined, if behaves as if they have a value of true.
     *
     * If then and/or else appear in a schema without if, then and else are ignored.
     */
    val `else`: OpenApiSchema?
}