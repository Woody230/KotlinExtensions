package com.bselzer.library.kotlin.extension.geometry.shape

import com.bselzer.library.kotlin.extension.geometry.position.Point2D
import com.bselzer.library.kotlin.extension.geometry.serialization.LineSerializer
import kotlinx.serialization.Serializable

@Serializable(with = LineSerializer::class)
data class Line(
    val x1: Double = 0.0,
    val y1: Double = 0.0,
    val x2: Double = 0.0,
    val y2: Double = 0.0
) {
    constructor(point1: Point2D, point2: Point2D) : this(point1.x, point1.y, point2.x, point2.y)
}