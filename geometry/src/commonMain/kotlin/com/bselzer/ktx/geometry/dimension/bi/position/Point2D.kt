package com.bselzer.ktx.geometry.dimension.bi.position

import com.bselzer.ktx.geometry.dimension.bi.serialization.Point2DSerializer
import kotlinx.serialization.Serializable

@Serializable(with = Point2DSerializer::class)
data class Point2D(
    override val x: Double = 0.0,
    override val y: Double = 0.0
) : Coordinates2D