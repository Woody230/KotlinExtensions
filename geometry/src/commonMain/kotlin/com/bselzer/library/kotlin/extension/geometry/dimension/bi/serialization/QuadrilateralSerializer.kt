package com.bselzer.library.kotlin.extension.geometry.dimension.bi.serialization

import com.bselzer.library.kotlin.extension.geometry.dimension.bi.polygon.Quadrilateral
import com.bselzer.library.kotlin.extension.geometry.dimension.bi.position.Point

/**
 * A serializer for converting an array into a [Quadrilateral].
 */
class QuadrilateralSerializer : PolygonSerializer<Quadrilateral>() {
    override fun deserialize(points: List<Point>): Quadrilateral = Quadrilateral(points.at(0), points.at(1), points.at(2), points.at(3))
}