package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.path.OpenApiEncoding
import com.bselzer.ktx.serialization.context.getBooleanOrNull
import com.bselzer.ktx.serialization.context.getContentOrNull
import com.bselzer.ktx.serialization.context.getEnumOrNull
import com.bselzer.ktx.serialization.context.getObjectMapOrEmpty
import kotlinx.serialization.json.JsonObject

internal object OpenApiEncodingSerializer : OpenApiObjectSerializer<OpenApiEncoding>() {
    override fun JsonObject.deserialize(): OpenApiEncoding = OpenApiEncoding(
        contentType = getContentOrNull("contentType"),
        headers = getObjectMapOrEmpty("headers", ReferenceOfOpenApiHeaderSerializer::deserialize),
        style = getEnumOrNull("style"),
        explode = getBooleanOrNull("explode")
    )
}