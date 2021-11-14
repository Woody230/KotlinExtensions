package com.bselzer.library.kotlin.extension.geometry.dimension.tri.position

import com.bselzer.library.kotlin.extension.geometry.dimension.tri.serialization.Point3DSerializer
import kotlinx.serialization.Serializable

@Serializable(with = Point3DSerializer::class)
data class Point3D(
    val x: Double = 0.0,
    val y: Double = 0.0,
    val z: Double = 0.0
)