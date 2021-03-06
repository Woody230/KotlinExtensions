package com.bselzer.ktx.compose.ui.layout.appbar.top

import androidx.compose.material.AppBarDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import com.bselzer.ktx.compose.ui.layout.appbar.action.ActionPolicy
import com.bselzer.ktx.compose.ui.layout.iconbutton.IconButtonPresenter
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.PresentableModifier
import com.bselzer.ktx.compose.ui.layout.project.Presentable
import com.bselzer.ktx.compose.ui.layout.project.Presenter
import com.bselzer.ktx.compose.ui.layout.text.TextPresenter
import com.bselzer.ktx.function.objects.safeMerge

data class TopAppBarPresenter(
    override val modifier: PresentableModifier = PresentableModifier,

    /**
     * The [Presentable] for the title.
     */
    val title: TextPresenter = TextPresenter.Default,

    /**
     * The [Presentable] for the navigation and action icons.
     */
    val icon: IconButtonPresenter = IconButtonPresenter.Default,

    /**
     * The policy for determining how to display the actions.
     */
    val actionPolicy: ActionPolicy = ActionPolicy,

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
) : Presenter<TopAppBarPresenter>(modifier) {
    companion object {
        @Stable
        val Default = TopAppBarPresenter()
    }

    override fun safeMerge(other: TopAppBarPresenter) = TopAppBarPresenter(
        modifier = modifier.merge(other.modifier),
        title = title.merge(other.title),
        icon = icon.merge(other.icon),
        actionPolicy = actionPolicy.safeMerge(other.actionPolicy, ActionPolicy),
        backgroundColor = ComposeMerger.color.safeMerge(backgroundColor, other.backgroundColor),
        contentColor = ComposeMerger.color.safeMerge(contentColor, other.contentColor),
        elevation = ComposeMerger.dp.safeMerge(elevation, other.elevation)
    )

    @Composable
    override fun localized() = TopAppBarPresenter(
        title = TextPresenter(
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            textStyle = MaterialTheme.typography.h6
        ),
        backgroundColor = MaterialTheme.colors.primarySurface,
        elevation = AppBarDefaults.TopAppBarElevation
    ).merge(this).run {
        copy(
            contentColor = if (ComposeMerger.color.isDefault(contentColor)) contentColorFor(backgroundColor) else contentColor
        )
    }
}