package com.bselzer.ktx.geometry.dimension.bi.position

interface Coordinates2D {
    val x: Double
    val y: Double

    fun toPoint2D(): Point2D = Point2D(x, y)

    val isDefault: Boolean
        get() = x == 0.0 && y == 0.0
}