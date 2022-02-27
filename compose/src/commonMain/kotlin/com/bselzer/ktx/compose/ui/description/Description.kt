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
    style: ColumnStyle = LocalColumnStyle.localized(),
    title: String,
    titleStyle: WordStyle = LocalWordStyle.localized(),
    subtitle: String,
    subtitleStyle: WordStyle = LocalWordStyle.localized()
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
    style: WordStyle = LocalWordStyle.localized()
) = Text(
    text = title,
    style = LocalDescriptionStyle.localized().titleStyle.merge(style)
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
    style: WordStyle = LocalWordStyle.localized()
) = Text(
    text = subtitle,
    style = LocalDescriptionStyle.localized().subtitleStyle.merge(style)
)