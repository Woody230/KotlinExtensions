package com.bselzer.ktx.compose.ui.layout.badge

import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.project.Interactor

data class BadgeInteractor(
    override val modifier: InteractableModifier = InteractableModifier
) : Interactor(modifier) {
    companion object {
        @Stable
        val Default = BadgeInteractor()
    }
}