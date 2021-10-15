package com.bselzer.library.kotlin.extension.geometry.dimension

import com.bselzer.library.kotlin.extension.geometry.serialization.Dimension2DSerializer
import kotlinx.serialization.Serializable

@Serializable(with = Dimension2DSerializer::class)
data class Dimension2D(
    val width: Double = 0.0,
    val height: Double = 0.0
)