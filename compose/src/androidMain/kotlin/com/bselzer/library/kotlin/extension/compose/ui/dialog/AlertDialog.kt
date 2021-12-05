package com.bselzer.library.kotlin.extension.compose.ui.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 8.dp, top = 8.dp, bottom = 8.dp) // Matching the padding in the specs. https://material.io/components/dialogs#specs
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
                negativeButton?.let { negativeButton ->
                    negativeButton()
                    Spacer(modifier = Modifier.width(8.dp))
                }

                positiveButton?.invoke()
            }
        }
    }
)

/**
 * Lays out a button for dismissing a dialog.
 *
 * @param modifier the [Button] modifier
 * @param textStyle the style of the text
 * @param colors the [Button] colors
 * @param onClick the on-click handler
 */
@Composable
fun DismissButton(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.button,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    onClick: () -> Unit
) = MaterialDialogButton(modifier = modifier, text = stringResource(id = android.R.string.cancel), textStyle = textStyle, colors = colors, onClick = onClick)

/**
 * Lays out a button for confirmation a selection.
 *
 * @param modifier the [Button] modifier
 * @param textStyle the style of the text
 * @param colors the [Button] colors
 * @param onClick the on-click handler
 */
@Composable
fun ConfirmationButton(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.button,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    onClick: () -> Unit
) = MaterialDialogButton(modifier = modifier, text = stringResource(id = android.R.string.ok), textStyle = textStyle, colors = colors, onClick = onClick)

/**
 * Lays out a button for deleting a selection.
 *
 * @param modifier the [Button] modifier
 * @param textStyle the style of the text
 * @param colors the [Button] colors
 * @param onClick the on-click handler
 */
@Composable
fun DeleteButton(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.button,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    onClick: () -> Unit
) = MaterialDialogButton(modifier = modifier, text = "Delete", textStyle = textStyle, colors = colors, onClick = onClick)

/**
 * Lays out a button for a dialog.
 *
 * @param modifier the [Button] modifier
 * @param text the text
 * @param textStyle the style of the [text]
 * @param colors the [Button] colors
 * @param onClick the on-click handler
 */
@Composable
fun MaterialDialogButton(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.button,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    onClick: () -> Unit
) = Button(
    modifier = modifier,
    colors = colors,
    onClick = onClick
) {
    Text(text = text, style = textStyle)
}