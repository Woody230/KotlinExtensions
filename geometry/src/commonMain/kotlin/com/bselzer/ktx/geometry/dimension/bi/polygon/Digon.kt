package com.bselzer.ktx.geometry.dimension.bi.polygon

import com.bselzer.ktx.geometry.dimension.bi.position.Point2D

/**
 * A polygon made of two points.
 */
data class Digon(
    val point1: Point2D = Point2D(),
    val point2: Point2D = Point2D()
) : Polygon(listOf(point1, point2))