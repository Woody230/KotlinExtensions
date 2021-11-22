package com.bselzer.library.kotlin.extension.geometry.dimension.bi.serialization

import com.bselzer.library.kotlin.extension.geometry.dimension.bi.position.Point2D
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

    override fun deserialize(decoder: Decoder): Point2D = deserialize(serializer.deserialize(decoder))

    /**
     * Converts a list to a [Point2D].
     *
     * @param components the two-dimensional components
     */
    fun deserialize(components: List<Double>) = Point2D(components.getOrElse(0) { 0.0 }, components.getOrElse(1) { 0.0 })

    override fun serialize(encoder: Encoder, value: Point2D) {
        val list = listOf(value.x, value.y)
        serializer.serialize(encoder, list)
    }
}