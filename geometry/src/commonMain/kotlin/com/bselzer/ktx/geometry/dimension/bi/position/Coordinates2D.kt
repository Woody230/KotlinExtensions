package com.bselzer.ktx.geometry.dimension.bi.position

interface Coordinates2D {
    val x: Double
    val y: Double

    val isDefault: Boolean
        get() = x == 0.0 && y == 0.0
}