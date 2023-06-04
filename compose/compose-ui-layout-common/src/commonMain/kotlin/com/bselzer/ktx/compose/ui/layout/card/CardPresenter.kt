package com.bselzer.ktx.compose.ui.layout.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.PresentableModifier
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class CardPresenter(
    override val modifier: PresentableModifier = PresentableModifier,

    /**
     * Defines the card's shape as well its shadow. A shadow is only displayed if the elevation is greater than zero.
     */
    val shape: Shape = ComposeMerger.shape.default,

    /**
     * The background color.
     */
    val backgroundColor: Color = ComposeMerger.color.default,

    /**
     * The preferred content color provided by this card to its children. Defaults to either the matching content color for backgroundColor, or if backgroundColor is not a color from the theme, this will keep the same value set above this card.
     */
    val contentColor: Color = ComposeMerger.color.default,

    /**
     * Border to draw on top of the card
     */
    val border: BorderStroke? = ComposeMerger.borderStroke.default,

    /**
     * The z-coordinate at which to place this card. This controls the size of the shadow below the card.
     */
    val elevation: Dp = ComposeMerger.dp.default
) : Presenter<CardPresenter>(modifier) {
    companion object {
        @Stable
        val Default = CardPresenter()
    }

    override fun safeMerge(other: CardPresenter) = CardPresenter(
        modifier = modifier.merge(other.modifier),
        shape = ComposeMerger.shape.safeMerge(shape, other.shape),
        backgroundColor = ComposeMerger.color.safeMerge(backgroundColor, other.backgroundColor),
        contentColor = ComposeMerger.color.safeMerge(contentColor, other.contentColor),
        border = ComposeMerger.borderStroke.nullMerge(border, other.border),
        elevation = ComposeMerger.dp.safeMerge(elevation, other.elevation)
    )

    @Composable
    override fun localized() = CardPresenter(
        shape = RectangleShape,
        backgroundColor = MaterialTheme.colors.surface,
        border = null,
        elevation = 0.dp
    ).merge(this).run {
        if (ComposeMerger.color.isDefault(contentColor)) {
            copy(contentColor = contentColorFor(backgroundColor))
        } else {
            this
        }
    }
}