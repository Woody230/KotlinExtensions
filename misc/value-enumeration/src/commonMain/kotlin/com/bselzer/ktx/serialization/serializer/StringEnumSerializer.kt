package com.bselzer.ktx.serialization.serializer

import com.bselzer.ktx.value.enumeration.StringEnum
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class StringEnumSerializer<T>(private val enumSerializer: KSerializer<T>) : KSerializer<StringEnum<T>> where T : Enum<T> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(StringEnum::class.qualifiedName!!, PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): StringEnum<T> = StringEnum(decoder.decodeString(), enumSerializer)

    override fun serialize(encoder: Encoder, value: StringEnum<T>) {
        encoder.encodeString(value.toString())
    }
}