package com.bselzer.ktx.compose.ui.layout.checkbox

import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.project.Interactor

data class CheckboxInteractor(
    override val modifier: InteractableModifier = InteractableModifier,

    /**
     * whether Checkbox is checked or unchecked
     */
    val checked: Boolean,

    /**
     * whether the component is enabled or grayed out
     */
    val enabled: Boolean = true,

    /**
     * callback to be invoked when checkbox is being clicked, therefore the change of checked state in requested.
     * If null, then this is passive and relies entirely on a higher-level component to control the "checked" state.
     */
    val onCheckedChange: ((Boolean) -> Unit)? = null,
) : Interactor(modifier)