package com.bselzer.ktx.compose.ui.description

import androidx.compose.runtime.Composable
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
    style = LocalDescriptionStyle.current.titleStyle.merge(style)
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
    style = LocalDescriptionStyle.current.subtitleStyle.merge(style)
)