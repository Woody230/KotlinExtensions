package com.bselzer.ktx.openapi.model.schema

import com.bselzer.ktx.openapi.model.reference.ReferenceOfOpenApiSchema

sealed interface OpenApiSchemaArray {
    /**
     * Set the items keyword to a single schema that will be used to validate all of the items in the array.
     */
    val items: ReferenceOfOpenApiSchema?

    /**
     * prefixItems is an array, where each item is a schema that corresponds to each index of the document’s array.
     */
    val prefixItems: List<ReferenceOfOpenApiSchema>

    /**
     * While the items schema must be valid for every item in the array, the contains schema only needs to validate against one or more items in the array.
     */
    val contains: ReferenceOfOpenApiSchema?

    /**
     * minContains and maxContains can be used with contains to further specify how many times a schema matches a contains constraint. These keywords can be any non-negative number including zero.
     */
    val minContains: Int?

    /**
     * minContains and maxContains can be used with contains to further specify how many times a schema matches a contains constraint. These keywords can be any non-negative number including zero.
     */
    val maxContains: Int?

    /**
     * The length of the array can be specified using the minItems and maxItems keywords. The value of each keyword must be a non-negative number.
     */
    val minItems: Int?

    /**
     * The length of the array can be specified using the minItems and maxItems keywords. The value of each keyword must be a non-negative number.
     */
    val maxItems: Int?

    /**
     * A schema can ensure that each of the items in an array is unique. Simply set the uniqueItems keyword to true.
     */
    val uniqueItems: Boolean?
}