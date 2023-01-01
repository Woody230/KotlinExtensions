package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.reference.toOpenApiReferencePath
import com.bselzer.ktx.openapi.model.schema.OpenApiDiscriminator
import com.bselzer.ktx.serialization.context.getContent
import com.bselzer.ktx.serialization.context.getContentMapOrEmpty
import kotlinx.serialization.json.JsonObject

object OpenApiDiscriminatorSerializer : OpenApiObjectSerializer<OpenApiDiscriminator>() {
    override fun JsonObject.deserialize(): OpenApiDiscriminator = OpenApiDiscriminator(
        propertyName = getContent("propertyName"),
        mapping = getContentMapOrEmpty("mapping").mapValues { entry -> entry.value.toOpenApiReferencePath() },
        extensions = getOpenApiExtensions()
    )
}