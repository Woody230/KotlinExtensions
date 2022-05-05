package com.bselzer.ktx.compose.ui.layout.badge

import androidx.compose.ui.graphics.Color
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.project.PresentationModel

data class BadgePresentation(
    /**
     * The background color for the badge
     */
    val backgroundColor: Color = ComposeMerger.color.default,

    /**
     * The color of label text rendered in the badge
     */
    val contentColor: Color = ComposeMerger.color.default,
) : PresentationModel