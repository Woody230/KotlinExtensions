package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.path.OpenApiPaths
import com.bselzer.ktx.serialization.context.getObjectMapOrEmpty
import kotlinx.serialization.json.JsonObject

internal object OpenApiPathsSerializer : OpenApiObjectSerializer<OpenApiPaths>() {
    override fun JsonObject.deserialize(): OpenApiPaths = OpenApiPaths(
        pathItems = getObjectMapOrEmpty("pathItems", OpenApiPathItemSerializer::deserialize),
        extensions = getOpenApiExtensions()
    )
}