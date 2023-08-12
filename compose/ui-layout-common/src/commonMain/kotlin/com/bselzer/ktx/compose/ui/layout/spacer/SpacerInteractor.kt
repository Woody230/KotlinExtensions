package com.bselzer.ktx.compose.ui.layout.spacer

import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.project.Interactor

data class SpacerInteractor(
    override val modifier: InteractableModifier = InteractableModifier
) : Interactor(modifier) {
    companion object {
        @Stable
        val Default = SpacerInteractor()
    }
}