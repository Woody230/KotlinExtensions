package com.bselzer.ktx.openapi.model.reference

import com.bselzer.ktx.serialization.context.JsonContext.Default.decodeOrNull

private const val localReferencePathStart: String = "#/components"

fun String.toOpenApiReferencePath(): OpenApiReferencePath = let { value ->
    if (!value.contains(localReferencePathStart)) {
        return@let UnknownReferencePath(value)
    }

    // Should only have the component and component name.
    val localReferenceSegments = value.substringAfter(localReferencePathStart, "").split("/")
    if (localReferenceSegments.size != 2) {
        return@let UnknownReferencePath(value)
    }

    val component = localReferenceSegments[0].decodeOrNull<OpenApiReferenceComponent>() ?: return@let UnknownReferencePath(value)
    val componentName = localReferenceSegments[1]
    if (componentName.isBlank()) {
        return@let UnknownReferencePath(value)
    }

    val localReference = LocalReferencePath(component, componentName)
    val documentPath = value.substringBefore(localReferencePathStart, "")
    when {
        documentPath.isBlank() -> localReference
        else -> RemoteReferencePath(documentPath, localReference)
    }
}