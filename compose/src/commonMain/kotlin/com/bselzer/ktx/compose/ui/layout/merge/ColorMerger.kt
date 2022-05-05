package com.bselzer.ktx.compose.ui.layout.merge

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

internal class ColorMerger : ComponentMerger<Color> {
    override val default: Color = Default

    companion object {
        @Stable
        val Default = Color.Unspecified
    }
}