package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.value.*
import kotlinx.serialization.json.*

class OpenApiValueSerializer : OpenApiElementSerializer<OpenApiValue>() {
    override fun JsonElement.deserialize(): OpenApiValue = when (this) {
        is JsonNull -> OpenApiNull
        is JsonPrimitive -> toOpenApiValue()
        is JsonObject -> toOpenApiValue()
        is JsonArray -> toOpenApiValue()
    }

    private fun JsonArray.toOpenApiValue(): OpenApiValue {
        val values = map { value -> value.deserialize() }
        return OpenApiList(values)
    }

    private fun JsonObject.toOpenApiValue(): OpenApiValue {
        val entries = mapValues { entry -> entry.value.deserialize() }
        return OpenApiMap(entries)
    }

    private fun JsonPrimitive.toOpenApiValue(): OpenApiValue = when {
        this is JsonNull -> OpenApiNull
        isString -> OpenApiString(content) // Need to check isString before the rest to verify we don't have a quoted literal
        doubleOrNull != null -> OpenApiNumber(double)
        longOrNull != null -> OpenApiNumber(long)
        booleanOrNull != null -> OpenApiBoolean(boolean)
        intOrNull != null -> OpenApiNumber(int)
        floatOrNull != null -> OpenApiNumber(float)
        else -> OpenApiString(content)
    }
}