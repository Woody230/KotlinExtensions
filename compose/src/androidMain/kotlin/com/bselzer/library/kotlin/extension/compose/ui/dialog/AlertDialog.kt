package com.bselzer.library.kotlin.extension.compose.ui.dialog

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

/* TODO common dialog
*   https://github.com/JetBrains/compose-jb/issues/758
*   https://issuetracker.google.com/issues/194625542
*   https://github.com/atsushieno/compose-mpp/
*/

/**
 * Lays out an alert dialog.
 *
 * @param showDialog the block for setting whether the dialog should be shown
 * @param onDismissRequest the block for when the user tries to dismiss the dialog by clicking outside or pressing the back button
 * @param shape the dialog shape
 * @param backgroundColor the color of the dialog background
 * @param contentColor the color of the dialog content
 * @param properties the properties
 * @param title the title describing the [content]
 * @param negativeButton the negative action button
 * @param neutralButton the neutral action button
 * @param positiveButton the positive action button
 * @param content the main content of the dialog
 */
@Composable
fun MaterialAlertDialog(
    showDialog: (Boolean) -> Unit,
    onDismissRequest: () -> Unit = { showDialog(false) },
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    properties: DialogProperties = DialogProperties(),
    title: @Composable () -> Unit = {},
    negativeButton: (@Composable () -> Unit)? = null,
    neutralButton: (@Composable () -> Unit)? = null,
    positiveButton: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) = AlertDialog(
    onDismissRequest = onDismissRequest,
    title = title,
    shape = shape,
    backgroundColor = backgroundColor,
    contentColor = contentColor,
    properties = properties,
    text = content,
    buttons = {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {
            val (neutral, spacer, core) = createRefs()

            Row(modifier = Modifier.constrainAs(neutral) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(spacer.start)
            }) {
                neutralButton?.invoke()
            }

            Spacer(modifier = Modifier.constrainAs(spacer) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(neutral.end)
                end.linkTo(core.start)
                width = Dimension.fillToConstraints
            })

            Row(modifier = Modifier.constrainAs(core) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(spacer.end)
                end.linkTo(parent.end)
            }) {
                negativeButton?.invoke()
                positiveButton?.invoke()
            }
        }
    }
)

/**
 * Lays out a button for dismissing a dialog.
 *
 * @param textStyle the style of the text on this button
 * @param onClick the on-click handler
 */
@Composable
fun DismissButton(textStyle: TextStyle = MaterialTheme.typography.button, onClick: () -> Unit) =
    DialogButton(text = stringResource(id = android.R.string.cancel), textStyle = textStyle, onClick = onClick)

/**
 * Lays out a button for confirmation a selection.
 *
 * @param textStyle the style of the text on this button
 * @param onClick the on-click handler
 */
@Composable
fun ConfirmationButton(textStyle: TextStyle = MaterialTheme.typography.button, onClick: () -> Unit) =
    DialogButton(text = stringResource(id = android.R.string.ok), textStyle = textStyle, onClick = onClick)

/**
 * Lays out a button for deleting a selection.
 *
 * @param textStyle the style of the text on this button
 * @param onClick the on-click handler
 */
@Composable
fun DeleteButton(textStyle: TextStyle = MaterialTheme.typography.button, onClick: () -> Unit) =
    DialogButton(text = "Delete", textStyle = textStyle, onClick = onClick)

/**
 * Lays out a button for a dialog.
 */
@Composable
fun DialogButton(text: String, textStyle: TextStyle = MaterialTheme.typography.button, onClick: () -> Unit) = Button(onClick = onClick) {
    Text(text = text, style = textStyle)
}