package com.bselzer.library.kotlin.extension.geometry.dimension.tri.position

import com.bselzer.library.kotlin.extension.geometry.dimension.bi.position.Point2D
import com.bselzer.library.kotlin.extension.geometry.dimension.tri.serialization.Point3DSerializer
import kotlinx.serialization.Serializable

@Serializable(with = Point3DSerializer::class)
data class Point3D(
    val x: Double = 0.0,
    val y: Double = 0.0,
    val z: Double = 0.0
) {
    /**
     * Converts this [Point3D] to a [Point2D].
     */
    fun toPoint2D(): Point2D = Point2D(x, y)

    /**
     * @return whether this [Point3D] is a default point
     */
    fun isDefault(): Boolean = this == Point3D()
}