package com.bselzer.ktx.serialization.serializer

import com.bselzer.ktx.geometry.dimension.bi.polygon.Quadrilateral
import com.bselzer.ktx.geometry.dimension.bi.position.Point2D

/**
 * A serializer for converting an array into a [Quadrilateral].
 */
class QuadrilateralSerializer : PolygonSerializer<Quadrilateral>() {
    override val serialName: String = Quadrilateral::class.qualifiedName!!
    override fun deserialize(points: List<Point2D>): Quadrilateral = Quadrilateral(points.at(0), points.at(1), points.at(2), points.at(3))
}