package com.bselzer.ktx.compose.ui.layout.project

import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier

interface Interactable {
    /**
     * The [Modifier]s.
     */
    val modifier: InteractableModifier
}