package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.value.*
import kotlinx.serialization.json.*

internal object OpenApiValueSerializer : OpenApiElementSerializer<OpenApiValue>() {
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
        doubleOrNull != null -> OpenApiDouble(double)
        longOrNull != null -> OpenApiLong(long)
        booleanOrNull != null -> OpenApiBoolean(boolean)
        intOrNull != null -> OpenApiInt(int)
        floatOrNull != null -> OpenApiFloat(float)
        else -> OpenApiString(content)
    }
}