package com.bselzer.library.kotlin.extension.geometry.dimension.bi.position

import com.bselzer.library.kotlin.extension.geometry.dimension.bi.serialization.PointSerializer
import kotlinx.serialization.Serializable

@Serializable(with = PointSerializer::class)
data class Point(
    val x: Double = 0.0,
    val y: Double = 0.0
)