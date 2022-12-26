package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.server.OpenApiServerVariable
import com.bselzer.ktx.serialization.context.getContent
import com.bselzer.ktx.serialization.context.getContentListOrEmpty
import kotlinx.serialization.json.JsonObject

object OpenApiServerVariableSerializer : OpenApiObjectSerializer<OpenApiServerVariable>() {
    override fun JsonObject.deserialize(): OpenApiServerVariable = OpenApiServerVariable(
        enum = getContentListOrEmpty("enum"),
        default = getContent("default"),
        description = getDescriptionOrNull("description"),
        extensions = getOpenApiExtensions()
    )
}