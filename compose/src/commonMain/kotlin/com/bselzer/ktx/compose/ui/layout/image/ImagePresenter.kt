package com.bselzer.ktx.compose.ui.layout.image

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.PresentableModifier
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class ImagePresenter(
    override val modifier: PresentableModifier = PresentableModifier,

    /**
     * Alignment parameter used to place the Painter in the given bounds defined by the width and height.
     */
    val alignment: Alignment = ComposeMerger.alignment.default,

    /**
     * Scale parameter used to determine the aspect ratio scaling to be used if the bounds are a different size from the intrinsic size of the Painter
     */
    val contentScale: ContentScale = ComposeMerger.contentScale.default,

    /**
     * Opacity to be applied to the Painter when it is rendered onscreen the default renders the Painter completely opaque
     */
    val alpha: Float = ComposeMerger.float.default,

    /**
     * ColorFilter to apply for the Painter when it is rendered onscreen
     */
    val colorFilter: ColorFilter? = ComposeMerger.colorFilter.default
) : Presenter<ImagePresenter>(modifier) {
    companion object {
        @Stable
        val Default = ImagePresenter()
    }

    override fun safeMerge(other: ImagePresenter) = ImagePresenter(
        modifier = modifier.merge(other.modifier),
        alignment = ComposeMerger.alignment.safeMerge(alignment, other.alignment),
        contentScale = ComposeMerger.contentScale.safeMerge(contentScale, other.contentScale),
        alpha = ComposeMerger.float.safeMerge(alpha, other.alpha),
        colorFilter = ComposeMerger.colorFilter.nullMerge(colorFilter, other.colorFilter)
    )

    @Composable
    override fun localized() = ImagePresenter(
        alignment = Alignment.Center,
        contentScale = ContentScale.Fit,
        alpha = DefaultAlpha,
        colorFilter = null
    ).merge(this)
}