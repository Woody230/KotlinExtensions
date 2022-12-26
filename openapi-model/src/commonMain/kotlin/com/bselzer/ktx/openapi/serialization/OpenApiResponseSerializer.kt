package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.OpenApiDescription
import com.bselzer.ktx.openapi.model.path.OpenApiMediaTypeName
import com.bselzer.ktx.openapi.model.response.OpenApiResponse
import com.bselzer.ktx.serialization.context.getContent
import com.bselzer.ktx.serialization.context.getObjectMapOrEmpty
import kotlinx.serialization.json.JsonObject

class OpenApiResponseSerializer : OpenApiObjectSerializer<OpenApiResponse>() {
    override fun JsonObject.deserialize(): OpenApiResponse = OpenApiResponse(
        description = OpenApiDescription(getContent("description")),
        headers = getObjectMapOrEmpty("headers") { OpenApiReferenceOfSerializer(OpenApiHeaderSerializer()).deserialize(it) },
        content = getObjectMapOrEmpty("content") { OpenApiMediaTypeSerializer().deserialize(it) }.mapKeys { entry -> OpenApiMediaTypeName(entry.key) },
        links = getObjectMapOrEmpty("links") { OpenApiReferenceOfSerializer(OpenApiLinkSerializer()).deserialize(it) },
        extensions = getOpenApiExtensions()
    )
}