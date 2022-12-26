package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.OpenApi
import com.bselzer.ktx.serialization.context.*
import kotlinx.serialization.json.JsonObject

object OpenApiSerializer : OpenApiObjectSerializer<OpenApi>() {
    override fun JsonObject.deserialize(): OpenApi = OpenApi(
        openapi = getContentOrNull("openapi") ?: "3.1.0",
        info = getObject("info") { OpenApiInformationSerializer.deserialize(it) },
        jsonSchemaDialect = getContentOrNull("jsonSchemaDialect") ?: "https://spec.openapis.org/oas/3.1/dialect/base",
        servers = getObjectListOrEmpty("servers") { OpenApiServerSerializer.deserialize(it) },
        paths = getObject("paths") { OpenApiPathsSerializer.deserialize(it) },
        webhooks = getObjectMapOrEmpty("webhooks") { OpenApiReferenceOfSerializer(OpenApiPathItemSerializer).deserialize(it) },
        components = getObjectOrNull("components") { OpenApiComponentsSerializer.deserialize(it) },
        security = getSecurityRequirements("security"),
        tags = getObjectListOrEmpty("tags") { OpenApiTagSerializer.deserialize(it) },
        externalDocs = getExternalDocumentationOrNull("externalDocs"),
        extensions = getOpenApiExtensions(),
    )
}