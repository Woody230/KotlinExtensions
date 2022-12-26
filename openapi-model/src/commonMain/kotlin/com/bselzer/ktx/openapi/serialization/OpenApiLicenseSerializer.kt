package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.information.OpenApiLicense
import com.bselzer.ktx.serialization.context.getContent
import com.bselzer.ktx.serialization.context.getContentOrNull
import kotlinx.serialization.json.JsonObject

class OpenApiLicenseSerializer : OpenApiObjectSerializer<OpenApiLicense>() {
    override fun JsonObject.deserialize(): OpenApiLicense = OpenApiLicense(
        name = getContent("name"),
        identifier = getContentOrNull("identifier"),
        url = getUrlOrNull("url"),
        extensions = getOpenApiExtensions()
    )
}