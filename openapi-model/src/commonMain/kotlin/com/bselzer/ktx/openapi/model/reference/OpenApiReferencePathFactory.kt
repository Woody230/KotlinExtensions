package com.bselzer.ktx.openapi.model.reference

import com.bselzer.ktx.serialization.context.JsonContext.Default.decodeOrNull

private const val localReferencePathStart: String = "#/components"

fun String.toOpenApiReferencePath(): OpenApiReferencePath = let { value ->
    if (!value.contains(localReferencePathStart)) {
        throw IllegalArgumentException("Local reference path missing in `$this`.")
    }

    // Should only have the component and component name.
    val localReferenceSegments = value.substringAfter(localReferencePathStart, "").split("/")
    if (localReferenceSegments.size != 2) {
        throw IllegalArgumentException("Expecting exactly 2 components within the local reference path in `$this`.")
    }

    val component = localReferenceSegments[0].decodeOrNull<OpenApiReferenceComponent>()
        ?: throw IllegalArgumentException("Local reference path component must be one of `${OpenApiReferenceComponent.values().joinToString()}` in path `$this`.")

    val componentName = localReferenceSegments[1]
    if (componentName.isBlank()) {
        throw IllegalArgumentException("Local reference path component name is not provided in `$this`.")
    }

    var documentPath: String? = value.substringBefore(localReferencePathStart, "")
    documentPath = documentPath?.ifBlank { null }

    OpenApiReferencePath(documentPath, component, componentName)
}