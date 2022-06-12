package com.bselzer.ktx.geometry.dimension.bi

interface Size2D {
    val width: Double
    val height: Double

    val isDefault: Boolean
        get() = width == 0.0 && height == 0.0
}