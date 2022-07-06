package com.bselzer.ktx.compose.ui.layout.textbutton

import com.bselzer.ktx.compose.ui.layout.button.ButtonInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.project.Interactor
import com.bselzer.ktx.compose.ui.layout.text.TextInteractor

data class TextButtonInteractor(
    override val modifier: InteractableModifier = InteractableModifier,

    /**
     * The [Interactor] for the button.
     */
    val button: ButtonInteractor,

    /**
     * The [Interactor] for the text.
     */
    val text: TextInteractor
) : Interactor(modifier)