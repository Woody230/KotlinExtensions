package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.reference.OpenApiReference
import com.bselzer.ktx.openapi.model.reference.OpenApiReferencePath
import com.bselzer.ktx.serialization.context.getContent
import com.bselzer.ktx.serialization.context.getContentOrNull
import kotlinx.serialization.json.JsonObject

object OpenApiReferenceSerializer : OpenApiObjectSerializer<OpenApiReference>() {
    override fun JsonObject.deserialize(): OpenApiReference = OpenApiReference(
        `$ref` = getContent("\$ref").let(::OpenApiReferencePath),
        summary = getContentOrNull("summary"),
        description = getDescriptionOrNull("description")
    )
}