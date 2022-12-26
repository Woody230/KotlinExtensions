package com.bselzer.ktx.openapi.serialization

import com.bselzer.ktx.openapi.model.OpenApiReferenceOf
import kotlinx.serialization.json.JsonObject

class OpenApiReferenceOfSerializer<T>(
    private val valueSerializer: OpenApiObjectSerializer<T>
) : OpenApiObjectSerializer<OpenApiReferenceOf<T>>() {
    private val referenceSerializer = OpenApiReferenceSerializer()

    override fun JsonObject.deserialize(): OpenApiReferenceOf<T> {
        if (containsKey("\$ref")) {
            val reference = referenceSerializer.deserialize(this)
            return OpenApiReferenceOf(reference)
        }

        val value = valueSerializer.deserialize(this)
        return OpenApiReferenceOf(value)
    }
}