package com.bselzer.ktx.compose.ui.layout.alertdialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.toUpperCase
import com.bselzer.ktx.compose.ui.layout.button.ButtonInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.project.Interactor
import com.bselzer.ktx.compose.ui.layout.text.TextInteractor
import com.bselzer.ktx.compose.ui.layout.textbutton.TextButtonInteractor

data class AlertDialogInteractor(
    override val modifier: InteractableModifier = InteractableModifier,

    /**
     * The [Interactor] for the negative action button for dismissal.
     */
    val negativeButton: TextButtonInteractor? = null,

    /**
     * The [Interactor] for the neutral action button for an alternative action.
     */
    val neutralButton: TextButtonInteractor? = null,

    /**
     * The [Interactor] for the positive action button for confirmation.
     */
    val positiveButton: TextButtonInteractor? = null,

    /**
     * The [Interactor] for the title.
     */
    val title: TextInteractor? = null,

    /**
     * The state of the dialog indicating whether it should be opened.
     */
    val state: DialogState = DialogState.OPENED,

    /**
     * The block for when the user tries to dismiss the dialog by clicking outside or pressing the back button
     */
    val onDismissRequest: () -> Unit,
) : Interactor(modifier)

/**
 * Creates an [AlertDialogInteractor] with optional negative/neutral/positive buttons.
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
 * @param positiveText The [TextInteractor] for a positive button.
 * If null, then the positive button is not displayed.
 *
 * @param onPositive Executes when the user clicks the positive button.
 * Returns whether the dialog should be shown.
 *
 * @param closeDialog Executes when the user tries to dismiss the dialog or clicks a button and the associated callback indicates that the dialog should be closed.
 */
@Composable
fun alertDialogButtonsInteractor(
    onDismissRequest: () -> DialogState = { DialogState.CLOSED },
    negativeText: TextInteractor? = null,
    onNegative: () -> DialogState = { DialogState.CLOSED },
    neutralText: TextInteractor? = null,
    onNeutral: () -> DialogState = { DialogState.CLOSED },
    positiveText: TextInteractor? = null,
    onPositive: () -> DialogState = { DialogState.CLOSED },
    closeDialog: () -> Unit,
): AlertDialogInteractor {
    fun (() -> DialogState).perform() {
        if (invoke() == DialogState.CLOSED) {
            closeDialog()
        }
    }

    return AlertDialogInteractor(
        negativeButton = negativeText?.let {
            TextButtonInteractor(
                text = negativeText.copy(text = negativeText.text.toUpperCase()),
                button = ButtonInteractor { onNegative.perform() },
            )
        },
        neutralButton = neutralText?.let {
            TextButtonInteractor(
                text = neutralText.copy(text = neutralText.text.toUpperCase()),
                button = ButtonInteractor { onNeutral.perform() },
            )
        },
        positiveButton = positiveText?.let {
            TextButtonInteractor(
                text = positiveText.copy(text = positiveText.text.toUpperCase()),
                button = ButtonInteractor { onPositive.perform() }
            )
        },
        onDismissRequest = { onDismissRequest.perform() }
    )
}

/**
 * The state of a dialog indicating whether the dialog should remain opened.
 */
enum class DialogState {
    OPENED,
    CLOSED;

    fun toBoolean() = this == OPENED
}

/**
 * @return [DialogState.OPENED] if the [value] is true, otherwise [DialogState.CLOSED]
 */
fun DialogState(value: Boolean?) = when (value) {
    true -> DialogState.OPENED
    else -> DialogState.CLOSED
}