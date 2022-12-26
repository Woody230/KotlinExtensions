package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.server.OpenApiServer
import com.bselzer.ktx.serialization.context.getObjectMapOrEmpty
import kotlinx.serialization.json.JsonObject

object OpenApiServerSerializer : OpenApiObjectSerializer<OpenApiServer>() {
    override fun JsonObject.deserialize(): OpenApiServer = OpenApiServer(
        url = getUrl("url"),
        description = getDescriptionOrNull("description"),
        variables = getObjectMapOrEmpty("variables", OpenApiServerVariableSerializer::deserialize),
        extensions = getOpenApiExtensions()
    )
}