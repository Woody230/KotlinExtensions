package com.bselzer.ktx.compose.ui.layout.tristatecheckbox

import androidx.compose.ui.state.ToggleableState
import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.project.Interactor

data class TriStateCheckboxInteractor(
    override val modifier: InteractableModifier = InteractableModifier,

    /**
     * whether TriStateCheckbox is checked, unchecked or in indeterminate state
     */
    val state: ToggleableState,

    /**
     * whether the component is enabled or grayed out
     */
    val enabled: Boolean = true,

    /**
     * callback to be invoked when checkbox is being clicked, therefore the change of ToggleableState state is requested.
     * If null, then this is passive and relies entirely on a higher-level component to control the state.
     */
    val onClick: (() -> Unit)? = null,
) : Interactor(modifier)