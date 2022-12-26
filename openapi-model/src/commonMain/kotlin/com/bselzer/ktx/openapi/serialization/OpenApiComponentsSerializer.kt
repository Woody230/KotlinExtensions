package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.OpenApiComponents
import com.bselzer.ktx.openapi.model.parameter.OpenApiParameterName
import com.bselzer.ktx.openapi.model.security.scheme.OpenApiSecuritySchemeName
import com.bselzer.ktx.serialization.context.getObjectMapOrEmpty
import kotlinx.serialization.json.JsonObject

object OpenApiComponentsSerializer : OpenApiObjectSerializer<OpenApiComponents>() {
    override fun JsonObject.deserialize(): OpenApiComponents = OpenApiComponents(
        schemas = getObjectMapOrEmpty("schemas") { it.toOpenApiSchema() },
        responses = getObjectMapOrEmpty("responses") { OpenApiReferenceOfSerializer(OpenApiResponseSerializer).deserialize(it) },
        parameters = getObjectMapOrEmpty("parameters") { OpenApiReferenceOfSerializer(OpenApiParameterSerializer).deserialize(it) }.mapKeys { entry ->
            OpenApiParameterName(
                entry.key
            )
        },
        examples = getObjectMapOrEmpty("examples") { OpenApiReferenceOfSerializer(OpenApiExampleSerializer).deserialize(it) },
        requestBodies = getObjectMapOrEmpty("requestBodies") { OpenApiReferenceOfSerializer(OpenApiRequestBodySerializer).deserialize(it) },
        headers = getObjectMapOrEmpty("headers") { OpenApiReferenceOfSerializer(OpenApiHeaderSerializer).deserialize(it) },
        securitySchemes = getObjectMapOrEmpty("securitySchemes") { OpenApiReferenceOfSerializer(OpenApiSecuritySchemeSerializer).deserialize(it) }.mapKeys { entry ->
            OpenApiSecuritySchemeName(
                entry.key
            )
        },
        links = getObjectMapOrEmpty("links") { OpenApiReferenceOfSerializer(OpenApiLinkSerializer).deserialize(it) },
        callbacks = getObjectMapOrEmpty("callbacks") { OpenApiReferenceOfSerializer(OpenApiCallbackSerializer).deserialize(it) },
        pathItems = getObjectMapOrEmpty("pathItems") { OpenApiReferenceOfSerializer(OpenApiPathItemSerializer).deserialize(it) },
        extensions = getOpenApiExtensions()
    )
}