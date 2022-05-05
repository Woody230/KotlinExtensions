package com.bselzer.ktx.compose.ui.layout.button

import com.bselzer.ktx.compose.ui.layout.project.LogicModel

data class ButtonLogic(
    /**
     * Controls the enabled state of the button. When false, this button will not be clickable
     */
    val enabled: Boolean = true,

    /**
     * Will be called when the user clicks the button.
     */
    val onClick: () -> Unit = {}
) : LogicModel