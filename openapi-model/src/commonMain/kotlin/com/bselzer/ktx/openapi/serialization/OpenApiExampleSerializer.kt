package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.OpenApiExample
import com.bselzer.ktx.serialization.context.getContentOrNull
import com.bselzer.ktx.serialization.context.getObjectOrNull
import kotlinx.serialization.json.JsonObject

object OpenApiExampleSerializer : OpenApiObjectSerializer<OpenApiExample>() {
    override fun JsonObject.deserialize(): OpenApiExample = OpenApiExample(
        summary = getContentOrNull("summary"),
        description = getDescriptionOrNull("description"),
        value = getObjectOrNull("value", OpenApiValueSerializer::deserialize),
        externalValue = getContentOrNull("externalValue"),
        extensions = getOpenApiExtensions()
    )
}