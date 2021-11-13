package com.bselzer.library.kotlin.extension.geometry.dimension.bi.serialization

import com.bselzer.library.kotlin.extension.geometry.dimension.bi.polygon.Digon
import com.bselzer.library.kotlin.extension.geometry.dimension.bi.position.Point2D

/**
 * A serializer for converting an array into a [Digon].
 */
class DigonSerializer : PolygonSerializer<Digon>() {
    override fun deserialize(points: List<Point2D>): Digon = Digon(points.at(0), points.at(1))
}