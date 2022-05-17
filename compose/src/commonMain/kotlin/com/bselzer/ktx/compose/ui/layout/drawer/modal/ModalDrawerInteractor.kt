package com.bselzer.ktx.compose.ui.layout.drawer.modal

import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.project.Interactor

data class ModalDrawerInteractor(
    override val modifier: InteractableModifier = InteractableModifier,

    /**
     * The initial value of the state.
     */
    val value: DrawerValue = DrawerValue.Closed,

    /**
     * Optional callback invoked to confirm or veto a pending state change.
     */
    val confirmStateChange: (DrawerValue) -> Boolean = { true },

    /**
     * Whether or not drawer can be interacted by gestures
     */
    val gesturesEnabled: Boolean = true,
) : Interactor(modifier) {
    /**
     * The state of the drawer.
     */
    val state = DrawerState(value, confirmStateChange)

    companion object {
        @Stable
        val Default = ModalDrawerInteractor()
    }
}