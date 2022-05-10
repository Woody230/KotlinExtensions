package com.bselzer.ktx.compose.ui.layout.modifier

import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

data class Size(
    val width: Dp = Dp.Unspecified,
    val height: Dp = Dp.Unspecified
) : Modifiable {
    override val modifier: Modifier = Modifier.size(width = width, height = height)
}