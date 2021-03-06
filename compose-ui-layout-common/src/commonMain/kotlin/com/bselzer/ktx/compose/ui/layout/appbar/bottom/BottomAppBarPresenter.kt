package com.bselzer.ktx.compose.ui.layout.appbar.bottom

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import com.bselzer.ktx.compose.ui.layout.appbar.action.ActionPolicy
import com.bselzer.ktx.compose.ui.layout.iconbutton.IconButtonPresenter
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.PresentableModifier
import com.bselzer.ktx.compose.ui.layout.project.Presentable
import com.bselzer.ktx.compose.ui.layout.project.Presenter
import com.bselzer.ktx.function.objects.safeMerge

data class BottomAppBarPresenter(
    override val modifier: PresentableModifier = PresentableModifier,

    /**
     * The [Presentable] for the navigation and action icons.
     */
    val icon: IconButtonPresenter = IconButtonPresenter.Default,

    /**
     * The policy for determining how to display the actions.
     */
    val actionPolicy: ActionPolicy = ActionPolicy,

    /**
     * The background color for the BottomAppBar. Use Color.Transparent to have no color.
     */
    val backgroundColor: Color = ComposeMerger.color.default,

    /**
     * The preferred content color provided by this BottomAppBar to its children.
     * Defaults to either the matching content color for backgroundColor, or if backgroundColor is not a color from the theme, this will keep the same value set above this BottomAppBar.
     */
    val contentColor: Color = ComposeMerger.color.default,

    /**
     * The elevation of this BottomAppBar.
     */
    val elevation: Dp = ComposeMerger.dp.default,

    /**
     * The padding applied to the content of this BottomAppBar
     */
    val contentPadding: PaddingValues = ComposeMerger.paddingValues.default,

    /**
     * The shape of the cutout that will be added to the BottomAppBar - this should typically be the same shape used inside the FloatingActionButton,
     * when BottomAppBar and FloatingActionButton are being used together in Scaffold.
     * This shape will be drawn with an offset around all sides. If null, where will be no cutout.
     */
    val cutoutShape: Shape? = ComposeMerger.shape.default
) : Presenter<BottomAppBarPresenter>(modifier) {
    companion object {
        @Stable
        val Default = BottomAppBarPresenter()
    }

    override fun safeMerge(other: BottomAppBarPresenter) = BottomAppBarPresenter(
        modifier = modifier.merge(other.modifier),
        icon = icon.merge(other.icon),
        actionPolicy = actionPolicy.safeMerge(other.actionPolicy, ActionPolicy),
        backgroundColor = ComposeMerger.color.safeMerge(backgroundColor, other.backgroundColor),
        contentColor = ComposeMerger.color.safeMerge(contentColor, other.contentColor),
        elevation = ComposeMerger.dp.safeMerge(elevation, other.elevation),
        contentPadding = ComposeMerger.paddingValues.safeMerge(contentPadding, other.contentPadding),
        cutoutShape = ComposeMerger.shape.nullMerge(cutoutShape, other.cutoutShape)
    )

    @Composable
    override fun localized() = BottomAppBarPresenter(
        backgroundColor = MaterialTheme.colors.primarySurface,
        elevation = AppBarDefaults.BottomAppBarElevation,
        cutoutShape = null,
        contentPadding = AppBarDefaults.ContentPadding
    ).merge(this).run {
        copy(
            contentColor = if (ComposeMerger.color.isDefault(contentColor)) contentColorFor(backgroundColor) else contentColor
        )
    }
}