package com.bselzer.ktx.geometry.dimension.bi.position

import com.bselzer.ktx.geometry.dimension.bi.serialization.Point2DSerializer
import kotlinx.serialization.Serializable

@Serializable(with = Point2DSerializer::class)
data class Point2D(
    val x: Double = 0.0,
    val y: Double = 0.0
) {
    /**
     * @return whether this [Point2D] is a default point
     */
    fun isDefault(): Boolean = this == Point2D()
}