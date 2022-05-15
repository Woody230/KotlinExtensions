package com.bselzer.ktx.compose.ui.layout.modifier.interactable

import androidx.compose.ui.Modifier

internal data class CombinedInteractableModifier(
    val outer: InteractableModifier,
    val inner: InteractableModifier
) : InteractableModifier {
    override val modifier: Modifier = outer.modifier.then(inner.modifier)
}