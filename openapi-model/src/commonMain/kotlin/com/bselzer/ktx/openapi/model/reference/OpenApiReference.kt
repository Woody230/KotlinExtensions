package com.bselzer.ktx.openapi.model.reference

import com.bselzer.ktx.openapi.model.OpenApiDescription

/**
 * A simple object to allow referencing other components in the OpenAPI document, internally and externally.
 * The $ref string value contains a URI RFC3986, which identifies the location of the value being referenced.
 * See the rules for resolving Relative References.
 *
 * This object cannot be extended with additional properties and any properties added SHALL be ignored.
 * Note that this restriction on additional properties is a difference between Reference Objects and Schema Objects that contain a $ref keyword.
 */
data class OpenApiReference(
    /**
     * REQUIRED. The reference identifier. This MUST be in the form of a URI.
     */
    val `$ref`: OpenApiReferencePath,

    /**
     * A short summary which by default SHOULD override that of the referenced component. If the referenced object-type does not allow a summary field, then this field has no effect.
     */
    val summary: String? = null,

    /**
     * A description which by default SHOULD override that of the referenced component. CommonMark syntax MAY be used for rich text representation. If the referenced object-type does not allow a description field, then this field has no effect.
     */
    val description: OpenApiDescription? = null
)