package com.bselzer.ktx.compose.ui.preference

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.painter.Painter


/**
 * Lays out a [dialog] with a [title] and a [subtitle] describing the preference.
 *
 * @param style the style describing how to lay out the preference
 * @param painter the painter for displaying the image
 * @param title the name of the preference
 * @param subtitle the description of the preference
 * @param dialog the block for displaying the dialog with blocks for setting whether to show the dialog and for passing the new state
 */
@Composable
fun DialogPreference(
    style: SimplePreferenceStyle = LocalSimplePreferenceStyle.current,
    painter: Painter,
    title: String,
    subtitle: String,
    dialog: @Composable ((Boolean) -> Unit) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    if (showDialog) {
        dialog { showDialog = it }
    }

    SimplePreference(
        style = style,
        painter = painter,
        title = title,
        subtitle = subtitle,
        onClick = { showDialog = true },
        ending = null
    )
}

/**
 * Lays out a [dialog] with a [title] and a [subtitle] describing the preference.
 *
 * @param style the style describing how to lay out the preference
 * @param painter the painter for displaying the image
 * @param title the name of the preference
 * @param subtitle the description of the preference
 * @param onStateChanged the block for setting the updated state
 * @param dialog the block for displaying the dialog with blocks for setting whether to show the dialog and for passing the new state
 */
@Composable
fun <T> DialogPreference(
    style: SimplePreferenceStyle = LocalSimplePreferenceStyle.current,
    painter: Painter,
    title: String,
    subtitle: String,
    onStateChanged: (T?) -> Unit,
    dialog: @Composable ((Boolean) -> Unit, (T?) -> Unit) -> Unit
) = DialogPreference(
    style = style,
    painter = painter,
    title = title,
    subtitle = subtitle,
) { setShowDialog ->
    dialog(setShowDialog) { onStateChanged(it) }
}