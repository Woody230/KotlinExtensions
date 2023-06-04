package com.bselzer.ktx.compose.ui.layout.modifier.interactable

import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.modifier.Modifiable

interface InteractableModifier : Modifiable {
    infix fun then(other: InteractableModifier): InteractableModifier =
        if (other === InteractableModifier) this else CombinedInteractableModifier(this, other)

    companion object : InteractableModifier {
        override val modifier: Modifier = Modifier
    }
}