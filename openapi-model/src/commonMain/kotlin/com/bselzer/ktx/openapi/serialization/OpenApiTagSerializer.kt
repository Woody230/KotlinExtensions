package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.OpenApiTag
import com.bselzer.ktx.serialization.context.getContent
import kotlinx.serialization.json.JsonObject

object OpenApiTagSerializer : OpenApiObjectSerializer<OpenApiTag>() {
    override fun JsonObject.deserialize(): OpenApiTag = OpenApiTag(
        name = getContent("name"),
        description = getDescriptionOrNull("description"),
        externalDocs = getExternalDocumentationOrNull("externalDocs"),
        extensions = getOpenApiExtensions()
    )
}