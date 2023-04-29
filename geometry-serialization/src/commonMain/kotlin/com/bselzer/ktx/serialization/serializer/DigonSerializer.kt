package com.bselzer.ktx.serialization.serializer

import com.bselzer.ktx.geometry.dimension.bi.polygon.Digon
import com.bselzer.ktx.geometry.dimension.bi.position.Point2D

/**
 * A serializer for converting an array into a [Digon].
 */
class DigonSerializer : PolygonSerializer<Digon>() {
    override val serialName: String = Digon::class.qualifiedName!!
    override fun deserialize(points: List<Point2D>): Digon = Digon(points.at(0), points.at(1))
}