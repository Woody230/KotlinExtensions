package com.bselzer.ktx.compose.ui.layout.alertdialog

import androidx.compose.foundation.clickable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.toUpperCase
import com.bselzer.ktx.compose.ui.layout.button.ButtonInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.project.Interactor
import com.bselzer.ktx.compose.ui.layout.text.TextInteractor
import com.bselzer.ktx.compose.ui.layout.text.textInteractor
import com.bselzer.ktx.compose.ui.layout.textbutton.TextButtonInteractor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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
         */
        var onDismissRequest: suspend CoroutineScope.() -> Unit = { closeDialog() },

        /**
         * The text for the title.
         */
        var title: String? = null,

        /**
         * The text for the negative button.
         * If null, then the negative button is not displayed.
         */
        var negativeText: String? = null,

        /**
         * Whether the negative button is enabled.
         */
        var negativeEnabled: Boolean = true,

        /**
         * Executes when the user clicks the negative button.
         * Returns whether the dialog should be shown.
         */
        var onNegative: suspend CoroutineScope.() -> DialogState = { DialogState.CLOSED },

        /**
         * The text for the neutral button.
         * If null, then the neutral button is not displayed.
         */
        var neutralText: String? = null,

        /**
         * Whether the neutral button is enabled.
         */
        var neutralEnabled: Boolean = true,

        /**
         * Executes when the user clicks the neutral button.
         * Returns whether the dialog should be shown.
         */
        var onNeutral: suspend CoroutineScope.() -> DialogState = { DialogState.CLOSED },

        /**
         * The text for the positive button.
         * If null, then the positive button is not displayed.
         */
        var positiveText: String? = null,

        /**
         * Whether the positive button is enabled.
         */
        var positiveEnabled: Boolean = true,

        /**
         * Executes when the user clicks the positive button.
         * Returns whether the dialog should be shown.
         */
        var onPositive: suspend CoroutineScope.() -> DialogState = { DialogState.CLOSED },

        /**
         * The state of the dialog indicating whether it should be opened.
         */
        var state: DialogState = DialogState.OPENED,

        /**
         * Executes when the user tries to dismiss the dialog or clicks a button and the associated callback indicates that the dialog should be closed.
         */
        var closeDialog: suspend CoroutineScope.() -> Unit,
    ) {
        /**
         * Creates a new instance of the [AlertDialogInteractor.Builder].
         *
         * The [AlertDialogInteractor.Builder.state] is set to the [state].
         * On closing of the dialog, the [state] is updated to [DialogState.CLOSED] and the [onClose] callback is called.
         */
        constructor(state: MutableState<DialogState>, onClose: suspend CoroutineScope.() -> Unit = {}) : this(state = state.value, closeDialog = {
            onClose()
            state.value = DialogState.CLOSED
        })

        /**
         * Applies the [block] to this builder then creates an [AlertDialogInteractor] from the components.
         *
         * The [negativeText], [neutralText], and [positiveText] are converted to uppercase.
         */
        @Composable
        fun build(block: @Composable Builder.() -> Unit = {}): AlertDialogInteractor = block(this).run {
            val scope = rememberCoroutineScope()
            fun (suspend CoroutineScope.() -> DialogState).perform() = scope.launch {
                if (invoke(this) == DialogState.CLOSED) {
                    closeDialog()
                }
            }

            return AlertDialogInteractor(
                title = title?.textInteractor(),
                state = state,
                negativeButton = negativeText?.textInteractor()?.let { negativeText ->
                    TextButtonInteractor(
                        text = negativeText.copy(text = negativeText.text.toUpperCase()),
                        button = ButtonInteractor(enabled = negativeEnabled) { onNegative.perform() },
                    )
                },
                neutralButton = neutralText?.textInteractor()?.let { neutralText ->
                    TextButtonInteractor(
                        text = neutralText.copy(text = neutralText.text.toUpperCase()),
                        button = ButtonInteractor(enabled = neutralEnabled) { onNeutral.perform() },
                    )
                },
                positiveButton = positiveText?.textInteractor()?.let { positiveText ->
                    TextButtonInteractor(
                        text = positiveText.copy(text = positiveText.text.toUpperCase()),
                        button = ButtonInteractor(enabled = positiveEnabled) { onPositive.perform() }
                    )
                },
                onDismissRequest = {
                    scope.launch { onDismissRequest() }
                }
            )
        }

        /**
         * Executes the [block] when the user tries to dismiss the Dialog by clicking outside or pressing the back button.
         * This is not called when the dismiss button is clicked.
         * After execution, the dialog will be closed.
         */
        @Composable
        fun closeOnDismissRequest(block: suspend CoroutineScope.() -> Unit) = apply {
            onDismissRequest = {
                block()
                closeDialog()
            }
        }

        /**
         * Executes the [block] when the user clicks the negative button.
         * After execution, the dialog will be closed.
         */
        @Composable
        fun closeOnNegative(block: suspend CoroutineScope.() -> Unit) = apply {
            onNegative = {
                block()
                DialogState.CLOSED
            }
        }

        /**
         * Executes the [block] when the user clicks the neutral button.
         * After execution, the dialog will be closed.
         */
        @Composable
        fun closeOnNeutral(block: suspend CoroutineScope.() -> Unit) = apply {
            onNeutral = {
                block()
                DialogState.CLOSED
            }
        }

        /**
         * Executes the [block] when the user clicks the positive button.
         * After execution, the dialog will be closed.
         */
        @Composable
        fun closeOnPositive(block: suspend CoroutineScope.() -> Unit) = apply {
            onPositive = {
                block()
                DialogState.CLOSED
            }
        }
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

/**
 * @return a modifier that sets the [DialogState] to open when clicked
 */
fun MutableState<DialogState>.openOnClick(): Modifier = Modifier.clickable { value = DialogState.OPENED }

/**
 * Remembers the mutable [DialogState] whose initial value is [initial].
 */
@Composable
fun rememberDialogState(initial: DialogState = DialogState.CLOSED) = remember { mutableStateOf(initial) }