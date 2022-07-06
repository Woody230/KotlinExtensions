package com.bselzer.ktx.compose.ui.layout.preference.section

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.bselzer.ktx.compose.ui.layout.column.ColumnPresenter
import com.bselzer.ktx.compose.ui.layout.column.ColumnProjector
import com.bselzer.ktx.compose.ui.layout.column.spacedColumnProjector
import com.bselzer.ktx.compose.ui.layout.divider.DividerPresenter
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.ModularPadding
import com.bselzer.ktx.compose.ui.layout.preference.PreferenceConstants

/**
 * Creates a [ColumnProjector] for projecting preferences and preference sections with a divider with the given [color].
 */
@Composable
fun preferenceColumnProjector(
    color: Color = DividerPresenter.Default.color
): ColumnProjector {
    // Divide the components by the spacing split across the divider thickness and then evenly across the remaining space.
    val thickness = PreferenceConstants.Thickness
    val segment = (PreferenceConstants.Spacing - thickness) / 2
    return spacedColumnProjector(
        color = color,
        thickness = thickness,
        presenter = ColumnPresenter(
            divider = DividerPresenter(
                modifier = ModularPadding(vertical = segment)
            )
        )
    )
}

/**
 * Creates a [ColumnProjector] for projecting preferences and preference sections with a transparent divider.
 */
@Composable
fun spacedPreferenceColumnProjector(): ColumnProjector = preferenceColumnProjector(color = Color.Transparent)