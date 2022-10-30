package com.bselzer.ktx.openapi.model.schema

import com.bselzer.ktx.openapi.model.OpenApiReferenceIdentifier

sealed interface OpenApiSchemaStructure {
    /**
     * You can set the base URI by using the $id keyword at the root of the schema. The value of $id is a URI-reference without a fragment that resolves against the Retrieval URI. The resulting URI is the base URI for the schema.
     */
    val `$id`: OpenApiReferenceIdentifier?

    /**
     * A less common way to identify a subschema is to create a named anchor in the schema using the $anchor keyword and using that name in the URI fragment. Anchors must start with a letter followed by any number of letters, digits, -, _, :, or ..
     */
    val `$anchor`: OpenApiReferenceIdentifier?

    /**
     * Sometimes we have small subschemas that are only intended for use in the current schema and it doesn’t make sense to define them as separate schemas. Although we can identify any subschema using JSON Pointers or named anchors, the $defs keyword gives us a standardized place to keep subschemas intended for reuse in the current schema document.
     */
    val `$defs`: Map<OpenApiReferenceIdentifier, OpenApiSchema>
}