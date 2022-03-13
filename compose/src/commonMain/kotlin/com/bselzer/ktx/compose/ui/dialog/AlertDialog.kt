package com.bselzer.ktx.compose.ui.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.bselzer.ktx.compose.ui.container.Spacer
import com.bselzer.ktx.compose.ui.style.*

/**
 * Lays out an alert dialog.
 *
 * @param onDismissRequest the block for when the user tries to dismiss the dialog by clicking outside or pressing the back button
 * @param style how to display the dialog
 * @param title the title describing the [content]
 * @param negativeButton the negative action button layout for dismissal
 * @param neutralButton the neutral action button layout for an alternative action
 * @param positiveButton the positive action button layout for confirmation
 * @param content the main content of the dialog
 */
@Composable
fun MaterialAlertDialog(
    onDismissRequest: () -> Unit,
    style: AlertDialogStyle = LocalAlertDialogStyle.localized(),
    title: (@Composable () -> Unit)? = null,
    negativeButton: (@Composable () -> Unit)? = null,
    neutralButton: (@Composable () -> Unit)? = null,
    positiveButton: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) = MaterialAlertDialog(
    onDismissRequest = onDismissRequest,
    style = style,
    title = title,
    content = content,
    buttons = { MaterialAlertDialogButtons(negativeButton = negativeButton, neutralButton = neutralButton, positiveButton = positiveButton) }
)

/**
 * Lays out an alert dialog.
 *
 * @param onDismissRequest the block for when the user tries to dismiss the dialog by clicking outside or pressing the back button
 * @param style how to display the dialog
 * @param title the title describing the [content]
 * @param buttons the supplementary action buttons
 * @param content the main content of the dialog
 */
@Composable
fun MaterialAlertDialog(
    onDismissRequest: () -> Unit,
    style: AlertDialogStyle = LocalAlertDialogStyle.localized(),
    title: (@Composable () -> Unit)? = null,
    buttons: (@Composable () -> Unit)?,
    content: @Composable () -> Unit,
) = Dialog(
    onDismissRequest = onDismissRequest,
    properties = style.properties
) {
    Surface(
        modifier = style.modifier,
        shape = style.shape,
        color = style.backgroundColor,
        contentColor = style.contentColor,
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
            Spacer(width = 8.dp)
        }

        positiveButton?.invoke()
    }
}

/**
 * Lays out a button for a dialog.
 *
 * @param text the text
 * @param textStyle the style of the [text]
 * @param style the style of the [Button]
 * @param onClick the on-click handler
 */
@Composable
fun MaterialDialogButton(
    text: String,
    textStyle: WordStyle = LocalWordStyle.localized(),
    style: ButtonStyle = LocalButtonStyle.current.localized(type = ButtonStyleType.TextButton),
    onClick: () -> Unit
) = Button(
    style = style,
    onClick = onClick
) {
    Text(text = text, style = WordStyle(textStyle = MaterialTheme.typography.button).merge(textStyle))
}