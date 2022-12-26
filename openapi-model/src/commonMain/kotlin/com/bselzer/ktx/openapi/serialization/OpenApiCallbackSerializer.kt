package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.path.OpenApiCallback
import com.bselzer.ktx.serialization.context.getObjectMapOrEmpty
import kotlinx.serialization.json.JsonObject

object OpenApiCallbackSerializer : OpenApiObjectSerializer<OpenApiCallback>() {
    override fun JsonObject.deserialize(): OpenApiCallback = OpenApiCallback(
        pathItems = getObjectMapOrEmpty("pathItems", OpenApiReferenceOfSerializer(OpenApiPathItemSerializer)::deserialize),
        extensions = getOpenApiExtensions()
    )
}