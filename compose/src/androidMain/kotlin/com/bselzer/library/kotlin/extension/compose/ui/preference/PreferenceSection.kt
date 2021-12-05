package com.bselzer.library.kotlin.extension.compose.ui.preference

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.bselzer.library.kotlin.extension.compose.ui.container.DividedColumn
import com.bselzer.library.kotlin.extension.compose.ui.container.DividedRow

/**
 * Lays out a [title] for a section of preferences.
 *
 * @param modifier the [Column] modifier
 * @param spacing the spacing between components
 * @param iconPainter the painter for displaying the icon image
 * @param iconSize the size of the icon image
 * @param iconScale how to scale the icon image content
 * @param title the name of the preference section
 * @param titleStyle the style of the text for displaying the [title]
 * @param titleColor the color of the [title]
 */
@Composable
fun PreferenceSectionTitle(
    modifier: Modifier = Modifier,
    spacing: Dp = 25.dp,
    iconPainter: Painter,
    iconSize: DpSize = DpSize(48.dp, 48.dp),
    iconScale: ContentScale = ContentScale.FillBounds,
    title: String,
    titleStyle: TextStyle = MaterialTheme.typography.subtitle1,
    titleColor: Color = MaterialTheme.colors.primary,
) = DividedRow(
    modifier = Modifier
        .fillMaxWidth()
        .then(modifier),
    verticalAlignment = Alignment.CenterVertically,
    divider = { Spacer(modifier = Modifier.width(spacing)) },
    contents = arrayOf(
        {
            PreferenceIcon(
                painter = iconPainter,
                contentDescription = title,
                contentSize = iconSize,
                contentScale = iconScale
            )
        },
        {
            PreferenceTitle(title = title, style = titleStyle.copy(color = titleColor))
        }
    )
)

/**
 * Lays out a section of preferences with a [title].
 *
 * @param modifier the [Column] modifier
 * @param iconPainter the painter for displaying the icon image
 * @param iconSize the size of the icon image
 * @param iconScale how to scale the icon image content
 * @param title the name of the preference section
 * @param titleStyle the style of the text for displaying the [title]
 * @param titleColor the color of the [title]
 * @param titleSpacing the spacing within the components of the [title]
 * @param spacing the spacing between the [title] and the [content]
 */
@Composable
fun PreferenceSection(
    modifier: Modifier = Modifier,
    iconPainter: Painter,
    iconSize: DpSize = DpSize(48.dp, 48.dp),
    iconScale: ContentScale = ContentScale.FillBounds,
    title: String,
    titleStyle: TextStyle = MaterialTheme.typography.subtitle1,
    titleColor: Color = MaterialTheme.colors.primary,
    titleSpacing: Dp = 25.dp,
    spacing: Dp = 10.dp,
    content: @Composable ColumnScope.() -> Unit
) = DividedColumn(
    modifier = modifier,
    divider = { Spacer(modifier = Modifier.height(spacing)) },
    contents = arrayOf(
        {
            PreferenceSectionTitle(
                spacing = titleSpacing,
                iconPainter = iconPainter,
                iconSize = iconSize,
                iconScale = iconScale,
                title = title,
                titleStyle = titleStyle,
                titleColor = titleColor
            )
        },
        {
            content()
        }
    )
)

/**
 * Lays out a [title] for a section of preferences.
 *
 * @param modifier the [Column] modifier
 * @param spacing the spacing between components
 * @param title the name of the preference section
 * @param titleStyle the style of the text for displaying the [title]
 * @param titleColor the color of the [title]
 */
@Composable
fun PreferenceSectionTitle(
    modifier: Modifier = Modifier,
    spacing: Dp = 73.dp, // Default spacing + default image size
    title: String,
    titleStyle: TextStyle = MaterialTheme.typography.subtitle1,
    titleColor: Color = MaterialTheme.colors.primary,
) = PreferenceTitle(
    title = title,
    style = titleStyle.copy(color = titleColor),
    modifier = Modifier
        .padding(start = spacing)
        .then(modifier),
)

/**
 * Lays out a section of preferences with a [title].
 *
 * @param modifier the [Column] modifier
 * @param title the name of the preference section
 * @param titleStyle the style of the text for displaying the [title]
 * @param titleColor the color of the [title]
 * @param titleSpacing the spacing within the components of the [title]
 * @param spacing the spacing between the [title] and the [content]
 */
@Composable
fun PreferenceSection(
    modifier: Modifier = Modifier,
    title: String,
    titleStyle: TextStyle = MaterialTheme.typography.subtitle1,
    titleColor: Color = MaterialTheme.colors.primary,
    titleSpacing: Dp = 73.dp,
    spacing: Dp = 10.dp,
    content: @Composable ColumnScope.() -> Unit
) = DividedColumn(
    modifier = modifier,
    divider = { Spacer(modifier = Modifier.height(spacing)) },
    contents = arrayOf(
        {
            PreferenceSectionTitle(
                spacing = titleSpacing,
                title = title,
                titleStyle = titleStyle,
                titleColor = titleColor
            )
        },
        {
            content()
        }
    )
)