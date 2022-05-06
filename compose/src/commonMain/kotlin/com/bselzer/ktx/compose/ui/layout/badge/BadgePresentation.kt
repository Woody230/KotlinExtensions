package com.bselzer.ktx.compose.ui.layout.badge

import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class BadgePresentation(
    /**
     * The background color for the badge
     */
    val backgroundColor: Color = ComposeMerger.color.default,

    /**
     * The color of label text rendered in the badge
     */
    val contentColor: Color = ComposeMerger.color.default,
) : Presenter<BadgePresentation>() {
    override fun safeMerge(other: BadgePresentation) = BadgePresentation(
        backgroundColor = ComposeMerger.color.safeMerge(backgroundColor, other.backgroundColor),
        contentColor = ComposeMerger.color.safeMerge(contentColor, other.contentColor)
    )

    @Composable
    override fun localized(): BadgePresentation {
        val localized = super.localized()

        return if (ComposeMerger.color.isDefault(localized.contentColor)) {
            localized.copy(contentColor = contentColorFor(localized.backgroundColor))
        } else {
            localized
        }
    }

    @Composable
    override fun createLocalization() = BadgePresentation(
        backgroundColor = MaterialTheme.colors.error,
    )
}