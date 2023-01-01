package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.response.OpenApiHttpStatusCode
import com.bselzer.ktx.openapi.model.response.OpenApiResponses
import com.bselzer.ktx.serialization.context.getObjectMapOrEmpty
import com.bselzer.ktx.serialization.context.getObjectOrNull
import kotlinx.serialization.json.JsonObject

internal object OpenApiResponsesSerializer : OpenApiObjectSerializer<OpenApiResponses>() {
    override fun JsonObject.deserialize(): OpenApiResponses = OpenApiResponses(
        default = getObjectOrNull("default", ReferenceOfOpenApiResponseSerializer::deserialize),
        responses = getObjectMapOrEmpty("responses", ReferenceOfOpenApiResponseSerializer::deserialize).mapKeys { entry ->
            OpenApiHttpStatusCode(
                entry.key
            )
        },
        extensions = getOpenApiExtensions()
    )
}