package com.bselzer.ktx.datetime.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.time.Duration

/**
 * A serializer for [Duration] using a string representation.
 */
class DurationSerializer: KSerializer<Duration> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Duration", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Duration = Duration.parse(decoder.decodeString())

    override fun serialize(encoder: Encoder, value: Duration) {
        encoder.encodeString(value.toIsoString())
    }
}