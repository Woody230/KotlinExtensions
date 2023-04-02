package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.information.OpenApiInformation
import com.bselzer.ktx.serialization.context.getContent
import com.bselzer.ktx.serialization.context.getContentOrNull
import com.bselzer.ktx.serialization.context.getObjectOrNull
import kotlinx.serialization.json.JsonObject

object OpenApiInformationSerializer : OpenApiObjectSerializer<OpenApiInformation>() {
    override fun JsonObject.deserialize(): OpenApiInformation = OpenApiInformation(
        title = getContent("title"),
        summary = getContentOrNull("summary"),
        description = getDescriptionOrNull("description"),
        termsOfService = getContentOrNull("termsOfService"),
        contact = getObjectOrNull("contact", OpenApiContactSerializer::deserialize),
        license = getObjectOrNull("license", OpenApiLicenseSerializer::deserialize),
        version = getContent("version"),
        extensions = getOpenApiExtensions()
    )
}