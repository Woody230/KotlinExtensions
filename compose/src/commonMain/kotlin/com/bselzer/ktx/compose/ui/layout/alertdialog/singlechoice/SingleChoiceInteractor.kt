package com.bselzer.ktx.compose.ui.layout.alertdialog.singlechoice

import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.project.Interactor

data class SingleChoiceInteractor<Choice>(
    override val modifier: InteractableModifier = InteractableModifier,

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
) : Interactor(modifier)