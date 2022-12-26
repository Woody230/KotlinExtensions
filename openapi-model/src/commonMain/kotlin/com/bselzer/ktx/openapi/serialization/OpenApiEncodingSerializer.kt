package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.path.OpenApiEncoding
import com.bselzer.ktx.serialization.context.JsonContext.Default.decode
import com.bselzer.ktx.serialization.context.getBooleanOrNull
import com.bselzer.ktx.serialization.context.getContentOrNull
import com.bselzer.ktx.serialization.context.getObjectMapOrEmpty
import kotlinx.serialization.json.JsonObject

class OpenApiEncodingSerializer : OpenApiObjectSerializer<OpenApiEncoding>() {
    override fun JsonObject.deserialize(): OpenApiEncoding = OpenApiEncoding(
        contentType = getContentOrNull("contentType"),
        headers = getObjectMapOrEmpty("headers") { OpenApiReferenceOfSerializer(OpenApiHeaderSerializer()).deserialize(it) },
        style = getContentOrNull("style")?.decode(),
        explode = getBooleanOrNull("explode")
    )
}