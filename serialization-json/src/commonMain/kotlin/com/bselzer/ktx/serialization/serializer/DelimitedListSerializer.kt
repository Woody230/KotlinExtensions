package com.bselzer.ktx.serialization.serializer

import com.bselzer.ktx.serialization.context.JsonContext
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.jsonPrimitive

/**
 * Represents a serializer for a delimited list of values that are encoded and decoded as primitives.
 */
open class DelimitedListSerializer<T>(
    private val serializer: KSerializer<T>,
) : KSerializer<List<T>> {
    protected open val delimiter: String = "|"

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("DelimitedList", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): List<T> = with(JsonContext) {
        decoder.decodeString().split(delimiter).decode(serializer)
    }

    override fun serialize(encoder: Encoder, value: List<T>) = with(JsonContext) {
        val delimited = value.joinToString(delimiter) { value -> instance.encodeToJsonElement(serializer, value).jsonPrimitive.content }
        encoder.encodeString(delimited)
    }
}