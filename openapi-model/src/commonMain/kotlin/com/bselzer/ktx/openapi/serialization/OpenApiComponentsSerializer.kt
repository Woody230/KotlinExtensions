package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.OpenApiComponents
import com.bselzer.ktx.openapi.model.parameter.OpenApiParameterName
import com.bselzer.ktx.openapi.model.security.scheme.OpenApiSecuritySchemeName
import com.bselzer.ktx.serialization.context.getObjectMapOrEmpty
import kotlinx.serialization.json.JsonObject

object OpenApiComponentsSerializer : OpenApiObjectSerializer<OpenApiComponents>() {
    override fun JsonObject.deserialize(): OpenApiComponents = OpenApiComponents(
        schemas = getObjectMapOrEmpty("schemas", OpenApiSchemaSerializer::deserialize),
        responses = getObjectMapOrEmpty("responses", ReferenceOfOpenApiResponseSerializer::deserialize),
        parameters = getObjectMapOrEmpty("parameters", ReferenceOfOpenApiParameterSerializer::deserialize).mapKeys { entry ->
            OpenApiParameterName(
                entry.key
            )
        },
        examples = getObjectMapOrEmpty("examples", ReferenceOfOpenApiExampleSerializer::deserialize),
        requestBodies = getObjectMapOrEmpty("requestBodies", ReferenceOfOpenApiRequestBodySerializer::deserialize),
        headers = getObjectMapOrEmpty("headers", ReferenceOfOpenApiHeaderSerializer::deserialize),
        securitySchemes = getObjectMapOrEmpty("securitySchemes", ReferenceOfOpenApiSecuritySchemeSerializer::deserialize).mapKeys { entry ->
            OpenApiSecuritySchemeName(
                entry.key
            )
        },
        links = getObjectMapOrEmpty("links", ReferenceOfOpenApiLinkSerializer::deserialize),
        callbacks = getObjectMapOrEmpty("callbacks", ReferenceOfOpenApiCallbackSerializer::deserialize),
        pathItems = getObjectMapOrEmpty("pathItems", ReferenceOfOpenApiPathItemSerializer::deserialize),
        extensions = getOpenApiExtensions()
    )
}