package com.bselzer.ktx.geometry.dimension.bi.polygon

import com.bselzer.ktx.geometry.dimension.bi.position.Point2D
import com.bselzer.ktx.geometry.dimension.bi.serialization.DigonSerializer
import kotlinx.serialization.Serializable

/**
 * A polygon made of two points.
 */
@Serializable(with = DigonSerializer::class)
data class Digon(
    val point1: Point2D = Point2D(),
    val point2: Point2D = Point2D()
) : Polygon(listOf(point1, point2))