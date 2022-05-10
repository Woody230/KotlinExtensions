package com.bselzer.ktx.compose.ui.layout.tristatecheckbox

import androidx.compose.ui.state.ToggleableState
import com.bselzer.ktx.compose.ui.layout.modifier.InteractableModifiers
import com.bselzer.ktx.compose.ui.layout.project.Interactor

data class TriStateCheckboxInteractor(
    override val modifiers: InteractableModifiers = InteractableModifiers.Default,

    /**
     * whether TriStateCheckbox is checked, unchecked or in indeterminate state
     */
    val state: ToggleableState,

    /**
     * callback to be invoked when checkbox is being clicked, therefore the change of ToggleableState state is requested.
     * If null, then this is passive and relies entirely on a higher-level component to control the state.
     */
    val onClick: (() -> Unit)? = null,

    /**
     * whether the component is enabled or grayed out
     */
    val enabled: Boolean = true,
) : Interactor(modifiers)