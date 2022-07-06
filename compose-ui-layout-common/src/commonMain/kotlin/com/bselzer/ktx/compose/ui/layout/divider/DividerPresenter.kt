package com.bselzer.ktx.compose.ui.layout.divider

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.compose.ui.layout.LayoutOrientation
import com.bselzer.ktx.compose.ui.layout.LocalLayoutOrientation
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.PresentableModifier
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class DividerPresenter(
    override val modifier: PresentableModifier = PresentableModifier,

    /**
     * Color of the divider line.
     */
    val color: Color = ComposeMerger.color.default,

    /**
     * Thickness of the divider line, 1 dp is used by default. Using Dp.Hairline will produce a single pixel divider regardless of screen density.
     */
    val thickness: Dp = ComposeMerger.dp.default,

    /**
     * Start offset of this line, no offset by default.
     */
    val startIndent: Dp = ComposeMerger.dp.default,

    /**
     * The direction to lay out the divider.
     */
    val orientation: LayoutOrientation = ComposeMerger.layoutOrientation.default
) : Presenter<DividerPresenter>(modifier) {
    companion object {
        @Stable
        val Default = DividerPresenter()
    }

    override fun safeMerge(other: DividerPresenter) = DividerPresenter(
        modifier = modifier.merge(other.modifier),
        color = ComposeMerger.color.safeMerge(color, other.color),
        thickness = ComposeMerger.dp.safeMerge(thickness, other.thickness),
        startIndent = ComposeMerger.dp.safeMerge(startIndent, other.startIndent),
        orientation = ComposeMerger.layoutOrientation.safeMerge(orientation, other.orientation)
    )

    @Composable
    override fun localized() = DividerPresenter(
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f),
        thickness = 1.dp,
        startIndent = 0.dp,
        orientation = when (LocalLayoutOrientation.current) {
            // Layout in the opposite direction to divide items inside columns/rows.
            LayoutOrientation.HORIZONTAL -> LayoutOrientation.VERTICAL
            LayoutOrientation.VERTICAL -> LayoutOrientation.HORIZONTAL

            // By default, a divider will always orient horizontally.
            // Consequently, this is why the layout orientation has been added.
            else -> LayoutOrientation.HORIZONTAL
        }
    ).merge(this)
}