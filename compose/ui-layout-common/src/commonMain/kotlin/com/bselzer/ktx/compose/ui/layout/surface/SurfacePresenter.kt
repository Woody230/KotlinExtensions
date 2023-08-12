package com.bselzer.ktx.compose.ui.layout.surface

import androidx.compose.foundation.BorderStroke
import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.PresentableModifier
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class SurfacePresenter(
    override val modifier: PresentableModifier = PresentableModifier,

    /**
     * Defines the Surface's shape as well its shadow. A shadow is only displayed if the elevation is greater than zero.
     */
    val shape: Shape = ComposeMerger.shape.default,

    /**
     * The background color. Use Color.Transparent to have no color.
     */
    val color: Color = ComposeMerger.color.default,

    /**
     * The preferred content color provided by this Surface to its children. Defaults to either the matching content color for backgroundColor, or if backgroundColor is not a color from the theme, this will keep the same value set above this Surface.
     */
    val contentColor: Color = ComposeMerger.color.default,

    /**
     * Border to draw on top of the Surface
     */
    val border: BorderStroke? = ComposeMerger.borderStroke.default,

    /**
     * The z-coordinate at which to place this Surface. This controls the size of the shadow below the Surface.
     */
    val elevation: Dp = ComposeMerger.dp.default
) : Presenter<SurfacePresenter>(modifier) {
    companion object {
        @Stable
        val Default = SurfacePresenter()
    }

    override fun safeMerge(other: SurfacePresenter) = SurfacePresenter(
        modifier = modifier.merge(other.modifier),
        shape = ComposeMerger.shape.safeMerge(shape, other.shape),
        color = ComposeMerger.color.safeMerge(color, other.color),
        contentColor = ComposeMerger.color.safeMerge(contentColor, other.contentColor),
        border = ComposeMerger.borderStroke.nullMerge(border, other.border),
        elevation = ComposeMerger.dp.safeMerge(elevation, other.elevation)
    )

    @Composable
    override fun localized() = SurfacePresenter(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colors.surface,
        border = null,
        elevation = 0.dp
    ).merge(this).run {
        if (ComposeMerger.color.isDefault(contentColor)) {
            copy(contentColor = contentColorFor(color))
        } else {
            this
        }
    }
}