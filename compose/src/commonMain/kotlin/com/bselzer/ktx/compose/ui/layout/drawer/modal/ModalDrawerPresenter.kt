package com.bselzer.ktx.compose.ui.layout.drawer.modal

import androidx.compose.material.DrawerDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.compose.ui.layout.column.ColumnPresenter
import com.bselzer.ktx.compose.ui.layout.divider.DividerPresenter
import com.bselzer.ktx.compose.ui.layout.drawer.header.DrawerHeaderPresenter
import com.bselzer.ktx.compose.ui.layout.drawer.section.DrawerSectionPresenter
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.ModularPadding
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.ModularSize
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.PresentableModifier
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class ModalDrawerPresenter(
    override val modifier: PresentableModifier = PresentableModifier,
    val container: ColumnPresenter = ColumnPresenter.Default,
    val header: DrawerHeaderPresenter = DrawerHeaderPresenter.Default,
    val section: DrawerSectionPresenter = DrawerSectionPresenter.Default,

    /**
     * Shape of the drawer sheet
     */
    val shape: Shape = ComposeMerger.shape.default,

    /**
     * Drawer sheet elevation.
     * This controls the size of the shadow below the drawer sheet
     */
    val elevation: Dp = ComposeMerger.dp.default,

    /**
     * Background color to be used for the drawer sheet
     */
    val backgroundColor: Color = ComposeMerger.color.default,

    /**
     * Color of the content to use inside the drawer sheet.
     * Defaults to either the matching content color for drawerBackgroundColor, or, if it is not a color from the theme, this will keep the same value set above this Surface.
     */
    val contentColor: Color = ComposeMerger.color.default,

    /**
     * Color of the scrim that obscures content when the drawer is open
     */
    val scrimColor: Color = ComposeMerger.color.default
) : Presenter<ModalDrawerPresenter>(modifier) {
    companion object {
        @Stable
        val Default = ModalDrawerPresenter()
    }

    override fun safeMerge(other: ModalDrawerPresenter) = ModalDrawerPresenter(
        modifier = modifier.merge(other.modifier),
        container = container.merge(other.container),
        header = header.merge(other.header),
        section = section.merge(other.section),
        shape = ComposeMerger.shape.safeMerge(shape, other.shape),
        elevation = ComposeMerger.dp.safeMerge(elevation, other.elevation),
        backgroundColor = ComposeMerger.color.safeMerge(backgroundColor, other.backgroundColor),
        contentColor = ComposeMerger.color.safeMerge(contentColor, other.contentColor),
        scrimColor = ComposeMerger.color.safeMerge(scrimColor, other.scrimColor)
    )

    @Composable
    override fun localized() = ModalDrawerPresenter(
        container = ColumnPresenter(
            modifier = ModularSize.FillWidth then ModularPadding(all = 8.dp),
            divider = DividerPresenter(
                modifier = ModularPadding(vertical = 4.dp)
            )
        ),
        shape = MaterialTheme.shapes.large,
        elevation = DrawerDefaults.Elevation,
        backgroundColor = MaterialTheme.colors.surface,
        scrimColor = DrawerDefaults.scrimColor
    ).merge(this).run {
        copy(
            container = container.localized(),
            header = header.localized(),
            section = section.localized(),
            contentColor = if (ComposeMerger.color.isDefault(contentColor)) contentColorFor(backgroundColor) else contentColor
        )
    }
}