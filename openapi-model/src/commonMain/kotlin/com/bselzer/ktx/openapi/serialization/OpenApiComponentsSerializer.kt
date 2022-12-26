package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.OpenApiComponents
import com.bselzer.ktx.openapi.model.parameter.OpenApiParameterName
import com.bselzer.ktx.openapi.model.security.scheme.OpenApiSecuritySchemeName
import com.bselzer.ktx.serialization.context.getObjectMapOrEmpty
import kotlinx.serialization.json.JsonObject

object OpenApiComponentsSerializer : OpenApiObjectSerializer<OpenApiComponents>() {
    override fun JsonObject.deserialize(): OpenApiComponents = OpenApiComponents(
        schemas = getObjectMapOrEmpty("schemas", OpenApiSchemaSerializer::deserialize),
        responses = getObjectMapOrEmpty("responses", OpenApiReferenceOfSerializer(OpenApiResponseSerializer)::deserialize),
        parameters = getObjectMapOrEmpty("parameters", OpenApiReferenceOfSerializer(OpenApiParameterSerializer)::deserialize).mapKeys { entry ->
            OpenApiParameterName(
                entry.key
            )
        },
        examples = getObjectMapOrEmpty("examples", OpenApiReferenceOfSerializer(OpenApiExampleSerializer)::deserialize),
        requestBodies = getObjectMapOrEmpty("requestBodies", OpenApiReferenceOfSerializer(OpenApiRequestBodySerializer)::deserialize),
        headers = getObjectMapOrEmpty("headers", OpenApiReferenceOfSerializer(OpenApiHeaderSerializer)::deserialize),
        securitySchemes = getObjectMapOrEmpty("securitySchemes", OpenApiReferenceOfSerializer(OpenApiSecuritySchemeSerializer)::deserialize).mapKeys { entry ->
            OpenApiSecuritySchemeName(
                entry.key
            )
        },
        links = getObjectMapOrEmpty("links", OpenApiReferenceOfSerializer(OpenApiLinkSerializer)::deserialize),
        callbacks = getObjectMapOrEmpty("callbacks", OpenApiReferenceOfSerializer(OpenApiCallbackSerializer)::deserialize),
        pathItems = getObjectMapOrEmpty("pathItems", OpenApiReferenceOfSerializer(OpenApiPathItemSerializer)::deserialize),
        extensions = getOpenApiExtensions()
    )
}