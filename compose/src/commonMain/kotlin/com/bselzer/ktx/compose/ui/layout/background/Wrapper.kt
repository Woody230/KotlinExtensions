package com.bselzer.ktx.compose.ui.layout.background

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.style.*

/**
 * Wraps the [background] in a [Box] behind the [content].
 *
 * @param style the style describing how to lay out the Box
 * @param background the block for displaying the background
 * @param content the block for displaying the content overlaying the background
 */
@Composable
fun Background(
    style: BoxStyle = LocalBoxStyle.current,
    background: @Composable BoxScope.() -> Unit,
    content: @Composable BoxScope.() -> Unit
) = Box(style = style) {
    background()
    content()
}

/**
 * Wraps the [background] in a [Box] and displays the [content] in a [Row].
 *
 * @param style the style describing how to lay out the row
 * @param backgroundStyle the style describing how to lay out the background
 * @param background the block for displaying the background
 * @param content the block for displaying the content overlaying the background
 */
@Composable
fun BackgroundRow(
    style: RowStyle = LocalRowStyle.current,
    backgroundStyle: BoxStyle = LocalBoxStyle.current,
    background: @Composable BoxScope.() -> Unit,
    content: @Composable RowScope.() -> Unit
) = Background(style = backgroundStyle, background = background) {
    Row(
        style = style prioritize Modifier.wrapContentSize(),
        content = content
    )
}

/**
 * Wraps the [background] in a [Box] and displays the [content] in a [Column].
 *
 * @param style the style describing how to lay out the column
 * @param backgroundStyle the style describing how to lay out the background
 * @param background the block for displaying the background
 * @param content the block for displaying the content overlaying the background
 */
@Composable
fun BackgroundColumn(
    style: ColumnStyle = LocalColumnStyle.current,
    backgroundStyle: BoxStyle = LocalBoxStyle.current,
    background: @Composable BoxScope.() -> Unit,
    content: @Composable ColumnScope.() -> Unit
) = Background(style = backgroundStyle, background = background) {
    Column(
        style = style prioritize Modifier.wrapContentSize(),
        content = content
    )
}