package com.bselzer.ktx.compose.ui.description

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.bselzer.ktx.compose.ui.style.Text
import com.bselzer.ktx.compose.ui.style.WordStyle

/**
 * CompositionLocal containing the preferred TitleStyle that will be used by DescriptionTitle components by default.
 */
val LocalTitleStyle: ProvidableCompositionLocal<WordStyle> = compositionLocalOf {
    WordStyle.Default.copy(
        fontWeight = FontWeight.Bold,
        overflow = TextOverflow.Visible,
    )
}

/**
 * CompositionLocal containing the preferred SubtitleStyle that will be used by DescriptionSubtitle components by default.
 */
val LocalSubtitleStyle: ProvidableCompositionLocal<WordStyle> = compositionLocalOf {
    WordStyle.Default.copy(
        overflow = TextOverflow.Ellipsis,
    )
}

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
    titleStyle: WordStyle = LocalTitleStyle.current,
    subtitle: String,
    subtitleStyle: WordStyle = LocalSubtitleStyle.current
) = Column(
    modifier = modifier
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
    style: WordStyle = LocalTitleStyle.current
) = Text(
    text = title,
    style = WordStyle(
        textStyle = MaterialTheme.typography.subtitle1
    ).merge(style)
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
    style: WordStyle = LocalSubtitleStyle.current
) = Text(
    text = subtitle,
    style = WordStyle(
        textStyle = MaterialTheme.typography.subtitle2
    ).merge(style)
)