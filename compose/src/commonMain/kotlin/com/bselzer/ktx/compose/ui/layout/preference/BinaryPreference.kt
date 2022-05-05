package com.bselzer.ktx.compose.ui.layout.preference

import androidx.compose.material.Checkbox
import androidx.compose.material.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter


/**
 * Lays out a selector representing a [Boolean] preference state.
 *
 * @param style the style describing how to lay out the preference
 * @param painter the painter for displaying the image
 * @param title the name of the preference
 * @param subtitle the description of the preference
 * @param checked whether the choice is checked
 * @param onStateChanged the block for setting the updated state
 * @param selector the block for laying out the binary choice selector
 */
@Composable
fun BinaryPreference(
    style: SimplePreferenceStyle = LocalSimplePreferenceStyle.current,
    painter: Painter,
    title: String,
    subtitle: String,
    checked: Boolean,
    onStateChanged: (Boolean) -> Unit,
    selector: @Composable () -> Unit,
) = SimplePreference(
    style = style,
    painter = painter,
    title = title,
    subtitle = subtitle,
    onClick = { onStateChanged(!checked) },
    ending = selector
)

/**
 * Lays out a switch representing a [Boolean] preference state.
 *
 * @param style the style describing how to lay out the preference
 * @param painter the painter for displaying the image
 * @param title the name of the preference
 * @param subtitle the description of the preference
 * @param checked whether the choice is checked
 * @param onStateChanged the block for setting the updated state
 */
@Composable
fun SwitchPreference(
    style: SimplePreferenceStyle = LocalSimplePreferenceStyle.current,
    painter: Painter,
    title: String,
    subtitle: String,
    checked: Boolean,
    onStateChanged: (Boolean) -> Unit,
) = BinaryPreference(
    style = style,
    painter = painter,
    title = title,
    subtitle = subtitle,
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
 * @param style the style describing how to lay out the preference
 * @param painter the painter for displaying the image
 * @param title the name of the preference
 * @param subtitle the description of the preference
 * @param checked whether the choice is checked
 * @param onStateChanged the block for setting the updated state
 */
@Composable
fun CheckboxPreference(
    style: SimplePreferenceStyle = LocalSimplePreferenceStyle.current,
    painter: Painter,
    title: String,
    subtitle: String,
    checked: Boolean,
    onStateChanged: (Boolean) -> Unit,
) = BinaryPreference(
    style = style,
    painter = painter,
    title = title,
    subtitle = subtitle,
    checked = checked,
    onStateChanged = onStateChanged
) {
    Checkbox(
        checked = checked,
        onCheckedChange = { onStateChanged(it) },
    )
}