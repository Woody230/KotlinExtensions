package com.bselzer.ktx.openapi.model.schema

import com.bselzer.ktx.openapi.model.*

data class OpenApiSchemaArray(
    @Deprecated("The example property has been deprecated in favor of the JSON Schema examples keyword. Use of example is discouraged, and later versions of this specification may remove it.")
    override val example: OpenApiExampleValue? = null,
    override val examples: List<OpenApiExampleValue> = emptyList(),
    override val title: String? = null,
    override val description: OpenApiDescription? = null,
    override val default: List<Any>? = null,
    override val deprecated: Boolean = false,
    override val `$comment`: String? = null,
    override val isNullable: Boolean = false,
    override val format: String? = null,
    override val externalDocs: OpenApiExternalDocumentation? = null,
    override val extensions: OpenApiExtensions = emptyMap(),

    /**
     * Set the items keyword to a single schema that will be used to validate all of the items in the array.
     */
    val items: OpenApiReferenceOf<OpenApiSchema>? = null,

    /**
     * prefixItems is an array, where each item is a schema that corresponds to each index of the document’s array.
     */
    val prefixItems: List<OpenApiReferenceOf<OpenApiSchema>> = emptyList(),

    /**
     * While the items schema must be valid for every item in the array, the contains schema only needs to validate against one or more items in the array.
     */
    val contains: OpenApiReferenceOf<OpenApiSchema>? = null,

    /**
     * minContains and maxContains can be used with contains to further specify how many times a schema matches a contains constraint. These keywords can be any non-negative number including zero.
     */
    val minContains: Int? = null,

    /**
     * minContains and maxContains can be used with contains to further specify how many times a schema matches a contains constraint. These keywords can be any non-negative number including zero.
     */
    val maxContains: Int? = null,

    /**
     * The length of the array can be specified using the minItems and maxItems keywords. The value of each keyword must be a non-negative number.
     */
    val minItems: Int? = null,

    /**
     * The length of the array can be specified using the minItems and maxItems keywords. The value of each keyword must be a non-negative number.
     */
    val maxItems: Int? = null,

    /**
     * A schema can ensure that each of the items in an array is unique. Simply set the uniqueItems keyword to true.
     */
    val uniqueItems: Boolean? = false
) : OpenApiSchema {
    override val types: Set<OpenApiSchemaType> = setOf(OpenApiSchemaType.ARRAY) + if (isNullable) setOf(OpenApiSchemaType.NULL) else emptySet()
}