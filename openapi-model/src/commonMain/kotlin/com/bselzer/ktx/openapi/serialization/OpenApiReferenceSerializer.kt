package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.OpenApiReference
import com.bselzer.ktx.openapi.model.OpenApiReferenceIdentifier
import com.bselzer.ktx.serialization.context.getContent
import com.bselzer.ktx.serialization.context.getContentOrNull
import kotlinx.serialization.json.JsonObject

object OpenApiReferenceSerializer : OpenApiObjectSerializer<OpenApiReference>() {
    override fun JsonObject.deserialize(): OpenApiReference = OpenApiReference(
        `$ref` = OpenApiReferenceIdentifier(getContent("\$ref")),
        summary = getContentOrNull("summary"),
        description = getDescriptionOrNull("description")
    )
}