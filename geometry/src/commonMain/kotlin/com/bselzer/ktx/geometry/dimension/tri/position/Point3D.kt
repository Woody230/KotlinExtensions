package com.bselzer.ktx.geometry.dimension.tri.position

import com.bselzer.ktx.geometry.dimension.bi.position.Point2D
import com.bselzer.ktx.geometry.dimension.tri.serialization.Point3DSerializer
import kotlinx.serialization.Serializable

@Serializable(with = Point3DSerializer::class)
data class Point3D(
    override val x: Double = 0.0,
    override val y: Double = 0.0,
    override val z: Double = 0.0
) : Coordinates3D {
    /**
     * Converts this [Point3D] to a [Point2D].
     */
    fun toPoint2D(): Point2D = Point2D(x, y)
}