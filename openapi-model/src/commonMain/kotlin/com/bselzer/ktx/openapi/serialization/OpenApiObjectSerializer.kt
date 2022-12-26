package com.bselzer.ktx.openapi.serialization

import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.serializer

sealed class OpenApiObjectSerializer<T> : OpenApiModelSerializer<T>() {
    private val serializer = serializer<JsonObject>()

    override val descriptor: SerialDescriptor = serializer.descriptor

    override fun deserialize(decoder: Decoder): T {
        val jsonObject = serializer.deserialize(decoder)
        return deserialize(jsonObject)
    }

    internal fun deserialize(jsonObject: JsonObject): T = jsonObject.deserialize()
    protected abstract fun JsonObject.deserialize(): T

    override fun serialize(encoder: Encoder, value: T) {
        TODO("Not yet implemented")
    }
}