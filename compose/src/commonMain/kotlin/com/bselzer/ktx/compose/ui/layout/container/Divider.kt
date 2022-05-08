package com.bselzer.ktx.compose.ui.layout.container

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import com.bselzer.ktx.compose.ui.style.*

/**
 * Wraps a [Column] with the [prepend] block, the [contents] separated by the [divider], and then the [append] block.
 *
 * @param style the style describing how to lay out the column
 * @param prepend the start block before the main [contents] is displayed
 * @param append the end block after the main [contents] is displayed
 * @param divider the divider between the [contents] for a given index, except for between the column and the first and last items which should use the [prepend] and [append] blocks respectively instead
 * @param contents the main content
 */
@Composable
fun DividedColumn(
    style: ColumnStyle = LocalColumnStyle.current,
    prepend: @Composable ColumnScope.() -> Unit,
    append: @Composable ColumnScope.() -> Unit,
    divider: @Composable ColumnScope.(Int) -> Unit,
    vararg contents: @Composable ColumnScope.() -> Unit
) = Column(style = style) {
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
 * Wraps a [Column] with the [divider] if [prepend] is true, the [contents] separated by the [divider], and then the [divider] if [append] is true.
 *
 * @param style the style describing how to lay out the column
 * @param prepend whether to lay out the [divider] before the main [contents] is displayed
 * @param append whether to lay out the [divider] after the main [contents] is displayed
 * @param divider the divider between the [contents], except for between the column and the first and last items which should be indicated by [prepend] and [append] respectively instead
 * @param contents the main content
 */
@Composable
fun DividedColumn(
    style: ColumnStyle = LocalColumnStyle.current,
    prepend: Boolean = false,
    append: Boolean = false,
    divider: @Composable ColumnScope.() -> Unit,
    vararg contents: @Composable ColumnScope.() -> Unit
) = DividedColumn(
    style = style,
    prepend = { if (prepend) divider() },
    append = { if (append) divider() },
    divider = { divider() },
    contents = contents
)

/**
 * Wraps a [Row] with the [prepend] block, the [contents] separated by the [divider], and then the [append] block.
 *
 * @param style the style describing how to lay out the row
 * @param prepend the start block before the main [contents] is displayed
 * @param append the end block after the main [contents] is displayed
 * @param divider the divider between the [contents] for a given index, except for between the column and the first and last items which should use the [prepend] and [append] blocks respectively instead
 * @param contents the main content
 */
@Composable
fun DividedRow(
    style: RowStyle = LocalRowStyle.current,
    prepend: @Composable RowScope.() -> Unit,
    append: @Composable RowScope.() -> Unit,
    divider: @Composable RowScope.(Int) -> Unit,
    vararg contents: @Composable RowScope.() -> Unit
) = Row(style = style) {
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
 * Wraps a [Row] with the [divider] if [prepend] is true, the [contents] separated by the [divider], and then the [divider] if [append] is true.
 *
 * @param style the style describing how to lay out the row
 * @param prepend whether to lay out the [divider] before the main [contents] is displayed
 * @param append whether to lay out the [divider] after the main [contents] is displayed
 * @param divider the divider between the [contents], except for between the column and the first and last items which should be indicated by [prepend] and [append] respectively instead
 * @param contents the main content
 */
@Composable
fun DividedRow(
    style: RowStyle = LocalRowStyle.current,
    prepend: Boolean = false,
    append: Boolean = false,
    divider: @Composable RowScope.() -> Unit,
    vararg contents: @Composable RowScope.() -> Unit
) = DividedRow(
    style = style,
    prepend = { if (prepend) divider() },
    append = { if (append) divider() },
    divider = { divider() },
    contents = contents
)