package com.bselzer.ktx.compose.ui.layout.checkbox

import com.bselzer.ktx.compose.ui.layout.modifier.InteractableModifiers
import com.bselzer.ktx.compose.ui.layout.project.Interactor

data class CheckboxInteractor(
    override val modifiers: InteractableModifiers = InteractableModifiers.Default,

    /**
     * whether Checkbox is checked or unchecked
     */
    val checked: Boolean,

    /**
     * callback to be invoked when checkbox is being clicked, therefore the change of checked state in requested.
     * If null, then this is passive and relies entirely on a higher-level component to control the "checked" state.
     */
    val onCheckedChange: ((Boolean) -> Unit)? = null,

    /**
     * whether the component is enabled or grayed out
     */
    val enabled: Boolean = true,
) : Interactor(modifiers)