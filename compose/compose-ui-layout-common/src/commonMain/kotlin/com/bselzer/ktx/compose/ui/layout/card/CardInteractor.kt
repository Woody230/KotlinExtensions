package com.bselzer.ktx.compose.ui.layout.card

import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.project.Interactor

data class CardInteractor(
    override val modifier: InteractableModifier = InteractableModifier,

    /**
     * Callback to be called when the card is clicked
     */
    val onClick: (() -> Unit)? = null,

    /**
     * Controls the enabled state of the card. When false, this card will not be clickable
     */
    val enabled: Boolean = true,
) : Interactor(modifier) {
    companion object {
        @Stable
        val Default = CardInteractor()
    }
}