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
 * @param background the block for displaying the background
 * @param horizontalArrangement the horizontal arrangement of the [content]
 * @param verticalAlignment the vertical alignment of the [content]
 * @param content the block for displaying the content overlaying the background
 */
@Composable
fun BackgroundRow(
    modifier: Modifier = Modifier,
    background: @Composable BoxScope.() -> Unit,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    content: @Composable RowScope.() -> Unit
) = Background(background = background) {
    Row(modifier = modifier, horizontalArrangement = horizontalArrangement, verticalAlignment = verticalAlignment, content)
}

/**
 * Wraps the [background] in a [Box] and displays the [content] in a [Column].
 *
 * @param modifier the column modifier
 * @param background the block for displaying the background
 * @param verticalArrangement the vertical arrangement of the [content]
 * @param horizontalAlignment the horizontal alignment of the [content]
 * @param content the block for displaying the content overlaying the background
 */
@Composable
fun BackgroundColumn(
    modifier: Modifier = Modifier,
    background: @Composable BoxScope.() -> Unit,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable ColumnScope.() -> Unit
) = Background(background = background) {
    Column(modifier = modifier, verticalArrangement = verticalArrangement, horizontalAlignment = horizontalAlignment, content)
}