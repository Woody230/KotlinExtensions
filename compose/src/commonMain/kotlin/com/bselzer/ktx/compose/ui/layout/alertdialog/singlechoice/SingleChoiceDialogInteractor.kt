package com.bselzer.ktx.compose.ui.layout.alertdialog.singlechoice

import com.bselzer.ktx.compose.ui.layout.modifier.InteractableModifiers
import com.bselzer.ktx.compose.ui.layout.project.Interactor

data class SingleChoiceDialogInteractor<Choice>(
    override val modifiers: InteractableModifiers = InteractableModifiers.Default,

    /**
     * The selected value.
     */
    val selected: Choice?,

    /**
     * All possible values to select from.
     */
    val values: List<Choice>,

    /**
     * Converts the value into a displayable label.
     */
    val getLabel: (Choice) -> String,

    /**
     * Callback for managing the selection of a value.
     */
    val onSelection: (Choice) -> Unit
) : Interactor(modifiers)