package com.bselzer.ktx.compose.ui.preference

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.compose.ui.container.DividedColumn

/**
 * Lays out a column for preferences.
 *
 * @param modifier the column modifier
 * @param spacing the spacing between components
 * @param dividerThickness the thickness of the divider between [contents]
 * @param verticalArrangement the vertical arrangement of the content
 * @param horizontalAlignment the horizontal alignment of the content
 * @param prepend the start block before the main [contents] is displayed
 * @param append the end block after the main [contents] is displayed
 * @param contents the main content
 */
@Composable
fun PreferenceColumn(
    modifier: Modifier = Modifier,
    spacing: Dp = 25.dp,
    dividerThickness: Dp = 5.dp,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    prepend: @Composable ColumnScope.() -> Unit = { },
    append: @Composable ColumnScope.() -> Unit = { },
    vararg contents: @Composable ColumnScope.() -> Unit
) = DividedColumn(
    modifier = modifier,
    divider = {
        // Divide the components by the spacing split across the divider thickness and then evenly across the remaining space.
        val segment = (spacing - dividerThickness) / 2
        Spacer(modifier = Modifier.height(segment))
        Divider(thickness = dividerThickness)
        Spacer(modifier = Modifier.height(segment))
    },
    verticalArrangement = verticalArrangement,
    horizontalAlignment = horizontalAlignment,
    prepend = prepend,
    append = append,
    contents = contents
)