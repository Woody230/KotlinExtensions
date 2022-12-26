package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.response.OpenApiHttpStatusCode
import com.bselzer.ktx.openapi.model.response.OpenApiResponses
import com.bselzer.ktx.serialization.context.getObjectMapOrEmpty
import com.bselzer.ktx.serialization.context.getObjectOrNull
import kotlinx.serialization.json.JsonObject

object OpenApiResponsesSerializer : OpenApiObjectSerializer<OpenApiResponses>() {
    override fun JsonObject.deserialize(): OpenApiResponses = OpenApiResponses(
        default = getObjectOrNull("default") { OpenApiReferenceOfSerializer(OpenApiResponseSerializer).deserialize(it) },
        responses = getObjectMapOrEmpty("responses") { OpenApiReferenceOfSerializer(OpenApiResponseSerializer).deserialize(it) }.mapKeys { entry ->
            OpenApiHttpStatusCode(
                entry.key
            )
        },
        extensions = getOpenApiExtensions()
    )
}