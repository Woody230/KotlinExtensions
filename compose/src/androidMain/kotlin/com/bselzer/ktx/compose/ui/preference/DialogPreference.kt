package com.bselzer.ktx.compose.ui.preference

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import com.bselzer.ktx.compose.ui.dialog.ConfirmationButton
import com.bselzer.ktx.compose.ui.dialog.DismissButton
import com.bselzer.ktx.compose.ui.dialog.MaterialAlertDialog
import com.bselzer.ktx.compose.ui.dialog.ResetButton

/**
 * Lays out a [dialog] with a [title] and a [subtitle] describing the preference.
 *
 * @param modifier the dialog modifier
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

/**
 * Lays out a [dialog] with a [title] and a [subtitle] describing the preference.
 *
 * @param modifier the dialog modifier
 * @param onStateChanged the block for setting the updated state
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
fun <T> DialogPreference(
    modifier: Modifier = Modifier,
    onStateChanged: (T?) -> Unit,
    spacing: Dp = 25.dp,
    iconPainter: Painter,
    iconSize: DpSize = DpSize(48.dp, 48.dp),
    iconScale: ContentScale = ContentScale.FillBounds,
    title: String,
    titleStyle: TextStyle = MaterialTheme.typography.subtitle1,
    subtitle: String,
    subtitleStyle: TextStyle = MaterialTheme.typography.subtitle2,
    dialog: @Composable ((Boolean) -> Unit, (T?) -> Unit) -> Unit
) = DialogPreference(
    modifier = modifier,
    spacing = spacing,
    iconPainter = iconPainter,
    iconSize = iconSize,
    iconScale = iconScale,
    title = title,
    titleStyle = titleStyle,
    subtitle = subtitle,
    subtitleStyle = subtitleStyle,
) { setShowDialog ->
    dialog(setShowDialog) { onStateChanged(it) }
}

/**
 * Lays out a dialog with a [title] and a [subtitle] describing the preference.
 *
 * @param modifier the preference modifier
 * @param dialogModifier the dialog modifier
 * @param state the state of the preference
 * @param onStateChanged the block for setting the updated state
 * @param spacing the spacing between components
 * @param iconPainter the painter for displaying the icon image
 * @param iconSize the size of the icon image
 * @param iconScale how to scale the icon image content
 * @param title the name of the preference
 * @param titleStyle the style of the text for displaying the [title]
 * @param subtitle the description of the preference
 * @param subtitleStyle the style of the text for displaying the [subtitle]
 * @param buttonStyle the style of the text for the dialog buttons
 * @param buttonColors the colors of the dialog buttons
 * @param dialogShape the dialog shape
 * @param dialogBackgroundColor the color of the dialog background
 * @param dialogContentColor the color of the dialog content
 * @param dialogProperties the dialog properties
 * @param dialogTitle the name of the preference
 * @param dialogTitleStyle the style of the text for displaying the [dialogTitle]
 * @param dialogContent the block for displaying the main dialog content
 */
@Composable
fun <T> DialogPreference(
    modifier: Modifier = Modifier,
    dialogModifier: Modifier = Modifier,
    state: MutableState<T?>,
    onStateChanged: (T?) -> Unit,
    spacing: Dp = 25.dp,
    iconPainter: Painter,
    iconSize: DpSize = DpSize(48.dp, 48.dp),
    iconScale: ContentScale = ContentScale.FillBounds,
    title: String,
    titleStyle: TextStyle = MaterialTheme.typography.subtitle1,
    subtitle: String,
    subtitleStyle: TextStyle = MaterialTheme.typography.subtitle2,
    buttonStyle: TextStyle = MaterialTheme.typography.button,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(),
    dialogShape: Shape = MaterialTheme.shapes.medium,
    dialogBackgroundColor: Color = MaterialTheme.colors.surface,
    dialogContentColor: Color = contentColorFor(dialogBackgroundColor),
    dialogProperties: DialogProperties = DialogProperties(),
    dialogTitle: String = title,
    dialogTitleStyle: TextStyle = titleStyle,
    dialogContent: @Composable () -> Unit,
) = DialogPreference(
    modifier = modifier,
    onStateChanged = onStateChanged,
    spacing = spacing,
    iconPainter = iconPainter,
    iconSize = iconSize,
    iconScale = iconScale,
    title = title,
    titleStyle = titleStyle,
    subtitle = subtitle,
    subtitleStyle = subtitleStyle,
) { setShowDialog, setState ->
    MaterialAlertDialog(
        modifier = dialogModifier,
        showDialog = setShowDialog,
        shape = dialogShape,
        backgroundColor = dialogBackgroundColor,
        contentColor = dialogContentColor,
        properties = dialogProperties,
        title = { PreferenceTitle(title = dialogTitle, style = dialogTitleStyle) },
        negativeButton = {
            DismissButton(textStyle = buttonStyle, colors = buttonColors) { setShowDialog(false) }
        },
        neutralButton = {
            ResetButton(textStyle = buttonStyle, colors = buttonColors) {
                setState(null)
                setShowDialog(false)
            }
        },
        positiveButton = {
            ConfirmationButton(textStyle = buttonStyle, colors = buttonColors) {
                setState(state.value)
                setShowDialog(false)
            }
        },
    ) {
        dialogContent()
    }
}