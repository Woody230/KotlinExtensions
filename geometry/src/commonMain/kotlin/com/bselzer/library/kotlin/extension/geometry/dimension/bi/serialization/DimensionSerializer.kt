package com.bselzer.library.kotlin.extension.geometry.dimension.bi.serialization

import com.bselzer.library.kotlin.extension.geometry.dimension.bi.Dimension
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * A serializer for converting an array into a two-dimensional size.
 */
class DimensionSerializer : KSerializer<Dimension> {
    /**
     * The serializer of the 2D dimension data.
     */
    private val serializer = ListSerializer(Double.serializer())

    override val descriptor: SerialDescriptor = serializer.descriptor

    override fun deserialize(decoder: Decoder): Dimension {
        val list = serializer.deserialize(decoder)
        return Dimension(list.getOrElse(0) { 0.0 }, list.getOrElse(1) { 0.0 })
    }

    override fun serialize(encoder: Encoder, value: Dimension) {
        val list = listOf(value.width, value.height)
        serializer.serialize(encoder, list)
    }
}