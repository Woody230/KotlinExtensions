package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.OpenApi
import com.bselzer.ktx.serialization.context.*
import kotlinx.serialization.json.JsonObject

object OpenApiSerializer : OpenApiObjectSerializer<OpenApi>() {
    override fun JsonObject.deserialize(): OpenApi = OpenApi(
        openapi = getContentOrNull("openapi") ?: "3.1.0",
        info = getObject("info", OpenApiInformationSerializer::deserialize),
        jsonSchemaDialect = getContentOrNull("jsonSchemaDialect") ?: "https://spec.openapis.org/oas/3.1/dialect/base",
        servers = getObjectListOrEmpty("servers", OpenApiServerSerializer::deserialize),
        paths = getObject("paths", OpenApiPathsSerializer::deserialize),
        webhooks = getObjectMapOrEmpty("webhooks", ReferenceOfOpenApiPathItemSerializer::deserialize),
        components = getObjectOrNull("components", OpenApiComponentsSerializer::deserialize),
        security = getObjectListOrEmpty("security", OpenApiSecurityRequirementSerializer::deserialize),
        tags = getObjectListOrEmpty("tags", OpenApiTagSerializer::deserialize),
        externalDocs = getExternalDocumentationOrNull("externalDocs"),
        extensions = getOpenApiExtensions(),
    )
}