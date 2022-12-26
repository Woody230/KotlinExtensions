package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.OpenApiReferenceIdentifier
import com.bselzer.ktx.openapi.model.schema.OpenApiDiscriminator
import com.bselzer.ktx.serialization.context.getContent
import com.bselzer.ktx.serialization.context.getContentMapOrEmpty
import kotlinx.serialization.json.JsonObject

class OpenApiDiscriminatorSerializer : OpenApiObjectSerializer<OpenApiDiscriminator>() {
    override fun JsonObject.deserialize(): OpenApiDiscriminator = OpenApiDiscriminator(
        propertyName = getContent("propertyName"),
        mapping = getContentMapOrEmpty("mapping").mapValues { entry -> OpenApiReferenceIdentifier(entry.value) },
        extensions = getOpenApiExtensions()
    )
}