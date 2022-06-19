package com.bselzer.ktx.geometry.dimension.tri.position

import com.bselzer.ktx.geometry.dimension.bi.position.Coordinates2D

interface Coordinates3D : Coordinates2D {
    val z: Double

    override val isDefault: Boolean
        get() = super.isDefault && z == 0.0
}