package com.bselzer.ktx.compose.ui.layout.project

import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier

/**
 * Represents a model for providing data and interactions.
 */
abstract class Interactor(
    override val modifier: InteractableModifier
) : Interactable