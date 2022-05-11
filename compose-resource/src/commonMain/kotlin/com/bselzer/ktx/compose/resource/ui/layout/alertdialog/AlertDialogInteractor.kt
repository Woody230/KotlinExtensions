package com.bselzer.ktx.compose.resource.ui.layout.alertdialog

import androidx.compose.runtime.Composable
import com.bselzer.ktx.compose.resource.ui.layout.text.confirmationTextInteractor
import com.bselzer.ktx.compose.resource.ui.layout.text.dismissTextInteractor
import com.bselzer.ktx.compose.resource.ui.layout.text.resetTextInteractor
import com.bselzer.ktx.compose.ui.layout.alertdialog.alertDialogButtonsInteractor
import com.bselzer.ktx.compose.ui.layout.text.TextInteractor

/**
 * Creates an [AlertDialogInteractor] with an optional negative button, optional neutral button, and a positive confirmation button.
 *
 * @param onDismissRequest Executes when the user tries to dismiss the Dialog by clicking outside or pressing the back button.
 * This is not called when the dismiss button is clicked.
 * Returns whether the dialog should be shown.
 *
 * @param negativeText The [TextInteractor] for a negative button.
 * If null, then the negative button is not displayed.
 *
 * @param onNegative Executes when the user clicks the negative button.
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
fun confirmationAlertDialogInteractor(
    onDismissRequest: () -> Boolean = { false },
    negativeText: TextInteractor? = null,
    onNegative: () -> Boolean = { false },
    neutralText: TextInteractor? = null,
    onNeutral: (() -> Boolean) = { false },
    onConfirmation: () -> Boolean = { false },
    setShowDialog: (Boolean) -> Unit,
) = alertDialogButtonsInteractor(
    onDismissRequest = onDismissRequest,
    negativeText = negativeText,
    onNegative = onNegative,
    neutralText = neutralText,
    onNeutral = onNeutral,
    positiveText = confirmationTextInteractor(),
    onPositive = onConfirmation,
    setShowDialog = setShowDialog
)

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
fun dismissAlertDialogInteractor(
    onDismissRequest: () -> Boolean = { false },
    onDismiss: () -> Boolean = { false },
    neutralText: TextInteractor? = null,
    onNeutral: (() -> Boolean) = { false },
    onConfirmation: () -> Boolean = { false },
    setShowDialog: (Boolean) -> Unit,
) = confirmationAlertDialogInteractor(
    onDismissRequest = onDismissRequest,
    negativeText = dismissTextInteractor(),
    onNegative = onDismiss,
    neutralText = neutralText,
    onNeutral = onNeutral,
    onConfirmation = onConfirmation,
    setShowDialog = setShowDialog
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
) = dismissAlertDialogInteractor(
    onDismissRequest = onDismissRequest,
    onDismiss = onDismiss,
    neutralText = resetTextInteractor(),
    onNeutral = onReset,
    onConfirmation = onConfirmation,
    setShowDialog = setShowDialog
)