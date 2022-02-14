package com.bselzer.ktx.compose.ui.description

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.bselzer.ktx.compose.ui.style.*

/**
 * Lays out a description of a value through a [title] and [subtitle].
 *
 * @param style the style describing how to lay out the column
 * @param title the description of a value
 * @param titleStyle the style of the text for displaying the [title]
 * @param subtitle the value
 * @param subtitleStyle the style of the text for displaying the [subtitle]
 */
@Composable
fun Description(
    style: ColumnStyle = LocalColumnStyle.current,
    title: String,
    titleStyle: WordStyle = LocalWordStyle.current,
    subtitle: String,
    subtitleStyle: WordStyle = LocalWordStyle.current
) = Column(
    style = style
) {
    DescriptionTitle(title = title, style = titleStyle)
    DescriptionSubtitle(subtitle = subtitle, style = subtitleStyle)
}

/**
 * The default [WordStyle] for a [DescriptionTitle].
 */
@Composable
fun descriptionTitleStyle(): WordStyle = WordStyle.Default.copy(
    fontWeight = FontWeight.Bold,
    overflow = TextOverflow.Visible,
    textStyle = MaterialTheme.typography.subtitle1
)

/**
 * The default [WordStyle] for a [DescriptionSubtitle].
 */
@Composable
fun descriptionSubtitleStyle(): WordStyle = WordStyle.Default.copy(
    overflow = TextOverflow.Ellipsis,
    textStyle = MaterialTheme.typography.subtitle2
)

/**
 * Lays out a description title.
 *
 * @param title the description of a value
 * @param style the style of the text for displaying the [title]
 */
@Composable
fun DescriptionTitle(
    title: String,
    style: WordStyle = LocalWordStyle.current
) = Text(
    text = title,
    style = descriptionTitleStyle().merge(style)
)

/**
 * Lays out a description subtitle.
 *
 * @param subtitle the value
 * @param style the style of the text for displaying the [subtitle]
 */
@Composable
fun DescriptionSubtitle(
    subtitle: String,
    style: WordStyle = LocalWordStyle.current
) = Text(
    text = subtitle,
    style = descriptionSubtitleStyle().merge(style)
)