package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.OpenApiExampleValue
import com.bselzer.ktx.openapi.model.path.OpenApiEncodingName
import com.bselzer.ktx.openapi.model.path.OpenApiMediaType
import com.bselzer.ktx.serialization.context.getObject
import com.bselzer.ktx.serialization.context.getObjectMapOrEmpty
import com.bselzer.ktx.serialization.context.getObjectOrNull
import kotlinx.serialization.json.JsonObject

class OpenApiMediaTypeSerializer : OpenApiObjectSerializer<OpenApiMediaType>() {
    override fun JsonObject.deserialize(): OpenApiMediaType = OpenApiMediaType(
        schema = getObjectOrNull("schema") { it.toOpenApiSchema() },
        example = getObject("example") { OpenApiExampleValue(it.toOpenApiValue()) },
        examples = getObjectMapOrEmpty("examples") { OpenApiReferenceOfSerializer(OpenApiExampleSerializer()).deserialize(it) },
        encoding = getObjectMapOrEmpty("encoding") { OpenApiEncodingSerializer().deserialize(it) }.mapKeys { entry -> OpenApiEncodingName(entry.key) },
        extensions = getOpenApiExtensions()
    )
}