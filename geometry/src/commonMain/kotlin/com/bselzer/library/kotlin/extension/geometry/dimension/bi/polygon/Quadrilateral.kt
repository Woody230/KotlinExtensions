package com.bselzer.library.kotlin.extension.geometry.dimension.bi.polygon

import com.bselzer.library.kotlin.extension.geometry.dimension.bi.position.Point
import com.bselzer.library.kotlin.extension.geometry.dimension.bi.serialization.QuadrilateralSerializer
import kotlinx.serialization.Serializable

@Serializable(with = QuadrilateralSerializer::class)
data class Quadrilateral(
    val point1: Point = Point(),
    val point2: Point = Point(),
    val point3: Point = Point(),
    val point4: Point = Point()
) : Polygon(listOf(point1, point2, point3, point4))