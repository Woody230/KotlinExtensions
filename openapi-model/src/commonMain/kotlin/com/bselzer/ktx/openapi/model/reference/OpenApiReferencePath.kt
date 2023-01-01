package com.bselzer.ktx.openapi.model.reference

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Represents a path to a component within a document.
 */
data class OpenApiReferencePath(
    /**
     * The path to the document. The location may be local or remote.
     */
    val documentPath: String?,

    /**
     * The type of component being referenced.
     */
    val component: OpenApiReferenceComponent,

    /**
     * The name of the component being referenced.
     */
    val componentName: String
) {
    override fun toString(): String = "$documentPath#/components/${Json.encodeToString(component)}/$componentName"
}