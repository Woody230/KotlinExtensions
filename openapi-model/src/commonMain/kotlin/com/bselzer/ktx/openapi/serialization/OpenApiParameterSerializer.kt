package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.OpenApiExampleValue
import com.bselzer.ktx.openapi.model.parameter.OpenApiParameter
import com.bselzer.ktx.openapi.model.parameter.OpenApiParameterIn
import com.bselzer.ktx.openapi.model.parameter.OpenApiParameterName
import com.bselzer.ktx.openapi.model.parameter.OpenApiParameterStyle
import com.bselzer.ktx.openapi.model.path.OpenApiMediaTypeName
import com.bselzer.ktx.serialization.context.*
import com.bselzer.ktx.serialization.context.JsonContext.Default.decode
import kotlinx.serialization.json.JsonObject

class OpenApiParameterSerializer : OpenApiObjectSerializer<OpenApiParameter>() {
    override fun JsonObject.deserialize(): OpenApiParameter {
        val `in`: OpenApiParameterIn = with(JsonContext) { getContent("in").decode() }

        val defaultRequired = `in` == OpenApiParameterIn.PATH

        val defaultStyle = when (`in`) {
            OpenApiParameterIn.QUERY -> OpenApiParameterStyle.FORM
            OpenApiParameterIn.HEADER -> OpenApiParameterStyle.SIMPLE
            OpenApiParameterIn.PATH -> OpenApiParameterStyle.SIMPLE
            OpenApiParameterIn.COOKIE -> OpenApiParameterStyle.FORM
        }

        val style = getContentOrNull("style")?.decode() ?: defaultStyle

        val defaultExplode = when (style) {
            OpenApiParameterStyle.FORM -> true
            else -> false
        }

        return OpenApiParameter(
            name = OpenApiParameterName(getContent("name")),
            `in` = `in`,
            description = getDescriptionOrNull("description"),
            required = getBooleanOrNull("required") ?: defaultRequired,
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