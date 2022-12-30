package com.bselzer.ktx.openapi.model.reference

import com.bselzer.ktx.serialization.context.JsonContext.Default.decodeOrNull

class OpenApiReferencePathFactory(private val value: String) {
    private companion object {
        const val localReferencePathStart = "#/components/"
    }

    val referencePath: OpenApiReferencePath = run {
        if (!value.contains(localReferencePathStart)) {
            return@run UnknownReferencePath(value)
        }

        // Should only have the component and component name.
        val localReferenceSegments = value.substringAfter(localReferencePathStart, "").split("/")
        if (localReferenceSegments.size != 2) {
            return@run UnknownReferencePath(value)
        }

        val component = localReferenceSegments[0].decodeOrNull<OpenApiReferenceComponent>() ?: return@run UnknownReferencePath(value)
        val componentName = localReferenceSegments[1]
        if (componentName.isBlank()) {
            return@run UnknownReferencePath(value)
        }

        val localReference = LocalReferencePath(component, componentName)
        val documentPath = value.substringBefore(localReferencePathStart, "")
        when {
            documentPath.isBlank() -> localReference
            else -> RemoteReferencePath(documentPath, localReference)
        }
    }
}