package com.bselzer.ktx.compose.ui.description

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.bselzer.ktx.compose.ui.style.Text
import com.bselzer.ktx.compose.ui.style.WordStyle

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
    titleStyle: WordStyle = WordStyle(),
    subtitle: String,
    subtitleStyle: WordStyle = WordStyle()
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
fun DescriptionTitle(title: String, style: WordStyle = WordStyle(), onTextLayout: (TextLayoutResult) -> Unit = {}) {
    val default = WordStyle(
        fontWeight = FontWeight.Bold,
        overflow = TextOverflow.Visible,
        textStyle = MaterialTheme.typography.subtitle1
    )
    Text(text = title, style = default.merge(style), onTextLayout = onTextLayout)
}


/**
 * Lays out a description subtitle.
 *
 * @param subtitle the value
 * @param style the style of the text for displaying the [subtitle]
 */
@Composable
fun DescriptionSubtitle(subtitle: String, style: WordStyle = WordStyle(), onTextLayout: (TextLayoutResult) -> Unit = {}) {
    val default = WordStyle(
        overflow = TextOverflow.Ellipsis,
        textStyle = MaterialTheme.typography.subtitle2
    )
    Text(text = subtitle, style = default.merge(style), onTextLayout = onTextLayout)
}