package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.reference.OpenApiReference
import com.bselzer.ktx.openapi.model.reference.toOpenApiReferencePath
import com.bselzer.ktx.serialization.context.getContent
import com.bselzer.ktx.serialization.context.getContentOrNull
import kotlinx.serialization.json.JsonObject

object OpenApiReferenceSerializer : OpenApiObjectSerializer<OpenApiReference>() {
    override fun JsonObject.deserialize(): OpenApiReference = OpenApiReference(
        `$ref` = getContent("\$ref").let(String::toOpenApiReferencePath),
        summary = getContentOrNull("summary"),
        description = getDescriptionOrNull("description")
    )
}