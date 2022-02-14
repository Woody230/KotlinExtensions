package com.bselzer.ktx.compose.ui.preference

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.painter.Painter
import com.bselzer.ktx.compose.ui.description.DescriptionTitle
import com.bselzer.ktx.compose.ui.dialog.*
import com.bselzer.ktx.compose.ui.style.ButtonStyle
import com.bselzer.ktx.compose.ui.style.LocalButtonStyle
import com.bselzer.ktx.compose.ui.style.LocalWordStyle
import com.bselzer.ktx.compose.ui.style.WordStyle


/**
 * Lays out a dialog with a [title] and a [subtitle] describing the preference.
 *
 * @param state the state of the preference
 * @param onStateChanged the block for setting the updated state
 * @param style the style describing how to lay out the preference
 * @param title the name of the preference
 * @param subtitle the description of the preference
 * @param buttonStyle the style of the text for the dialog buttons
 * @param dialogTitle the name of the preference
 * @param dialogTitleStyle the style of the text for displaying the [dialogTitle]
 * @param dialogContent the block for displaying the main dialog content
 */
@Composable
fun <T> DialogPreference(
    state: MutableState<T?>,
    onStateChanged: (T?) -> Unit,
    style: SimplePreferenceStyle = LocalSimplePreferenceStyle.current,
    painter: Painter,
    title: String,
    subtitle: String,
    buttonStyle: ButtonStyle = LocalButtonStyle.current,
    buttonTextStyle: WordStyle = LocalWordStyle.current,
    dialogStyle: AlertDialogStyle = LocalAlertDialogStyle.current,
    dialogTitle: String = title,
    dialogTitleStyle: WordStyle = LocalWordStyle.current,
    dialogContent: @Composable () -> Unit,
) = DialogPreference(
    style = style,
    painter = painter,
    title = title,
    subtitle = subtitle,
    onStateChanged = onStateChanged,
) { setShowDialog, setState ->
    // TODO standardization and style for dialog with title/buttons
    MaterialAlertDialog(
        style = dialogStyle,
        onDismissRequest = { setShowDialog(false) },
        title = { DescriptionTitle(title = dialogTitle, style = dialogTitleStyle) },
        negativeButton = {
            DismissButton { setShowDialog(false) }
        },
        neutralButton = {
            ResetButton(style = buttonStyle, textStyle = buttonTextStyle) {
                setState(null)
                setShowDialog(false)
            }
        },
        positiveButton = {
            ConfirmationButton(style = buttonStyle, textStyle = buttonTextStyle) {
                setState(state.value)
                setShowDialog(false)
            }
        },
    ) {
        dialogContent()
    }
}