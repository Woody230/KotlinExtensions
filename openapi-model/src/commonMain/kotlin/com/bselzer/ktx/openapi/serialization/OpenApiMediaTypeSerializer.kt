package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.path.OpenApiEncodingName
import com.bselzer.ktx.openapi.model.path.OpenApiMediaType
import com.bselzer.ktx.serialization.context.getObject
import com.bselzer.ktx.serialization.context.getObjectMapOrEmpty
import com.bselzer.ktx.serialization.context.getObjectOrNull
import kotlinx.serialization.json.JsonObject

internal object OpenApiMediaTypeSerializer : OpenApiObjectSerializer<OpenApiMediaType>() {
    override fun JsonObject.deserialize(): OpenApiMediaType = OpenApiMediaType(
        schema = getObjectOrNull("schema", OpenApiSchemaSerializer::deserialize),
        example = getObject("example", OpenApiValueSerializer::deserialize),
        examples = getObjectMapOrEmpty("examples", ReferenceOfOpenApiExampleSerializer::deserialize),
        encoding = getObjectMapOrEmpty("encoding", OpenApiEncodingSerializer::deserialize).mapKeys { entry -> OpenApiEncodingName(entry.key) },
        extensions = getOpenApiExtensions()
    )
}