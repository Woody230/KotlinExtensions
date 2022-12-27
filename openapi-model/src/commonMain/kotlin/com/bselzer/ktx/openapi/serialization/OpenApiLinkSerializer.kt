package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.parameter.OpenApiParameterName
import com.bselzer.ktx.openapi.model.path.OpenApiOperationId
import com.bselzer.ktx.openapi.model.reference.OpenApiReferencePath
import com.bselzer.ktx.openapi.model.response.OpenApiLink
import com.bselzer.ktx.serialization.context.getContent
import com.bselzer.ktx.serialization.context.getContentOrNull
import com.bselzer.ktx.serialization.context.getMapOrEmpty
import com.bselzer.ktx.serialization.context.getObject
import kotlinx.serialization.json.JsonObject

object OpenApiLinkSerializer : OpenApiObjectSerializer<OpenApiLink>() {
    override fun JsonObject.deserialize(): OpenApiLink = OpenApiLink(
        operationRef = getContent("operationRef").let(::OpenApiReferencePath),
        operationId = getContentOrNull("operationId")?.let(::OpenApiOperationId),
        parameters = getMapOrEmpty("parameters", OpenApiValueOrRuntimeExpressionSerializer::deserialize).mapKeys { entry -> OpenApiParameterName(entry.key) },
        requestBody = get("requestBody")?.let(OpenApiValueOrRuntimeExpressionSerializer::deserialize),
        description = getDescriptionOrNull("description"),
        server = getObject("server", OpenApiServerSerializer::deserialize),
        extensions = getOpenApiExtensions()
    )
}