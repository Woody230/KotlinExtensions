package com.bselzer.library.kotlin.extension.geometry.serialization

import com.bselzer.library.kotlin.extension.geometry.shape.Rectangle
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * A serializer for converting an array into a rectangle.
 */
class RectangleSerializer : KSerializer<Rectangle> {
    /**
     * The serializer of the rectangle data.
     */
    private val serializer = ListSerializer(ListSerializer(Double.serializer()))

    override val descriptor: SerialDescriptor = serializer.descriptor

    override fun deserialize(decoder: Decoder): Rectangle {
        val list = serializer.deserialize(decoder)
        val point1 = list.firstOrNull() ?: emptyList()
        val point2 = list.getOrNull(1) ?: emptyList()
        val x1 = point1.getOrElse(0) { 0.0 }
        val y1 = point1.getOrElse(1) { 0.0 }
        val x2 = point2.getOrElse(0) { 0.0 }
        val y2 = point2.getOrElse(1) { 0.0 }
        return Rectangle(x1, y1, x2, y2)
    }

    override fun serialize(encoder: Encoder, value: Rectangle) {
        val list = listOf(listOf(value.x1, value.y1), listOf(value.x2, value.y2))
        serializer.serialize(encoder, list)
    }
}