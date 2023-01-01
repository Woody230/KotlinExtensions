package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.reference.OpenApiReference
import com.bselzer.ktx.openapi.model.reference.path.OpenApiReferencePath
import com.bselzer.ktx.serialization.context.getContent
import com.bselzer.ktx.serialization.context.getContentOrNull
import kotlinx.serialization.json.JsonObject

class OpenApiReferenceSerializer<TReferencePath> : OpenApiObjectSerializer<OpenApiReference<TReferencePath>>() where TReferencePath : OpenApiReferencePath {
    override fun JsonObject.deserialize(): OpenApiReference<TReferencePath> = OpenApiReference(
        `$ref` = getContent("\$ref").let(OpenApiReferencePathSerializer<TReferencePath>()::deserialize),
        summary = getContentOrNull("summary"),
        description = getDescriptionOrNull("description")
    )
}