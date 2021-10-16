package com.bselzer.library.kotlin.extension.geometry.dimension.bi.polygon

import com.bselzer.library.kotlin.extension.geometry.dimension.bi.position.Point
import com.bselzer.library.kotlin.extension.geometry.dimension.bi.serialization.DigonSerializer
import kotlinx.serialization.Serializable

/**
 * A plo
 */
@Serializable(with = DigonSerializer::class)
data class Digon(
    val point1: Point = Point(),
    val point2: Point = Point()
) : Polygon(listOf(point1, point2))