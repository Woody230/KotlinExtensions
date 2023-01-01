package com.bselzer.ktx.openapi.serialization

import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.serializer

internal sealed class OpenApiElementSerializer<T> : OpenApiModelSerializer<T>() {
    private val serializer = serializer<JsonElement>()

    override val descriptor: SerialDescriptor = serializer.descriptor

    override fun deserialize(decoder: Decoder): T {
        val jsonElement = serializer.deserialize(decoder)
        return deserialize(jsonElement)
    }

    internal fun deserialize(jsonElement: JsonElement): T = jsonElement.deserialize()
    protected abstract fun JsonElement.deserialize(): T

    override fun serialize(encoder: Encoder, value: T) {
        TODO("Not yet implemented")
    }
}