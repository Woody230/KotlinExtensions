package com.bselzer.library.kotlin.extension.geometry.serialization

import com.bselzer.library.kotlin.extension.geometry.position.Point2D
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * A serializer for converting an array into a two-dimensional point.
 */
class Point2DSerializer : KSerializer<Point2D> {
    /**
     * The serializer of the 2D point data.
     */
    private val serializer = ListSerializer(Double.serializer())

    override val descriptor: SerialDescriptor = serializer.descriptor

    override fun deserialize(decoder: Decoder): Point2D {
        val list = serializer.deserialize(decoder)
        return Point2D(list.getOrElse(0) { 0.0 }, list.getOrElse(1) { 0.0 })
    }

    override fun serialize(encoder: Encoder, value: Point2D) {
        val list = listOf(value.x, value.y)
        serializer.serialize(encoder, list)
    }
}