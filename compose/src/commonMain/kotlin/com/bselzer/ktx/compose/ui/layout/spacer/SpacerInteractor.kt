package com.bselzer.ktx.compose.ui.layout.spacer

import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.modifier.InteractableModifiers
import com.bselzer.ktx.compose.ui.layout.project.Interactor

data class SpacerInteractor(
    override val modifiers: InteractableModifiers = InteractableModifiers.Default
) : Interactor(modifiers) {
    companion object {
        @Stable
        val Default = SpacerInteractor()
    }
}