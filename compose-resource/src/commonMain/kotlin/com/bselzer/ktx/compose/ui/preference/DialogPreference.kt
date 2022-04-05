package com.bselzer.ktx.compose.ui.preference

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.painter.Painter
import com.bselzer.ktx.compose.ui.description.DescriptionTitle
import com.bselzer.ktx.compose.ui.dialog.ConfirmationButton
import com.bselzer.ktx.compose.ui.dialog.DismissButton
import com.bselzer.ktx.compose.ui.dialog.MaterialAlertDialog
import com.bselzer.ktx.compose.ui.dialog.ResetButton



/**
 * Lays out a dialog with a [title] and a [subtitle] describing the preference.
 *
 * @param state the state of the preference
 * @param onStateChanged the block for setting the updated state
 * @param style the style describing how to lay out the preference
 * @param painter the icon representing the preference
 * @param title the name of the preference
 * @param subtitle the description of the preference
 * @param dialogTitle the name of the preference
 * @param dialogContent the block for displaying the main dialog content
 */
@Composable
fun <T> DialogPreference(
    state: MutableState<T?>,
    onStateChanged: (T?) -> Unit,
    style: DialogPreferenceStyle = LocalDialogPreferenceStyle.current,
    painter: Painter,
    title: String,
    subtitle: String,
    dialogTitle: String = title,
    dialogContent: @Composable () -> Unit,
) = DialogPreference(
    style = style.preferenceStyle,
    painter = painter,
    title = title,
    subtitle = subtitle,
    onStateChanged = onStateChanged,
) { setShowDialog, setState ->
    MaterialAlertDialog(
        style = style.dialogStyle,
        onDismissRequest = { setShowDialog(false) },
        title = { DescriptionTitle(title = dialogTitle, style = style.dialogTextStyle) },
        negativeButton = {
            DismissButton(style = style.buttonStyle, textStyle = style.buttonTextStyle) { setShowDialog(false) }
        },
        neutralButton = {
            ResetButton(style = style.buttonStyle, textStyle = style.buttonTextStyle) {
                setState(null)
                setShowDialog(false)
            }
        },
        positiveButton = {
            ConfirmationButton(style = style.buttonStyle, textStyle = style.buttonTextStyle) {
                setState(state.value)
                setShowDialog(false)
            }
        },
    ) {
        dialogContent()
    }
}