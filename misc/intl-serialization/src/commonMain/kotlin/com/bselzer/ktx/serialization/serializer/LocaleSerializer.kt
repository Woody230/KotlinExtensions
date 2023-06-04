package com.bselzer.ktx.serialization.serializer

import com.bselzer.ktx.intl.Locale
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * A serializer for [Locale] using a string representation.
 */
class LocaleSerializer : KSerializer<Locale> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(Locale::class.qualifiedName!!, PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Locale = Locale(decoder.decodeString())

    override fun serialize(encoder: Encoder, value: Locale) {
        encoder.encodeString(value.languageTag)
    }
}