package com.bselzer.library.kotlin.extension.geometry.dimension.bi.polygon

import com.bselzer.library.kotlin.extension.geometry.dimension.bi.position.Point2D
import com.bselzer.library.kotlin.extension.geometry.dimension.bi.serialization.QuadrilateralSerializer
import kotlinx.serialization.Serializable

/**
 * A polygon made of four points.
 */
@Serializable(with = QuadrilateralSerializer::class)
data class Quadrilateral(
    val point1: Point2D = Point2D(),
    val point2: Point2D = Point2D(),
    val point3: Point2D = Point2D(),
    val point4: Point2D = Point2D()
) : Polygon(listOf(point1, point2, point3, point4))