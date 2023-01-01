package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.OpenApiValueOrRuntimeExpression
import com.bselzer.ktx.openapi.model.expression.toOpenApiRuntimeExpression
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonObject

internal object OpenApiValueOrRuntimeExpressionSerializer : OpenApiElementSerializer<OpenApiValueOrRuntimeExpression>() {
    override fun JsonElement.deserialize(): OpenApiValueOrRuntimeExpression {
        if (this is JsonPrimitive && isString) {
            val expression = content.toOpenApiRuntimeExpression()
            return OpenApiValueOrRuntimeExpression(expression)
        }

        val value = OpenApiValueSerializer.deserialize(jsonObject)
        return OpenApiValueOrRuntimeExpression(value)
    }
}