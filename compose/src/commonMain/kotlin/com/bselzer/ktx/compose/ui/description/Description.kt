package com.bselzer.ktx.compose.ui.description

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow

/**
 * Lays out a description of a value through a [title] and [subtitle].
 *
 * @param modifier the description modifier
 * @param title the description of a value
 * @param titleStyle the style of the text for displaying the [title]
 * @param subtitle the value
 * @param subtitleStyle the style of the text for displaying the [subtitle]
 */
@Composable
fun Description(
    modifier: Modifier = Modifier,
    title: String,
    titleStyle: TextStyle = MaterialTheme.typography.subtitle1,
    subtitle: String,
    subtitleStyle: TextStyle = MaterialTheme.typography.subtitle2
) = Column(
    modifier = modifier
) {
    DescriptionTitle(title = title, style = titleStyle)
    DescriptionSubtitle(subtitle = subtitle, style = subtitleStyle)
}

/**
 * Lays out a description title.
 *
 * @param modifier the [Text] modifier
 * @param title the description of a value
 * @param style the style of the text for displaying the [title]
 */
@Composable
fun DescriptionTitle(modifier: Modifier = Modifier, title: String, style: TextStyle = MaterialTheme.typography.subtitle1, maxLines: Int = Int.MAX_VALUE) =
    Text(modifier = modifier, text = title, fontWeight = FontWeight.Bold, maxLines = maxLines, overflow = TextOverflow.Visible, style = style)

/**
 * Lays out a description subtitle.
 *
 * @param modifier the [Text] modifier
 * @param subtitle the value
 * @param style the style of the text for displaying the [subtitle]
 */
@Composable
fun DescriptionSubtitle(modifier: Modifier = Modifier, subtitle: String, style: TextStyle = MaterialTheme.typography.subtitle2, maxLines: Int = Int.MAX_VALUE) =
    Text(modifier = modifier, text = subtitle, maxLines = maxLines, overflow = TextOverflow.Ellipsis, style = style)