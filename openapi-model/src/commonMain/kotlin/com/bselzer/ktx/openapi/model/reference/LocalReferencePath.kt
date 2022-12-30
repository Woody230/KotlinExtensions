package com.bselzer.ktx.openapi.model.reference

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Represents the path to a component within a document.
 */
data class LocalReferencePath internal constructor(
    /**
     * The type of component being referenced.
     */
    val component: OpenApiReferenceComponent,

    /**
     * The name of the component being referenced.
     */
    val componentName: String
) : OpenApiReferencePath {
    override fun toString(): String = "#/components/${Json.encodeToString(component)}/$componentName"
}