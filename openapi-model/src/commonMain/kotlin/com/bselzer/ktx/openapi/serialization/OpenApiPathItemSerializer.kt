package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.OpenApiReferenceIdentifier
import com.bselzer.ktx.openapi.model.path.OpenApiOperation
import com.bselzer.ktx.openapi.model.path.OpenApiPathItem
import com.bselzer.ktx.serialization.context.getContentOrNull
import com.bselzer.ktx.serialization.context.getObjectListOrEmpty
import com.bselzer.ktx.serialization.context.getObjectOrNull
import kotlinx.serialization.json.JsonObject

class OpenApiPathItemSerializer : OpenApiObjectSerializer<OpenApiPathItem>() {
    override fun JsonObject.deserialize(): OpenApiPathItem = OpenApiPathItem(
        `$ref` = getContentOrNull("\$ref")?.let { OpenApiReferenceIdentifier(it) },
        summary = getContentOrNull("summary"),
        description = getDescriptionOrNull("description"),
        get = getOperationOrNull("get"),
        put = getOperationOrNull("put"),
        post = getOperationOrNull("post"),
        delete = getOperationOrNull("delete"),
        options = getOperationOrNull("options"),
        head = getOperationOrNull("head"),
        patch = getOperationOrNull("patch"),
        trace = getOperationOrNull("trace"),
        servers = getObjectListOrEmpty("servers") { OpenApiServerSerializer().deserialize(it) },
        parameters = getObjectListOrEmpty("parameters") { OpenApiReferenceOfSerializer(OpenApiParameterSerializer()).deserialize(it) },
        extensions = getOpenApiExtensions()
    )

    private fun JsonObject.getOperationOrNull(key: String): OpenApiOperation? = getObjectOrNull(key) {
        OpenApiOperationSerializer().deserialize(it)
    }
}