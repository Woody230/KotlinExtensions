package com.bselzer.ktx.openapi.model.reference

/**
 * Represents a path to a component for a document that is in a designated location.
 */
data class RemoteReferencePath internal constructor(
    /**
     * The path to the document. The location may be local or remote.
     */
    val documentPath: String,

    /**
     * The path to a component within the document.
     */
    val localReferencePath: LocalReferencePath,
) : OpenApiReferencePath {
    override fun toString(): String = "$documentPath$localReferencePath"
}