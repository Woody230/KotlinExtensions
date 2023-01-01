package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.path.OpenApiMediaTypeName
import com.bselzer.ktx.openapi.model.response.OpenApiResponse
import com.bselzer.ktx.serialization.context.getObjectMapOrEmpty
import kotlinx.serialization.json.JsonObject

internal object OpenApiResponseSerializer : OpenApiObjectSerializer<OpenApiResponse>() {
    override fun JsonObject.deserialize(): OpenApiResponse = OpenApiResponse(
        description = getDescription("description"),
        headers = getObjectMapOrEmpty("headers", ReferenceOfOpenApiHeaderSerializer::deserialize),
        content = getObjectMapOrEmpty("content", OpenApiMediaTypeSerializer::deserialize).mapKeys { entry -> OpenApiMediaTypeName(entry.key) },
        links = getObjectMapOrEmpty("links", ReferenceOfOpenApiLinkSerializer::deserialize),
        extensions = getOpenApiExtensions()
    )
}