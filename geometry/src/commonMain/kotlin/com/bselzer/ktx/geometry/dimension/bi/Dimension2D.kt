package com.bselzer.ktx.geometry.dimension.bi

data class Dimension2D(
    override val width: Double = 0.0,
    override val height: Double = 0.0
) : Size2D {
    override fun toString(): String = "${width}x${height}"
}