package com.bselzer.ktx.compose.ui.layout.alertdialog

import androidx.compose.runtime.Composable
import com.bselzer.ktx.compose.ui.layout.button.ButtonInteractor
import com.bselzer.ktx.compose.ui.layout.text.TextInteractor
import com.bselzer.ktx.compose.ui.layout.text.confirmationTextInteractor
import com.bselzer.ktx.compose.ui.layout.text.dismissTextInteractor
import com.bselzer.ktx.compose.ui.layout.text.resetTextInteractor
import com.bselzer.ktx.compose.ui.layout.textbutton.TextButtonInteractor

/**
 * Creates an [AlertDialogInteractor] with a dismissal negative button, optional neutral button, and a positive confirmation button.
 *
 * @param onDismissRequest Executes when the user tries to dismiss the Dialog by clicking outside or pressing the back button.
 * This is not called when the dismiss button is clicked.
 * Returns whether the dialog should be shown.
 *
 * @param onDismiss Executes when the user clicks the dismissal button.
 * Returns whether the dialog should be shown.
 *
 * @param neutralText The [TextInteractor] for a neutral button.
 * If null, then the neutral button is not displayed.
 *
 * @param onNeutral Executes when the user clicks the neutral button.
 * Returns whether the dialog should be shown.
 *
 * @param onConfirmation Executes when the user clicks the positive button.
 * Returns whether the dialog should be shown.
 *
 * @param setShowDialog Executes when the user tries to dismiss the dialog or clicks a button.
 * The result of the associated callback is passed in, in order to change whether the dialog is shown.
 */
@Composable
fun alertDialogButtonsInteractor(
    onDismissRequest: () -> Boolean = { false },
    onDismiss: () -> Boolean = { false },
    neutralText: TextInteractor? = null,
    onNeutral: (() -> Boolean) = { false },
    onConfirmation: () -> Boolean = { false },
    setShowDialog: (Boolean) -> Unit,
) = AlertDialogInteractor(
    negativeButton = TextButtonInteractor(
        text = dismissTextInteractor(),
        button = ButtonInteractor {
            setShowDialog(onDismiss())
        },
    ),
    neutralButton = neutralText?.let {
        TextButtonInteractor(
            text = neutralText,
            button = ButtonInteractor {
                setShowDialog(onNeutral())
            },
        )
    },
    positiveButton = TextButtonInteractor(
        text = confirmationTextInteractor(),
        button = ButtonInteractor {
            setShowDialog(onConfirmation())
        }
    ),
    onDismissRequest = {
        setShowDialog(onDismissRequest())
    }
)

/**
 * Creates an [AlertDialogInteractor] with a dismissal negative button, reset neutral button, and a positive confirmation button.
 *
 * @param onDismissRequest Executes when the user tries to dismiss the Dialog by clicking outside or pressing the back button.
 * This is not called when the dismiss button is clicked.
 * Returns whether the dialog should be shown.
 *
 * @param onDismiss Executes when the user clicks the dismissal button.
 * Returns whether the dialog should be shown.
 *
 * @param onReset Executes when the user clicks the reset button.
 * Returns whether the dialog should be shown.
 *
 * @param onConfirmation Executes when the user clicks the positive button.
 * Returns whether the dialog should be shown.
 *
 * @param setShowDialog Executes when the user tries to dismiss the dialog or clicks a button.
 * The result of the associated callback is passed in, in order to change whether the dialog is shown.
 */
@Composable
fun resetAlertDialogInteractor(
    onDismissRequest: () -> Boolean = { false },
    onDismiss: () -> Boolean = { false },
    onReset: () -> Boolean = { false },
    onConfirmation: () -> Boolean = { false },
    setShowDialog: (Boolean) -> Unit,
) = alertDialogButtonsInteractor(
    onDismissRequest = onDismissRequest,
    onDismiss = onDismiss,
    neutralText = resetTextInteractor(),
    onNeutral = onReset,
    onConfirmation = onConfirmation,
    setShowDialog = setShowDialog
)