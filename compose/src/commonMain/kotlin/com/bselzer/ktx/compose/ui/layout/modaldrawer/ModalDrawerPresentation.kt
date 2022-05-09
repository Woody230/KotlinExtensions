package com.bselzer.ktx.compose.ui.layout.modaldrawer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.DrawerDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.compose.ui.layout.column.ColumnPresentation
import com.bselzer.ktx.compose.ui.layout.description.DescriptionPresentation
import com.bselzer.ktx.compose.ui.layout.icontext.IconTextPresentation
import com.bselzer.ktx.compose.ui.layout.image.ImagePresentation
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.project.PresentationModel
import com.bselzer.ktx.compose.ui.layout.project.Presenter
import com.bselzer.ktx.compose.ui.layout.row.RowPresentation
import com.bselzer.ktx.compose.ui.layout.text.TextPresentation

data class ModalDrawerPresentation(
    /**
     * The [PresentationModel] for the container holding the header and sections.
     */
    val container: ColumnPresentation = ColumnPresentation.Default,

    /**
     * The [PresentationModel] for the image.
     */
    val image: ImagePresentation = ImagePresentation.Default,

    /**
     * The [PresentationModel] for the description.
     */
    val description: DescriptionPresentation = DescriptionPresentation.Default,

    /**
     * The [PresentationModel] for the icon/text components.
     */
    val component: IconTextPresentation = IconTextPresentation.Default,

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
) : Presenter<ModalDrawerPresentation>() {
    companion object {
        @Stable
        val Default = ModalDrawerPresentation()
    }

    override fun safeMerge(other: ModalDrawerPresentation) = ModalDrawerPresentation(
        container = container.merge(other.container),
        image = image.merge(other.image),
        description = description.merge(other.description),
        component = component.merge(other.component),
        shape = ComposeMerger.shape.safeMerge(shape, other.shape),
        elevation = ComposeMerger.dp.safeMerge(elevation, other.elevation),
        backgroundColor = ComposeMerger.color.safeMerge(backgroundColor, other.backgroundColor),
        contentColor = ComposeMerger.color.safeMerge(contentColor, other.contentColor),
        scrimColor = ComposeMerger.color.safeMerge(scrimColor, other.scrimColor)
    )

    @Composable
    override fun localized() = ModalDrawerPresentation(
        component = IconTextPresentation(
            container = RowPresentation(
                horizontalArrangement = Arrangement.spacedBy(32.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically
            ),
            text = TextPresentation(
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textStyle = MaterialTheme.typography.body2
            )
        ),
        shape = MaterialTheme.shapes.large,
        elevation = DrawerDefaults.Elevation,
        backgroundColor = MaterialTheme.colors.surface,
        scrimColor = DrawerDefaults.scrimColor
    ).merge(this).run {
        copy(
            container = container.localized(),
            image = image.localized(),
            description = description.localized(),
            component = component.localized(),
            contentColor = if (ComposeMerger.color.isDefault(contentColor)) contentColorFor(backgroundColor) else contentColor
        )
    }
}