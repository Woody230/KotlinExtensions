package com.bselzer.library.kotlin.extension.compose.ui.container

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Wraps a [Column] with the [prepend] block, the [contents] separated by the [divider], and then the [append] block.
 *
 * @param modifier the [Column] modifier
 * @param verticalArrangement the vertical arrangement of the content
 * @param horizontalAlignment the horizontal alignment of the content
 * @param prepend the start block before the main [contents] is displayed
 * @param append the end block after the main [contents] is displayed
 * @param divider the divider between the [contents] for a given index, except for between the column and the first and last items which should use the [prepend] and [append] blocks respectively instead
 * @param contents the main content
 */
@Composable
fun DividedColumn(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    prepend: @Composable ColumnScope.() -> Unit = { },
    append: @Composable ColumnScope.() -> Unit = { },
    divider: @Composable ColumnScope.(Int) -> Unit,
    vararg contents: @Composable ColumnScope.() -> Unit
) = Column(modifier = modifier, verticalArrangement = verticalArrangement, horizontalAlignment = horizontalAlignment) {
    prepend()

    contents.forEachIndexed { index, content ->
        content()

        if (index != contents.size - 1) {
            divider(index)
        }
    }

    append()
}

/**
 * Wraps a [Row] with the [prepend] block, the [contents] separated by the [divider], and then the [append] block.
 *
 * @param modifier the [Row] modifier
 * @param horizontalArrangement the horizontal arrangement of the content
 * @param verticalAlignment the vertical alignment of the content
 * @param prepend the start block before the main [contents] is displayed
 * @param append the end block after the main [contents] is displayed
 * @param divider the divider between the [contents] for a given index, except for between the column and the first and last items which should use the [prepend] and [append] blocks respectively instead
 * @param contents the main content
 */
@Composable
fun DividedRow(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    prepend: @Composable RowScope.() -> Unit = { },
    append: @Composable RowScope.() -> Unit = { },
    divider: @Composable RowScope.(Int) -> Unit,
    vararg contents: @Composable RowScope.() -> Unit
) = Row(modifier = modifier, horizontalArrangement = horizontalArrangement, verticalAlignment = verticalAlignment) {
    prepend()

    contents.forEachIndexed { index, content ->
        content()

        if (index != contents.size - 1) {
            divider(index)
        }
    }

    append()
}