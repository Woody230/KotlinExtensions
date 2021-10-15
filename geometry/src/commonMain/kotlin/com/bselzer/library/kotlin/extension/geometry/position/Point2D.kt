package com.bselzer.library.kotlin.extension.geometry.position

import com.bselzer.library.kotlin.extension.geometry.serialization.Point2DSerializer
import kotlinx.serialization.Serializable

@Serializable(with = Point2DSerializer::class)
data class Point2D(
    val x: Double = 0.0,
    val y: Double = 0.0
)