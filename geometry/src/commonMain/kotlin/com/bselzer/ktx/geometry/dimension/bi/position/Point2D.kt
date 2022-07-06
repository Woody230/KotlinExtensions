package com.bselzer.ktx.geometry.dimension.bi.position

data class Point2D(
    override val x: Double = 0.0,
    override val y: Double = 0.0
) : Coordinates2D {
    override fun toString(): String = "[$x,$y]"
    override fun toPoint2D(): Point2D = this
}