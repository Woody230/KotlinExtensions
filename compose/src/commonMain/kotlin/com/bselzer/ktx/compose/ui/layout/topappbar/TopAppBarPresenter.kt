package com.bselzer.ktx.compose.ui.layout.topappbar

import androidx.compose.material.AppBarDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.bselzer.ktx.compose.ui.layout.iconbutton.IconButtonPresenter
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.modifier.PresentableModifiers
import com.bselzer.ktx.compose.ui.layout.project.Presentable
import com.bselzer.ktx.compose.ui.layout.project.Presenter
import com.bselzer.ktx.compose.ui.layout.text.TextPresenter

data class TopAppBarPresenter(
    override val modifiers: PresentableModifiers = PresentableModifiers.Default,

    /**
     * The [Presentable] for the title.
     */
    val title: TextPresenter = TextPresenter.Default,

    /**
     * The [Presentable] for the navigation and action icons.
     */
    val icon: IconButtonPresenter = IconButtonPresenter.Default,

    /**
     * The background color for the TopAppBar. Use Color.Transparent to have no color.
     */
    val backgroundColor: Color = ComposeMerger.color.default,

    /**
     * The preferred content color provided by this TopAppBar to its children.
     * Defaults to either the matching content color for backgroundColor, or if backgroundColor is not a color from the theme, this will keep the same value set above this TopAppBar.
     */
    val contentColor: Color = ComposeMerger.color.default,

    /**
     * The elevation of this TopAppBar.
     */
    val elevation: Dp = ComposeMerger.dp.default
) : Presenter<TopAppBarPresenter>(modifiers) {
    companion object {
        @Stable
        val Default = TopAppBarPresenter()
    }

    override fun safeMerge(other: TopAppBarPresenter) = TopAppBarPresenter(
        modifiers = modifiers.merge(other.modifiers),
        title = title.merge(other.title),
        icon = icon.merge(other.icon),
        backgroundColor = ComposeMerger.color.safeMerge(backgroundColor, other.backgroundColor),
        contentColor = ComposeMerger.color.safeMerge(contentColor, other.contentColor),
        elevation = ComposeMerger.dp.safeMerge(elevation, other.elevation)
    )

    @Composable
    override fun localized() = TopAppBarPresenter(
        backgroundColor = MaterialTheme.colors.primarySurface,
        elevation = AppBarDefaults.TopAppBarElevation
    ).merge(this).run {
        copy(
            title = title.localized(),
            icon = icon.localized(),
            contentColor = if (ComposeMerger.color.isDefault(contentColor)) contentColorFor(backgroundColor) else contentColor
        )
    }
}