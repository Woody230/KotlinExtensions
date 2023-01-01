package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.information.OpenApiContact
import com.bselzer.ktx.serialization.context.getContentOrNull
import kotlinx.serialization.json.JsonObject

internal object OpenApiContactSerializer : OpenApiObjectSerializer<OpenApiContact>() {
    override fun JsonObject.deserialize(): OpenApiContact = OpenApiContact(
        name = getContentOrNull("name"),
        url = getUrlOrNull("url"),
        email = getEmailOrNull("email"),
        extensions = getOpenApiExtensions()
    )
}