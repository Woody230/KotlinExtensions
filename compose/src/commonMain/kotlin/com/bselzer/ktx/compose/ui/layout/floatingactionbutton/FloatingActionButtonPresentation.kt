package com.bselzer.ktx.compose.ui.layout.floatingactionbutton

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.FloatingActionButtonElevation
import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.bselzer.ktx.compose.ui.layout.icon.IconPresentation
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.project.PresentationModel
import com.bselzer.ktx.compose.ui.layout.project.Presenter
import com.bselzer.ktx.compose.ui.layout.text.TextPresentation

data class FloatingActionButtonPresentation(
    /**
     * The [PresentationModel] of the text.
     */
    val text: TextPresentation = TextPresentation.Default,

    /**
     * The [PresentationModel] of the icon.
     */
    val icon: IconPresentation = IconPresentation.Default,

    /**
     * The Shape of this FAB
     */
    val shape: Shape = ComposeMerger.shape.default,

    /**
     * The background color. Use Color.Transparent to have no color
     */
    val backgroundColor: Color = ComposeMerger.color.default,

    /**
     * The preferred content color for content inside this FAB
     */
    val contentColor: Color = ComposeMerger.color.default,

    /**
     *  FloatingActionButtonElevation used to resolve the elevation for this FAB in different states.
     *  This controls the size of the shadow below the FAB.
     *  content - the content of this FAB - this is typically an Icon.
     */
    val elevation: FloatingActionButtonElevation = ComposeMerger.floatingActionButtonElevation.default
) : Presenter<FloatingActionButtonPresentation>() {
    companion object {
        @Stable
        val Default = FloatingActionButtonPresentation()
    }

    override fun safeMerge(other: FloatingActionButtonPresentation) = FloatingActionButtonPresentation(
        text = text.merge(other.text),
        icon = icon.merge(other.icon),
        shape = ComposeMerger.shape.safeMerge(shape, other.shape),
        backgroundColor = ComposeMerger.color.safeMerge(backgroundColor, other.backgroundColor),
        contentColor = ComposeMerger.color.safeMerge(contentColor, other.contentColor),
        elevation = ComposeMerger.floatingActionButtonElevation.safeMerge(elevation, other.elevation)
    )

    @Composable
    override fun localized() = FloatingActionButtonPresentation(
        shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
        backgroundColor = MaterialTheme.colors.secondary,
        elevation = FloatingActionButtonDefaults.elevation()
    ).merge(this).run {
        copy(
            text = text.localized(),
            icon = icon.localized(),
            contentColor = if (ComposeMerger.color.isDefault(contentColor)) contentColorFor(backgroundColor) else contentColor
        )
    }
}