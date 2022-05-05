package com.bselzer.ktx.compose.ui.layout.icon

import androidx.compose.ui.graphics.Color
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.project.PresentationModel

data class IconPresentation(
    /**
     * Tint to be applied to imageVector. If Color.Unspecified is provided, then no tint is applied
     */
    val tint: Color = ComposeMerger.color.default
) : PresentationModel