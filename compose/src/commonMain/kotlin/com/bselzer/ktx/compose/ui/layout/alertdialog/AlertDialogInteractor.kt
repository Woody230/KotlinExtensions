package com.bselzer.ktx.compose.ui.layout.alertdialog

import androidx.compose.ui.text.toUpperCase
import com.bselzer.ktx.compose.ui.layout.button.ButtonInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.project.Interactor
import com.bselzer.ktx.compose.ui.layout.text.TextInteractor
import com.bselzer.ktx.compose.ui.layout.text.textInteractor
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
) : Interactor(modifier) {
    data class Builder(
        /**
         * Executes when the user tries to dismiss the Dialog by clicking outside or pressing the back button.
         * This is not called when the dismiss button is clicked.
         * Returns whether the dialog should be shown.
         */
        var onDismissRequest: () -> DialogState = { DialogState.CLOSED },

        /**
         * The [TextInteractor] for the title.
         */
        var title: TextInteractor? = null,

        /**
         * The [TextInteractor] for the negative button.
         * If null, then the negative button is not displayed.
         */
        var negativeText: TextInteractor? = null,

        /**
         * Executes when the user clicks the negative button.
         * Returns whether the dialog should be shown.
         */
        var onNegative: () -> DialogState = { DialogState.CLOSED },

        /**
         * The [TextInteractor] for the neutral button.
         * If null, then the neutral button is not displayed.
         */
        var neutralText: TextInteractor? = null,

        /**
         * Executes when the user clicks the neutral button.
         * Returns whether the dialog should be shown.
         */
        var onNeutral: () -> DialogState = { DialogState.CLOSED },

        /**
         * The [TextInteractor] for the positive button.
         * If null, then the positive button is not displayed.
         */
        var positiveText: TextInteractor? = null,

        /**
         * Executes when the user clicks the positive button.
         * Returns whether the dialog should be shown.
         */
        var onPositive: () -> DialogState = { DialogState.CLOSED },

        /**
         * The state of the dialog indicating whether it should be opened.
         */
        var state: DialogState = DialogState.OPENED,

        /**
         * Executes when the user tries to dismiss the dialog or clicks a button and the associated callback indicates that the dialog should be closed.
         */
        var closeDialog: () -> Unit,
    ) {
        /**
         * Creates an [AlertDialogInteractor] from the components.
         *
         * The [negativeText], [neutralText], and [positiveText] are converted to uppercase.
         */
        fun build(): AlertDialogInteractor {
            fun (() -> DialogState).perform() {
                if (invoke() == DialogState.CLOSED) {
                    closeDialog()
                }
            }

            return AlertDialogInteractor(
                title = title,
                state = state,
                negativeButton = negativeText?.let { negativeText ->
                    TextButtonInteractor(
                        text = negativeText.copy(text = negativeText.text.toUpperCase()),
                        button = ButtonInteractor { onNegative.perform() },
                    )
                },
                neutralButton = neutralText?.let { neutralText ->
                    TextButtonInteractor(
                        text = neutralText.copy(text = neutralText.text.toUpperCase()),
                        button = ButtonInteractor { onNeutral.perform() },
                    )
                },
                positiveButton = positiveText?.let { positiveText ->
                    TextButtonInteractor(
                        text = positiveText.copy(text = positiveText.text.toUpperCase()),
                        button = ButtonInteractor { onPositive.perform() }
                    )
                },
                onDismissRequest = { onDismissRequest.perform() }
            )
        }

        /**
         * Applies the [text] to the title.
         */
        fun title(text: String) = apply { title = text.textInteractor() }

        /**
         * Executes the [block] when the user tries to dismiss the Dialog by clicking outside or pressing the back button.
         * This is not called when the dismiss button is clicked.
         * After execution, the dialog will be closed.
         */
        fun closeOnDismissRequest(block: () -> Unit) = apply {
            onDismissRequest = {
                block()
                DialogState.CLOSED
            }
        }

        /**
         * Executes the [block] when the user clicks the negative button.
         * After execution, the dialog will be closed.
         */
        fun closeOnNegative(block: () -> Unit) = apply {
            onNegative = {
                block()
                DialogState.CLOSED
            }
        }

        /**
         * Executes the [block] when the user clicks the neutral button.
         * After execution, the dialog will be closed.
         */
        fun closeOnNeutral(block: () -> Unit) = apply {
            onNeutral = {
                block()
                DialogState.CLOSED
            }
        }

        /**
         * Executes the [block] when the user clicks the positive button.
         * After execution, the dialog will be closed.
         */
        fun closeOnPositive(block: () -> Unit) = apply {
            onPositive = {
                block()
                DialogState.CLOSED
            }
        }

        /**
         * Applies the [text] for the negative button.
         */
        fun negativeText(text: String) = apply { negativeText = text.textInteractor() }

        /**
         * Applies the [text] for the neutral button.
         */
        fun neutralText(text: String) = apply { neutralText = text.textInteractor() }

        /**
         * Applies the [text] for the positive button.
         */
        fun positiveText(text: String) = apply { positiveText = text.textInteractor() }
    }
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