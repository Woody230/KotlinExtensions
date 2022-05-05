package com.bselzer.ktx.compose.ui.layout.image

import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import com.bselzer.ktx.compose.ui.layout.project.PresentationModel

data class ImagePresentation(
    /**
     * Alignment parameter used to place the Painter in the given bounds defined by the width and height.
     */
    val alignment: Alignment = Alignment.Center,

    /**
     * Scale parameter used to determine the aspect ratio scaling to be used if the bounds are a different size from the intrinsic size of the Painter
     */
    val contentScale: ContentScale = ContentScale.Fit,

    /**
     * Opacity to be applied to the Painter when it is rendered onscreen the default renders the Painter completely opaque
     */
    val alpha: Float = DefaultAlpha,

    /**
     * ColorFilter to apply for the Painter when it is rendered onscreen
     */
    val colorFilter: ColorFilter? = null
) : PresentationModel