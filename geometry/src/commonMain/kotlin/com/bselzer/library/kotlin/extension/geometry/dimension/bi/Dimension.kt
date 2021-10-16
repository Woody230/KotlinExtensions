package com.bselzer.library.kotlin.extension.geometry.dimension.bi

import com.bselzer.library.kotlin.extension.geometry.dimension.bi.serialization.DimensionSerializer
import kotlinx.serialization.Serializable

@Serializable(with = DimensionSerializer::class)
data class Dimension(
    val width: Double = 0.0,
    val height: Double = 0.0
)