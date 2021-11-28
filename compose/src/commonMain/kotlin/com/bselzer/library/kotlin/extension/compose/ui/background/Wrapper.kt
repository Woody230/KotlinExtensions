package com.bselzer.library.kotlin.extension.compose.ui.background

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Wraps the [background] in a [Box] behind the [content].
 *
 * @param modifier the box modifier
 * @param contentAlignment the box alignment
 * @param background the block for displaying the background
 * @param content the block for displaying the content overlaying the background
 */
@Composable
fun Background(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    background: @Composable BoxScope.() -> Unit,
    content: @Composable BoxScope.() -> Unit
) = Box(modifier = modifier, contentAlignment = contentAlignment) {
    background()
    content()
}

/**
 * Wraps the [background] in a [Box] and displays the [content] in a [Row].
 *
 * @param modifier the row modifier
 * @param alignment the alignment of the [background] and [Row]
 * @param background the block for displaying the background
 * @param contentHorizontalArrangement the horizontal arrangement of the [content]
 * @param contentVerticalAlignment the vertical alignment of the [content]
 * @param content the block for displaying the content overlaying the background
 */
@Composable
fun BackgroundRow(
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.TopStart,
    background: @Composable BoxScope.() -> Unit,
    contentHorizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    contentVerticalAlignment: Alignment.Vertical = Alignment.Top,
    content: @Composable RowScope.() -> Unit
) = Background(modifier = modifier, contentAlignment = alignment, background = background) {
    Row(modifier = Modifier.wrapContentSize(), horizontalArrangement = contentHorizontalArrangement, verticalAlignment = contentVerticalAlignment, content = content)
}

/**
 * Wraps the [background] in a [Box] and displays the [content] in a [Column].
 *
 * @param modifier the column modifier
 * @param alignment the alignment of the [background] and [Column]
 * @param background the block for displaying the background
 * @param contentVerticalArrangement the vertical arrangement of the [content]
 * @param contentHorizontalAlignment the horizontal alignment of the [content]
 * @param content the block for displaying the content overlaying the background
 */
@Composable
fun BackgroundColumn(
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.TopStart,
    background: @Composable BoxScope.() -> Unit,
    contentVerticalArrangement: Arrangement.Vertical = Arrangement.Top,
    contentHorizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable ColumnScope.() -> Unit
) = Background(modifier = modifier, contentAlignment = alignment, background = background) {
    Column(modifier = Modifier.wrapContentSize(), verticalArrangement = contentVerticalArrangement, horizontalAlignment = contentHorizontalAlignment, content = content)
}