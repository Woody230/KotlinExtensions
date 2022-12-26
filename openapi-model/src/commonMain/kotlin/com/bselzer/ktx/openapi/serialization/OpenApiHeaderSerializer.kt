package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.OpenApiExampleValue
import com.bselzer.ktx.openapi.model.parameter.OpenApiParameterStyle
import com.bselzer.ktx.openapi.model.path.OpenApiHeader
import com.bselzer.ktx.openapi.model.path.OpenApiMediaTypeName
import com.bselzer.ktx.serialization.context.*
import com.bselzer.ktx.serialization.context.JsonContext.Default.decode
import kotlinx.serialization.json.JsonObject

class OpenApiHeaderSerializer : OpenApiObjectSerializer<OpenApiHeader>() {
    override fun JsonObject.deserialize(): OpenApiHeader {
        val style = getContentOrNull("style")?.decode() ?: OpenApiParameterStyle.SIMPLE

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
            schema = getObject("schema") { it.toOpenApiSchemaReference() },
            example = getObject("example") { OpenApiExampleValue(it.toOpenApiValue()) },
            examples = getObjectMapOrEmpty("examples") { OpenApiReferenceOfSerializer(OpenApiExampleSerializer()).deserialize(it) },
            content = getObjectMapOrEmpty("content") { OpenApiMediaTypeSerializer().deserialize(it) }.mapKeys { entry -> OpenApiMediaTypeName(entry.key) },
            extensions = getOpenApiExtensions()
        )
    }
}