package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.OpenApiTagName
import com.bselzer.ktx.openapi.model.path.OpenApiOperation
import com.bselzer.ktx.openapi.model.path.OpenApiOperationId
import com.bselzer.ktx.serialization.context.*
import kotlinx.serialization.json.JsonObject

object OpenApiOperationSerializer : OpenApiObjectSerializer<OpenApiOperation>() {
    override fun JsonObject.deserialize(): OpenApiOperation = OpenApiOperation(
        tags = getContentListOrEmpty("tags").map(::OpenApiTagName),
        summary = getContentOrNull("summary"),
        description = getDescriptionOrNull("description"),
        externalDocs = getExternalDocumentationOrNull("externalDocs"),
        operationId = getContentOrNull("operationId")?.let(::OpenApiOperationId),
        parameters = getObjectListOrEmpty("parameters", ReferenceOfOpenApiParameterSerializer::deserialize),
        requestBody = getObjectOrNull("requestBody", ReferenceOfOpenApiRequestBodySerializer::deserialize),
        responses = getObject("responses", OpenApiResponsesSerializer::deserialize),
        callbacks = getObjectMapOrEmpty("callbacks", ReferenceOfOpenApiCallbackSerializer::deserialize),
        deprecated = getBooleanOrFalse("deprecated"),
        security = getObjectListOrEmpty("security", OpenApiSecurityRequirementSerializer::deserialize),
        servers = getObjectListOrEmpty("servers", OpenApiServerSerializer::deserialize),
        extensions = getOpenApiExtensions()
    )
}