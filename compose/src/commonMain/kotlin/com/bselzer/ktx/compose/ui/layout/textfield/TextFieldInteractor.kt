package com.bselzer.ktx.compose.ui.layout.textfield

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import com.bselzer.ktx.compose.ui.layout.icon.IconInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.InteractableModifiers
import com.bselzer.ktx.compose.ui.layout.project.Interactor
import com.bselzer.ktx.compose.ui.layout.text.TextInteractor

data class TextFieldInteractor(
    override val modifiers: InteractableModifiers = InteractableModifiers.Default,

    /**
     * The [Interactor] for the label to be displayed inside the text field container.
     */
    val label: TextInteractor? = null,

    /**
     * The [Interactor] for the placeholder to be displayed when the text field is in focus and the input text is empty.
     */
    val placeholder: TextInteractor? = null,

    /**
     * The [Interactor] for the leading icon to be displayed at the beginning of the text field container.
     */
    val leadingIcon: IconInteractor? = null,

    /**
     * The [Interactor] for the trailing icon to be displayed at the end of the text field container.
     */
    val trailingIcon: IconInteractor? = null,

    /**
     * the input text to be shown in the text field
     */
    val value: String,

    /**
     * The callback that is triggered when the input service updates the text. An updated text comes as a parameter of the callback
     */
    val onValueChange: (String) -> Unit,

    /**
     * Controls the enabled state of the TextField.
     * When false, the text field will be neither editable nor focusable, the input of the text field will not be selectable, visually text field will appear in the disabled UI state
     */
    val enabled: Boolean = true,

    /**
     * Controls the editable state of the TextField. When true, the text field can not be modified, however, a user can focus it and copy text from it.
     * Read-only text fields are usually used to display pre-filled forms that user can not edit
     */
    val readOnly: Boolean = false,

    /**
     * Indicates if the text field's current value is in error.
     * If set to true, the label, bottom indicator and trailing icon by default will be displayed in error color
     */
    val isError: Boolean = false,

    /**
     * Software keyboard options that contains configuration such as KeyboardType and ImeAction.
     */
    val keyboardOptions: KeyboardOptions = KeyboardOptions.Default,

    /**
     * When the input service emits an IME action, the corresponding callback is called.
     * Note that this IME action may be different from what you specified in KeyboardOptions.imeAction.
     */
    val keyboardActions: KeyboardActions = KeyboardActions.Default,
) : Interactor(modifiers)