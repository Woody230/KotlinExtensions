package com.bselzer.ktx.compose.ui.layout.box

import androidx.compose.ui.Alignment
import com.bselzer.ktx.compose.ui.layout.project.PresentationModel

data class BoxPresentation(
    /**
     * The default alignment inside the Box.
     */
    val contentAlignment: Alignment = Alignment.TopStart,

    /**
     * Whether the incoming min constraints should be passed to content.
     */
    val propagateMinConstraints: Boolean = false
) : PresentationModel