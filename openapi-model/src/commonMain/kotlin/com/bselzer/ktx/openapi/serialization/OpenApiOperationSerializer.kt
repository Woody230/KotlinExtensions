package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.OpenApiTagName
import com.bselzer.ktx.openapi.model.path.OpenApiOperation
import com.bselzer.ktx.openapi.model.path.OpenApiOperationId
import com.bselzer.ktx.serialization.context.*
import kotlinx.serialization.json.JsonObject

object OpenApiOperationSerializer : OpenApiObjectSerializer<OpenApiOperation>() {
    override fun JsonObject.deserialize(): OpenApiOperation = OpenApiOperation(
        tags = getObjectListOrEmpty("tags") { element -> OpenApiTagName(element.toContent()) },
        summary = getContentOrNull("summary"),
        description = getDescriptionOrNull("description"),
        externalDocs = getExternalDocumentationOrNull("externalDocs"),
        operationId = getContentOrNull("operationId")?.let { OpenApiOperationId(it) },
        parameters = getObjectListOrEmpty("parameters") { OpenApiReferenceOfSerializer(OpenApiParameterSerializer).deserialize(it) },
        requestBody = getObjectOrNull("requestBody") { OpenApiReferenceOfSerializer(OpenApiRequestBodySerializer).deserialize(it) },
        responses = getObject("responses") { OpenApiResponsesSerializer.deserialize(it) },
        callbacks = getObjectMapOrEmpty("callbacks") { OpenApiReferenceOfSerializer(OpenApiCallbackSerializer).deserialize(it) },
        deprecated = getBooleanOrFalse("deprecated"),
        security = getSecurityRequirements("security"),
        servers = getObjectListOrEmpty("servers") { OpenApiServerSerializer.deserialize(it) },
        extensions = getOpenApiExtensions()
    )
}