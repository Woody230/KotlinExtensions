package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.OpenApiReferenceOf
import kotlinx.serialization.json.JsonObject

class OpenApiReferenceOfSerializer<T>(
    private val valueSerializer: OpenApiObjectSerializer<T>
) : OpenApiObjectSerializer<OpenApiReferenceOf<T>>() {
    override fun JsonObject.deserialize(): OpenApiReferenceOf<T> {
        if (containsKey("\$ref")) {
            val reference = OpenApiReferenceSerializer.deserialize(this)
            return OpenApiReferenceOf(reference)
        }

        val value = valueSerializer.deserialize(this)
        return OpenApiReferenceOf(value)
    }
}