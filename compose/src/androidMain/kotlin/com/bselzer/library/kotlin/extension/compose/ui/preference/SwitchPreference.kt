package com.bselzer.library.kotlin.extension.compose.ui.preference

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

/**
 * Lays out a switch representing a [Boolean] preference state.
 *
 * @param modifier the [ConstraintLayout] modifier
 * @param state the preference state
 * @param onStateChanged the block for setting the updated state
 * @param spacing the spacing between components
 * @param iconPainter the painter for displaying the icon image
 * @param iconSize the size of the icon image
 * @param iconScale how to scale the icon image content
 * @param title the name of the preference
 * @param titleStyle the style of the text for displaying the [title]
 * @param subtitle the description of the preference
 * @param subtitleStyle the style of the text for displaying the [subtitle]
 */
@Composable
fun SwitchPreference(
    modifier: Modifier = Modifier,
    state: MutableState<Boolean>,
    onStateChanged: (Boolean) -> Unit = { state.value = it },
    spacing: Dp = 25.dp,
    iconPainter: Painter,
    iconSize: DpSize = DpSize(48.dp, 48.dp),
    iconScale: ContentScale = ContentScale.FillBounds,
    title: String,
    titleStyle: TextStyle = MaterialTheme.typography.subtitle1,
    subtitle: String,
    subtitleStyle: TextStyle = MaterialTheme.typography.subtitle2
) = ConstraintLayout(
    modifier = Modifier
        .fillMaxWidth()
        .then(modifier)
) {
    val (icon, description, switch) = createRefs()
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
        endRef = switch,
        spacing = spacing,
        title = title,
        titleStyle = titleStyle,
        subtitle = subtitle,
        subtitleStyle = subtitleStyle
    )

    Switch(
        checked = state.value,
        onCheckedChange = onStateChanged,
        modifier = Modifier.constrainAs(switch) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            end.linkTo(parent.end)
        }
    )
}