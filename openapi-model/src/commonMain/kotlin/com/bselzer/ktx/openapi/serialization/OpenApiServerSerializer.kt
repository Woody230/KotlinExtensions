package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.server.OpenApiServer
import com.bselzer.ktx.serialization.context.getObjectMapOrEmpty
import kotlinx.serialization.json.JsonObject

class OpenApiServerSerializer : OpenApiObjectSerializer<OpenApiServer>() {
    override fun JsonObject.deserialize(): OpenApiServer = OpenApiServer(
        url = getUrl("url"),
        description = getDescriptionOrNull("description"),
        variables = getObjectMapOrEmpty("variables") { OpenApiServerVariableSerializer().deserialize(it) },
        extensions = getOpenApiExtensions()
    )
}