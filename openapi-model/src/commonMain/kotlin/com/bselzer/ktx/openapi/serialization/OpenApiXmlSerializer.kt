package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.schema.OpenApiXml
import com.bselzer.ktx.serialization.context.getBooleanOrFalse
import com.bselzer.ktx.serialization.context.getContentOrNull
import kotlinx.serialization.json.JsonObject

class OpenApiXmlSerializer : OpenApiObjectSerializer<OpenApiXml>() {
    override fun JsonObject.deserialize(): OpenApiXml = OpenApiXml(
        name = getContentOrNull("name"),
        namespace = getContentOrNull("namespace"),
        prefix = getContentOrNull("prefix"),
        attribute = getBooleanOrFalse("attribute"),
        wrapped = getBooleanOrFalse("wrapped"),
        extensions = getOpenApiExtensions()
    )
}