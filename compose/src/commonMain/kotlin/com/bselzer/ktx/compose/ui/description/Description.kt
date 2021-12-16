package com.bselzer.ktx.compose.ui.description

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

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
fun DescriptionTitle(modifier: Modifier = Modifier, title: String, style: TextStyle = MaterialTheme.typography.subtitle1) =
    Text(modifier = modifier, text = title, fontWeight = FontWeight.Bold, style = style)

/**
 * Lays out a description subtitle.
 *
 * @param modifier the [Text] modifier
 * @param subtitle the value
 * @param style the style of the text for displaying the [subtitle]
 */
@Composable
fun DescriptionSubtitle(modifier: Modifier = Modifier, subtitle: String, style: TextStyle = MaterialTheme.typography.subtitle2) =
    Text(modifier = modifier, text = subtitle, style = style)