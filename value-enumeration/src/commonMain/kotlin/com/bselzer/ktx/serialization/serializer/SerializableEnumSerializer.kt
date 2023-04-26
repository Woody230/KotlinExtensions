package com.bselzer.ktx.serialization.serializer

import com.bselzer.ktx.value.enumeration.SerializableEnum
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class SerializableEnumSerializer<T>(private val enumSerializer: KSerializer<T>) : KSerializer<SerializableEnum<T>> where T : Enum<T> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("SerializableEnum", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): SerializableEnum<T> = SerializableEnum(decoder.decodeString(), enumSerializer)

    override fun serialize(encoder: Encoder, value: SerializableEnum<T>) {
        encoder.encodeString(value.toString())
    }
}