package com.bselzer.ktx.geometry.dimension.tri.position

data class Point3D(
    override val x: Double = 0.0,
    override val y: Double = 0.0,
    override val z: Double = 0.0
) : Coordinates3D {
    override fun toString(): String = "[$x,$y,$z]"
}