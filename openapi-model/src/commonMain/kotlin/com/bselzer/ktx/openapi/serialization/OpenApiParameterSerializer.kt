package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.parameter.OpenApiParameter
import com.bselzer.ktx.openapi.model.parameter.OpenApiParameterIn
import com.bselzer.ktx.openapi.model.parameter.OpenApiParameterName
import com.bselzer.ktx.openapi.model.parameter.OpenApiParameterStyle
import com.bselzer.ktx.openapi.model.path.OpenApiMediaTypeName
import com.bselzer.ktx.serialization.context.*
import kotlinx.serialization.json.JsonObject

internal object OpenApiParameterSerializer : OpenApiObjectSerializer<OpenApiParameter>() {
    override fun JsonObject.deserialize(): OpenApiParameter {
        val `in`: OpenApiParameterIn = getEnum("in")

        val defaultRequired = `in` == OpenApiParameterIn.PATH

        val defaultStyle = when (`in`) {
            OpenApiParameterIn.QUERY -> OpenApiParameterStyle.FORM
            OpenApiParameterIn.HEADER -> OpenApiParameterStyle.SIMPLE
            OpenApiParameterIn.PATH -> OpenApiParameterStyle.SIMPLE
            OpenApiParameterIn.COOKIE -> OpenApiParameterStyle.FORM
        }

        val style = getEnumOrNull("style") ?: defaultStyle

        val defaultExplode = when (style) {
            OpenApiParameterStyle.FORM -> true
            else -> false
        }

        return OpenApiParameter(
            name = getContent("name").let(::OpenApiParameterName),
            `in` = `in`,
            description = getDescriptionOrNull("description"),
            required = getBooleanOrNull("required") ?: defaultRequired,
            deprecated = getBooleanOrFalse("deprecated"),
            allowEmptyValue = getBooleanOrFalse("allowEmptyValue"),
            style = style,
            explode = getBooleanOrNull("explode") ?: defaultExplode,
            allowReserved = getBooleanOrFalse("allowReserved"),
            schema = getObject("schema", ReferenceOfOpenApiSchemaSerializer::deserialize),
            example = getObject("example", OpenApiValueSerializer::deserialize),
            examples = getObjectMapOrEmpty("examples", ReferenceOfOpenApiExampleSerializer::deserialize),
            content = getObjectMapOrEmpty("content", OpenApiMediaTypeSerializer::deserialize).mapKeys { entry -> OpenApiMediaTypeName(entry.key) },
            extensions = getOpenApiExtensions()
        )
    }
}