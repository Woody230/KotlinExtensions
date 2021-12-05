package com.bselzer.library.kotlin.extension.compose.ui.preference

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.bselzer.library.kotlin.extension.compose.ui.container.DividedRow

/**
 * Lays out a section of preferences with a [title].
 *
 * @param modifier the [Column] modifier
 * @param spacing the spacing between components
 * @param iconPainter the painter for displaying the icon image
 * @param iconSize the size of the icon image
 * @param iconScale how to scale the icon image content
 * @param title the name of the preference section
 * @param titleStyle the style of the text for displaying the [title]
 */
@Composable
fun PreferenceSection(
    modifier: Modifier = Modifier,
    spacing: Dp = 25.dp,
    iconPainter: Painter,
    iconSize: DpSize = DpSize(48.dp, 48.dp),
    iconScale: ContentScale = ContentScale.FillBounds,
    title: String,
    titleStyle: TextStyle = MaterialTheme.typography.subtitle2,
    titleColor: Color = MaterialTheme.colors.primary
) = Column(
    modifier = Modifier
        .fillMaxWidth()
        .then(modifier)
) {
    DividedRow(
        modifier = Modifier.fillMaxWidth(),
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
}