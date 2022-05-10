package com.bselzer.ktx.compose.ui.layout.project

import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.modifier.InteractableModifiers

interface Interactable {
    /**
     * The [Modifier]s.
     */
    val modifiers: InteractableModifiers
}