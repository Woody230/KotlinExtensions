package com.bselzer.ktx.serialization.context

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import nl.adaptivity.xmlutil.serialization.XML
import nl.adaptivity.xmlutil.serialization.XmlValue

open class XmlContext(
    override val instance: XML
) : StringFormatContext(instance) {
    companion object Default : XmlContext(XML.defaultInstance)

    internal class Wrapper<T>(
        @XmlValue
        val value: T
    )

    internal class WrapperSerializer<T>(
        private val valueSerializer: DeserializationStrategy<T>
    ) : DeserializationStrategy<Wrapper<T>> {
        @OptIn(ExperimentalSerializationApi::class)
        override val descriptor: SerialDescriptor = SerialDescriptor("Wrapper", valueSerializer.descriptor)

        override fun deserialize(decoder: Decoder): Wrapper<T> {
            val value = decoder.decodeSerializableValue(valueSerializer)
            return Wrapper(value)
        }
    }

    override fun <T> String.decode(deserializer: DeserializationStrategy<T>): T {
        val wrapper = WrapperSerializer(deserializer)
        return instance.decodeFromString(wrapper, "<Wrapper>$this</Wrapper>").value
    }
}