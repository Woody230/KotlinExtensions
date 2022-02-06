package com.bselzer.ktx.compose.ui.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
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
 * @param modifier the dialog modifier
 * @param showDialog the block for setting whether the dialog should be shown
 * @param onDismissRequest the block for when the user tries to dismiss the dialog by clicking outside or pressing the back button
 * @param shape the dialog shape
 * @param backgroundColor the color of the dialog background
 * @param contentColor the color of the dialog content
 * @param properties the properties
 * @param title the title describing the [content]
 * @param negativeButton the negative action button layout for dismissal
 * @param neutralButton the neutral action button layout for an alternative action
 * @param positiveButton the positive action button layout for confirmation
 * @param content the main content of the dialog
 */
@Composable
fun MaterialAlertDialog(
    modifier: Modifier = Modifier,
    showDialog: (Boolean) -> Unit,
    onDismissRequest: () -> Unit = { showDialog(false) },
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    properties: DialogProperties = DialogProperties(),
    title: (@Composable () -> Unit)? = null,
    negativeButton: (@Composable () -> Unit)? = null,
    neutralButton: (@Composable () -> Unit)? = null,
    positiveButton: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) = MaterialAlertDialog(
    modifier = modifier,
    showDialog = showDialog,
    onDismissRequest = onDismissRequest,
    shape = shape,
    backgroundColor = backgroundColor,
    contentColor = contentColor,
    properties = properties,
    title = title,
    content = content,
    buttons = { MaterialAlertDialogButtons(negativeButton = negativeButton, neutralButton = neutralButton, positiveButton = positiveButton) }
)

/**
 * Lays out an alert dialog.
 *
 * @param modifier the dialog modifier
 * @param showDialog the block for setting whether the dialog should be shown
 * @param onDismissRequest the block for when the user tries to dismiss the dialog by clicking outside or pressing the back button
 * @param shape the dialog shape
 * @param backgroundColor the color of the dialog background
 * @param contentColor the color of the dialog content
 * @param properties the properties
 * @param title the title describing the [content]
 * @param buttons the supplementary action buttons
 * @param content the main content of the dialog
 */
@Composable
fun MaterialAlertDialog(
    modifier: Modifier = Modifier,
    showDialog: (Boolean) -> Unit,
    onDismissRequest: () -> Unit = { showDialog(false) },
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    properties: DialogProperties = DialogProperties(),
    title: (@Composable () -> Unit)? = null,
    buttons: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) = Dialog(
    onDismissRequest = onDismissRequest,
    properties = properties
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = backgroundColor,
        contentColor = contentColor,
        border = null,
        elevation = 0.dp
    ) {
        ConstraintLayout(
            // TODO can't properly wrap content without title/buttons going out of view unless the size is defined (through the filling)
            modifier = Modifier.fillMaxHeight()
        ) {
            val (titleBox, contentBox, buttonBox) = createRefs()

            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .defaultMinSize(minHeight = if (title == null) 0.dp else 64.dp)
                    .constrainAs(titleBox) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start, 24.dp)
                        end.linkTo(parent.end, 8.dp)
                        width = Dimension.fillToConstraints
                    }
            ) {
                title?.invoke()
            }

            Box(
                modifier = Modifier.constrainAs(contentBox) {
                    top.linkTo(titleBox.bottom)
                    bottom.linkTo(buttonBox.top)
                    start.linkTo(parent.start, 24.dp)
                    end.linkTo(parent.end, 8.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.preferredWrapContent
                }
            ) {
                content()
            }

            Box(
                modifier = Modifier.constrainAs(buttonBox) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
            ) {
                buttons?.invoke()
            }
        }
    }
}

/**
 * Lays out the alert dialog buttons according to the design specifications.
 *
 * @param negativeButton the negative action button layout for dismissal
 * @param neutralButton the neutral action button layout for an alternative action
 * @param positiveButton the positive action button layout for confirmation
 * @see <a href="https://material.io/components/dialogs#specs">material.io</a>
 */
@Composable
fun MaterialAlertDialogButtons(
    negativeButton: (@Composable () -> Unit)? = null,
    neutralButton: (@Composable () -> Unit)? = null,
    positiveButton: (@Composable () -> Unit)? = null,
) = ConstraintLayout(
    modifier = Modifier
        .fillMaxWidth()
        .padding(start = 24.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
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

/**
 * Lays out a button for dismissing a dialog.
 *
 * @param modifier the [Button] modifier
 * @param textStyle the style of the text
 * @param colors the [Button] colors
 * @param enabled whether the button is clickable
 * @param onClick the on-click handler
 */
@Composable
fun DismissButton(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.button,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    enabled: Boolean = true,
    onClick: () -> Unit
) = MaterialDialogButton(
    modifier = modifier,
    text = stringResource(id = android.R.string.cancel),
    textStyle = textStyle,
    colors = colors,
    enabled = enabled,
    onClick = onClick
)

/**
 * Lays out a button for confirmation a selection.
 *
 * @param modifier the [Button] modifier
 * @param textStyle the style of the text
 * @param colors the [Button] colors
 * @param enabled whether the button is clickable
 * @param onClick the on-click handler
 */
@Composable
fun ConfirmationButton(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.button,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    enabled: Boolean = true,
    onClick: () -> Unit
) = MaterialDialogButton(modifier = modifier, text = stringResource(id = android.R.string.ok), textStyle = textStyle, colors = colors, enabled = enabled, onClick = onClick)

/**
 * Lays out a button for deleting a selection.
 *
 * @param modifier the [Button] modifier
 * @param textStyle the style of the text
 * @param colors the [Button] colors
 * @param enabled whether the button is clickable
 * @param onClick the on-click handler
 */
@Composable
fun ResetButton(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.button,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    enabled: Boolean = true,
    onClick: () -> Unit
) = MaterialDialogButton(modifier = modifier, text = "Reset", textStyle = textStyle, colors = colors, enabled = enabled, onClick = onClick)

/**
 * Lays out a button for a dialog.
 *
 * @param modifier the [Button] modifier
 * @param text the text
 * @param textStyle the style of the [text]
 * @param colors the [Button] colors
 * @param enabled whether the button is clickable
 * @param onClick the on-click handler
 */
@Composable
fun MaterialDialogButton(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.button,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    enabled: Boolean = true,
    onClick: () -> Unit
) = Button(
    modifier = modifier,
    colors = colors,
    onClick = onClick,
    enabled = enabled
) {
    Text(text = text, style = textStyle)
}