package com.bselzer.ktx.compose.ui.layout.preference

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import com.bselzer.ktx.compose.ui.layout.container.DividedColumn
import com.bselzer.ktx.compose.ui.style.*

/**
 * Lays out a column for preferences.
 *
 * @param style the style describing how to lay out the column
 * @param spacing the spacing between components
 * @param dividerStyle the style describing how to lay out the divider
 * @param prepend the start block before the main [contents] is displayed
 * @param append the end block after the main [contents] is displayed
 * @param contents the main content
 */
@Composable
fun PreferenceColumn(
    style: ColumnStyle = LocalColumnStyle.current,
    spacing: Dp = PreferenceSpacing,
    dividerStyle: DividerStyle = LocalDividerStyle.current,
    prepend: @Composable ColumnScope.() -> Unit = { },
    append: @Composable ColumnScope.() -> Unit = { },
    vararg contents: @Composable ColumnScope.() -> Unit
) = DividedColumn(
    style = style,
    divider = {
        val merged = DividerStyle(thickness = PreferenceThickness).with(dividerStyle)

        // Divide the components by the spacing split across the divider thickness and then evenly across the remaining space.
        val segment = (spacing - merged.thickness) / 2
        Spacer(height = segment)
        Divider(style = merged)
        Spacer(height = segment)
    },
    prepend = prepend,
    append = append,
    contents = contents
)