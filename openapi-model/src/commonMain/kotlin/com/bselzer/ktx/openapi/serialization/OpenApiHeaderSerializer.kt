package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.parameter.OpenApiParameterStyle
import com.bselzer.ktx.openapi.model.path.OpenApiHeader
import com.bselzer.ktx.openapi.model.path.OpenApiMediaTypeName
import com.bselzer.ktx.serialization.context.*
import kotlinx.serialization.json.JsonObject

object OpenApiHeaderSerializer : OpenApiObjectSerializer<OpenApiHeader>() {
    override fun JsonObject.deserialize(): OpenApiHeader {
        val style = getEnumOrNull("style") ?: OpenApiParameterStyle.SIMPLE

        val defaultExplode = when (style) {
            OpenApiParameterStyle.FORM -> true
            else -> false
        }

        return OpenApiHeader(
            description = getDescriptionOrNull("description"),
            required = getBooleanOrFalse("required"),
            deprecated = getBooleanOrFalse("deprecated"),
            allowEmptyValue = getBooleanOrFalse("allowEmptyValue"),
            style = style,
            explode = getBooleanOrNull("explode") ?: defaultExplode,
            allowReserved = getBooleanOrFalse("allowReserved"),
            schema = getObject("schema", OpenApiReferenceOfSerializer(OpenApiSchemaSerializer)::deserialize),
            example = getElement("example", OpenApiValueSerializer::deserialize),
            examples = getObjectMapOrEmpty("examples", OpenApiReferenceOfSerializer(OpenApiExampleSerializer)::deserialize),
            content = getObjectMapOrEmpty("content", OpenApiMediaTypeSerializer::deserialize).mapKeys { entry -> OpenApiMediaTypeName(entry.key) },
            extensions = getOpenApiExtensions()
        )
    }
}