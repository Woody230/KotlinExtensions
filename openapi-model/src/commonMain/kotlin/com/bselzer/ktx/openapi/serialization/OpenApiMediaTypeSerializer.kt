package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.path.OpenApiEncodingName
import com.bselzer.ktx.openapi.model.path.OpenApiMediaType
import com.bselzer.ktx.serialization.context.getObject
import com.bselzer.ktx.serialization.context.getObjectMapOrEmpty
import com.bselzer.ktx.serialization.context.getObjectOrNull
import kotlinx.serialization.json.JsonObject

object OpenApiMediaTypeSerializer : OpenApiObjectSerializer<OpenApiMediaType>() {
    override fun JsonObject.deserialize(): OpenApiMediaType = OpenApiMediaType(
        schema = getObjectOrNull("schema") { it.toOpenApiSchema() },
        example = getObject("example", OpenApiValueSerializer::deserialize),
        examples = getObjectMapOrEmpty("examples", OpenApiReferenceOfSerializer(OpenApiExampleSerializer)::deserialize),
        encoding = getObjectMapOrEmpty("encoding", OpenApiEncodingSerializer::deserialize).mapKeys { entry -> OpenApiEncodingName(entry.key) },
        extensions = getOpenApiExtensions()
    )
}