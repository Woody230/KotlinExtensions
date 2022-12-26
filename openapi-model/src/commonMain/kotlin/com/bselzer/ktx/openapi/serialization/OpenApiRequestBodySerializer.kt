package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.path.OpenApiMediaTypeName
import com.bselzer.ktx.openapi.model.path.OpenApiRequestBody
import com.bselzer.ktx.serialization.context.getBooleanOrFalse
import com.bselzer.ktx.serialization.context.getObjectMapOrEmpty
import kotlinx.serialization.json.JsonObject

class OpenApiRequestBodySerializer : OpenApiObjectSerializer<OpenApiRequestBody>() {
    override fun JsonObject.deserialize(): OpenApiRequestBody = OpenApiRequestBody(
        description = getDescriptionOrNull("description"),
        content = getObjectMapOrEmpty("content") { OpenApiMediaTypeSerializer().deserialize(it) }.mapKeys { entry -> OpenApiMediaTypeName(entry.key) },
        required = getBooleanOrFalse("required"),
        extensions = getOpenApiExtensions()
    )
}