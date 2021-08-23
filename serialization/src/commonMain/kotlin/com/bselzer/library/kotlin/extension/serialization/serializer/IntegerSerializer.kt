package com.bselzer.library.kotlin.extension.serialization.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * The serializer for converting an object into an integer. Lenient serialization is required.
 */
open class IntegerSerializer : KSerializer<Int>
{
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Integer", PrimitiveKind.INT)

    override fun serialize(encoder: Encoder, value: Int)
    {
        encoder.encodeInt(value)
    }

    override fun deserialize(decoder: Decoder): Int
    {
        val decoded = decoder.decodeString()
        return when
        {
            // Attempt to convert from a boolean first.
            "true".equals(decoded, true) -> 1
            "false".equals(decoded, true) -> 0
            else -> decoded.toInt()
        }
    }
}