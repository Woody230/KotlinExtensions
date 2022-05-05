package com.bselzer.ktx.compose.ui.layout.radiobutton

import com.bselzer.ktx.compose.ui.layout.project.LogicModel

data class RadioButtonLogic(
    /**
     * Whether the button is selected.
     */
    val selected: Boolean,

    /**
     * Controls the enabled state of the RadioButton. When false, this button will not be selectable and appears in the disabled ui state
     */
    val enabled: Boolean = true,

    /**
     * Callback to be invoked when the RadioButton is being clicked. If null, then this is passive and relies entirely on a higher-level component to control the state.
     */
    val onClick: (() -> Unit)? = null
) : LogicModel