package com.bselzer.ktx.geometry.dimension.tri.position

import com.bselzer.ktx.geometry.dimension.tri.serialization.Point3DSerializer
import kotlinx.serialization.Serializable

@Serializable(with = Point3DSerializer::class)
data class Point3D(
    override val x: Double = 0.0,
    override val y: Double = 0.0,
    override val z: Double = 0.0
) : Coordinates3D {
    override fun toString(): String = "[$x,$y,$z]"
}