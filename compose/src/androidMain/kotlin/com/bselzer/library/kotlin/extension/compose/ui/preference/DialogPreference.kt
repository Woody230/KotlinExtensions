package com.bselzer.library.kotlin.extension.compose.ui.preference

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

/**
 * Lays out a [dialog] representing with a [title] and a [subtitle] describing the preference.
 *
 * @param modifier the [ConstraintLayout] modifier
 * @param spacing the spacing between components
 * @param iconPainter the painter for displaying the icon image
 * @param iconSize the size of the icon image
 * @param iconScale how to scale the icon image content
 * @param title the name of the preference
 * @param titleStyle the style of the text for displaying the [title]
 * @param subtitle the description of the preference
 * @param subtitleStyle the style of the text for displaying the [subtitle]
 * @param dialog the block for displaying the dialog with blocks for setting whether to show the dialog and for passing the new state
 */
@Composable
fun DialogPreference(
    modifier: Modifier = Modifier,
    spacing: Dp = 25.dp,
    iconPainter: Painter,
    iconSize: DpSize = DpSize(48.dp, 48.dp),
    iconScale: ContentScale = ContentScale.FillBounds,
    title: String,
    titleStyle: TextStyle = MaterialTheme.typography.subtitle1,
    subtitle: String,
    subtitleStyle: TextStyle = MaterialTheme.typography.subtitle2,
    dialog: @Composable ((Boolean) -> Unit) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showDialog = true }
            .then(modifier)
    ) {
        val (icon, description) = createRefs()
        PreferenceIcon(
            ref = icon,
            contentDescription = title,
            painter = iconPainter,
            contentScale = iconScale,
            contentSize = iconSize,
        )

        PreferenceDescription(
            ref = description,
            startRef = icon,
            spacing = spacing,
            title = title,
            titleStyle = titleStyle,
            subtitle = subtitle,
            subtitleStyle = subtitleStyle
        )

        if (showDialog) {
            dialog { showDialog = it }
        }
    }
}