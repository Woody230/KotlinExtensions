package com.bselzer.library.kotlin.extension.geometry.dimension.bi.serialization

import com.bselzer.library.kotlin.extension.geometry.dimension.bi.polygon.Polygon
import com.bselzer.library.kotlin.extension.geometry.dimension.bi.position.Point2D
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * A generic serializer for serializing polygons.
 */
abstract class PolygonSerializer<TPolygon : Polygon> : KSerializer<TPolygon> {
    /**
     * The serializer of the point data.
     */
    private val serializer = ListSerializer(ListSerializer(Double.serializer()))

    override val descriptor: SerialDescriptor = serializer.descriptor

    override fun deserialize(decoder: Decoder): TPolygon {
        val points = serializer.deserialize(decoder).map { components -> Point2D(components.getOrElse(0) { 0.0 }, components.getOrElse(1) { 0.0 }) }
        return deserialize(points)
    }

    /**
     * @return the point at the [index] or a default
     */
    protected fun List<Point2D>.at(index: Int) = getOrElse(index) { Point2D() }

    /**
     * @return the polygon constructed by the [points]
     */
    abstract fun deserialize(points: List<Point2D>): TPolygon

    override fun serialize(encoder: Encoder, value: TPolygon) {
        serializer.serialize(encoder, value.points.map { point -> listOf(point.x, point.y) })
    }
}