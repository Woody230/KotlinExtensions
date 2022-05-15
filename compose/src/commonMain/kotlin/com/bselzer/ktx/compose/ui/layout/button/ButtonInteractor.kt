package com.bselzer.ktx.compose.ui.layout.button

import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.project.Interactor

data class ButtonInteractor(
    override val modifier: InteractableModifier = InteractableModifier,

    /**
     * Controls the enabled state of the button. When false, this button will not be clickable
     */
    val enabled: Boolean = true,

    /**
     * Will be called when the user clicks the button.
     */
    val onClick: () -> Unit
) : Interactor(modifier)