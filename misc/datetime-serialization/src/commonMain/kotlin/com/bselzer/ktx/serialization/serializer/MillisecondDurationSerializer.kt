package com.bselzer.ktx.serialization.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.DurationUnit

/**
 * A serializer for milliseconds in the form of a [Duration]
 */
class MillisecondDurationSerializer : KSerializer<Duration> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(MillisecondDurationSerializer::class.qualifiedName!!, PrimitiveKind.DOUBLE)

    override fun deserialize(decoder: Decoder): Duration = decoder.decodeDouble().milliseconds

    override fun serialize(encoder: Encoder, value: Duration) {
        encoder.encodeDouble(value.toDouble(DurationUnit.MILLISECONDS))
    }
}