package com.bselzer.ktx.compose.ui.layout.merge

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter

class ColorFilterMerger : ComponentMerger<ColorFilter> {
    override val default: ColorFilter = Default

    companion object {
        @Stable
        val Default = ColorFilter.tint(Color.Unspecified)
    }
}