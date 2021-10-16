package com.bselzer.library.kotlin.extension.geometry.dimension.tri.serialization

import com.bselzer.library.kotlin.extension.geometry.dimension.tri.position.Point
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * A serializer for converting an array into a three-dimensional point.
 */
class PointSerializer : KSerializer<Point> {
    /**
     * The serializer of the 3D point data.
     */
    private val serializer = ListSerializer(Double.serializer())

    override val descriptor: SerialDescriptor = serializer.descriptor

    override fun deserialize(decoder: Decoder): Point {
        val list = serializer.deserialize(decoder)
        return Point(list.getOrElse(0) { 0.0 }, list.getOrElse(1) { 0.0 }, list.getOrElse(2) { 0.0 })
    }

    override fun serialize(encoder: Encoder, value: Point) {
        val list = listOf(value.x, value.y, value.z)
        serializer.serialize(encoder, list)
    }
}