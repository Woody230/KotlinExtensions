package com.bselzer.ktx.compose.ui.preference

import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

/**
 * Lays out a selector representing a [Boolean] preference state.
 *
 * @param modifier the preference modifier
 * @param spacing the spacing between components
 * @param iconPainter the painter for displaying the icon image
 * @param iconSize the size of the icon image
 * @param iconScale how to scale the icon image content
 * @param title the name of the preference
 * @param titleStyle the style of the text for displaying the [title]
 * @param subtitle the description of the preference
 * @param subtitleStyle the style of the text for displaying the [subtitle]
 * @param checked whether the choice is checked
 * @param onStateChanged the block for setting the updated state
 * @param selector the block for laying out the binary choice selector
 */
@Composable
fun BinaryPreference(
    modifier: Modifier = Modifier,
    spacing: Dp = 25.dp,
    iconPainter: Painter,
    iconSize: DpSize = DpSize(48.dp, 48.dp),
    iconScale: ContentScale = ContentScale.FillBounds,
    title: String,
    titleStyle: TextStyle = MaterialTheme.typography.subtitle1,
    subtitle: String,
    subtitleStyle: TextStyle = MaterialTheme.typography.subtitle2,
    checked: Boolean,
    onStateChanged: (Boolean) -> Unit,
    selector: @Composable () -> Unit,
) = SimplePreference(
    modifier = modifier,
    spacing = spacing,
    iconPainter = iconPainter,
    iconSize = iconSize,
    iconScale = iconScale,
    title = title,
    titleStyle = titleStyle,
    subtitle = subtitle,
    subtitleStyle = subtitleStyle,
    onClick = { onStateChanged(!checked) },
    ending = selector
)

/**
 * Lays out a switch representing a [Boolean] preference state.
 *
 * @param modifier the preference modifier
 * @param spacing the spacing between components
 * @param iconPainter the painter for displaying the icon image
 * @param iconSize the size of the icon image
 * @param iconScale how to scale the icon image content
 * @param title the name of the preference
 * @param titleStyle the style of the text for displaying the [title]
 * @param subtitle the description of the preference
 * @param subtitleStyle the style of the text for displaying the [subtitle]
 * @param checked whether the choice is checked
 * @param onStateChanged the block for setting the updated state
 */
@Composable
fun SwitchPreference(
    modifier: Modifier = Modifier,
    spacing: Dp = 25.dp,
    iconPainter: Painter,
    iconSize: DpSize = DpSize(48.dp, 48.dp),
    iconScale: ContentScale = ContentScale.FillBounds,
    title: String,
    titleStyle: TextStyle = MaterialTheme.typography.subtitle1,
    subtitle: String,
    subtitleStyle: TextStyle = MaterialTheme.typography.subtitle2,
    checked: Boolean,
    onStateChanged: (Boolean) -> Unit,
) = BinaryPreference(
    modifier = modifier,
    spacing = spacing,
    iconPainter = iconPainter,
    iconSize = iconSize,
    iconScale = iconScale,
    title = title,
    titleStyle = titleStyle,
    subtitle = subtitle,
    subtitleStyle = subtitleStyle,
    checked = checked,
    onStateChanged = onStateChanged
) {
    Switch(
        checked = checked,
        onCheckedChange = { onStateChanged(it) },
    )
}

/**
 * Lays out a check box representing a [Boolean] preference state.
 *
 * @param modifier the preference modifier
 * @param spacing the spacing between components
 * @param iconPainter the painter for displaying the icon image
 * @param iconSize the size of the icon image
 * @param iconScale how to scale the icon image content
 * @param title the name of the preference
 * @param titleStyle the style of the text for displaying the [title]
 * @param subtitle the description of the preference
 * @param subtitleStyle the style of the text for displaying the [subtitle]
 * @param checked whether the choice is checked
 * @param onStateChanged the block for setting the updated state
 */
@Composable
fun CheckBoxPreference(
    modifier: Modifier = Modifier,
    spacing: Dp = 25.dp,
    iconPainter: Painter,
    iconSize: DpSize = DpSize(48.dp, 48.dp),
    iconScale: ContentScale = ContentScale.FillBounds,
    title: String,
    titleStyle: TextStyle = MaterialTheme.typography.subtitle1,
    subtitle: String,
    subtitleStyle: TextStyle = MaterialTheme.typography.subtitle2,
    checked: Boolean,
    onStateChanged: (Boolean) -> Unit,
) = BinaryPreference(
    modifier = modifier,
    spacing = spacing,
    iconPainter = iconPainter,
    iconSize = iconSize,
    iconScale = iconScale,
    title = title,
    titleStyle = titleStyle,
    subtitle = subtitle,
    subtitleStyle = subtitleStyle,
    checked = checked,
    onStateChanged = onStateChanged
) {
    Checkbox(
        checked = checked,
        onCheckedChange = { onStateChanged(it) },
    )
}