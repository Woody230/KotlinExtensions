package com.bselzer.library.kotlin.extension.geometry.shape

import com.bselzer.library.kotlin.extension.geometry.serialization.RectangleSerializer
import kotlinx.serialization.Serializable

@Serializable(with = RectangleSerializer::class)
data class Rectangle(
    val x1: Double = 0.0,
    val y1: Double = 0.0,
    val x2: Double = 0.0,
    val y2: Double = 0.0
)