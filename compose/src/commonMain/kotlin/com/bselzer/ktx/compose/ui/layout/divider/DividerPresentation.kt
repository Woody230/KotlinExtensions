package com.bselzer.ktx.compose.ui.layout.divider

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.project.PresentationModel

data class DividerPresentation(
    /**
     * Color of the divider line.
     */
    val color: Color = ComposeMerger.color.default,

    /**
     * Thickness of the divider line, 1 dp is used by default. Using Dp.Hairline will produce a single pixel divider regardless of screen density.
     */
    val thickness: Dp = 1.dp,

    /**
     * Start offset of this line, no offset by default.
     */
    val startIndent: Dp = 0.dp,
) : PresentationModel