package com.bselzer.library.kotlin.extension.geometry.dimension.tri.position

import com.bselzer.library.kotlin.extension.geometry.dimension.tri.serialization.PointSerializer
import kotlinx.serialization.Serializable

@Serializable(with = PointSerializer::class)
data class Point(
    val x: Double = 0.0,
    val y: Double = 0.0,
    val z: Double = 0.0
)