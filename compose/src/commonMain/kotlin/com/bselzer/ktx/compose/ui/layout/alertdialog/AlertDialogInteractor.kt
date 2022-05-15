package com.bselzer.ktx.compose.ui.layout.alertdialog

import androidx.compose.runtime.Composable
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
 * @param setShowDialog Executes when the user tries to dismiss the dialog or clicks a button.
 * The result of the associated callback is passed in, in order to change whether the dialog is shown.
 */
@Composable
fun alertDialogButtonsInteractor(
    onDismissRequest: () -> Boolean = { false },
    negativeText: TextInteractor? = null,
    onNegative: () -> Boolean = { false },
    neutralText: TextInteractor? = null,
    onNeutral: (() -> Boolean) = { false },
    positiveText: TextInteractor? = null,
    onPositive: () -> Boolean = { false },
    setShowDialog: (Boolean) -> Unit,
) = AlertDialogInteractor(
    negativeButton = negativeText?.let {
        TextButtonInteractor(
            text = negativeText,
            button = ButtonInteractor {
                setShowDialog(onNegative())
            },
        )
    },
    neutralButton = neutralText?.let {
        TextButtonInteractor(
            text = neutralText,
            button = ButtonInteractor {
                setShowDialog(onNeutral())
            },
        )
    },
    positiveButton = positiveText?.let {
        TextButtonInteractor(
            text = positiveText,
            button = ButtonInteractor {
                setShowDialog(onPositive())
            }
        )
    },
    onDismissRequest = {
        setShowDialog(onDismissRequest())
    }
)