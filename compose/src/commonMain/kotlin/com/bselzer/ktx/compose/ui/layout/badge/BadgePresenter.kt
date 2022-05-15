package com.bselzer.ktx.compose.ui.layout.badge

import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.PresentableModifier
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class BadgePresenter(
    override val modifier: PresentableModifier = PresentableModifier,

    /**
     * The background color for the badge
     */
    val backgroundColor: Color = ComposeMerger.color.default,

    /**
     * The color of label text rendered in the badge
     */
    val contentColor: Color = ComposeMerger.color.default,
) : Presenter<BadgePresenter>(modifier) {
    companion object {
        @Stable
        val Default = BadgePresenter()
    }

    override fun safeMerge(other: BadgePresenter) = BadgePresenter(
        modifier = modifier.merge(other.modifier),
        backgroundColor = ComposeMerger.color.safeMerge(backgroundColor, other.backgroundColor),
        contentColor = ComposeMerger.color.safeMerge(contentColor, other.contentColor)
    )

    @Composable
    override fun localized(): BadgePresenter = BadgePresenter(
        backgroundColor = MaterialTheme.colors.error
    ).merge(this).run {
        if (ComposeMerger.color.isDefault(contentColor)) {
            copy(contentColor = contentColorFor(backgroundColor))
        } else {
            this
        }
    }
}