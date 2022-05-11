package com.bselzer.ktx.compose.ui.layout.preference.textfield

import com.bselzer.ktx.compose.ui.layout.modifier.InteractableModifiers
import com.bselzer.ktx.compose.ui.layout.preference.alertdialog.AlertDialogPreferenceInteractor
import com.bselzer.ktx.compose.ui.layout.project.Interactor
import com.bselzer.ktx.compose.ui.layout.text.TextInteractor
import com.bselzer.ktx.compose.ui.layout.textfield.TextFieldInteractor

data class TextFieldPreferenceInteractor(
    override val modifiers: InteractableModifiers = InteractableModifiers.Default,

    /**
     * The [Interactor] for core preference and dialog properties.
     */
    val preference: AlertDialogPreferenceInteractor,

    /**
     * The [Interactor] for the subtitle of the dialog, describing the preference for input.
     */
    val inputDescription: TextInteractor,

    /**
     * The [Interactor] for the input field.
     */
    val input: TextFieldInteractor,

    /**
     * The initial state of the [input].
     */
    val initialInput: String = ""
) : Interactor(modifiers)