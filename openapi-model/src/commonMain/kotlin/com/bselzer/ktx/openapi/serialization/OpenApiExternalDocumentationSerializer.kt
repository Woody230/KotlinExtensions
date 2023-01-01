package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.OpenApiExternalDocumentation
import kotlinx.serialization.json.JsonObject

internal object OpenApiExternalDocumentationSerializer : OpenApiObjectSerializer<OpenApiExternalDocumentation>() {
    override fun JsonObject.deserialize(): OpenApiExternalDocumentation = OpenApiExternalDocumentation(
        description = getDescriptionOrNull("description"),
        url = getUrl("url"),
        extensions = getOpenApiExtensions()
    )
}