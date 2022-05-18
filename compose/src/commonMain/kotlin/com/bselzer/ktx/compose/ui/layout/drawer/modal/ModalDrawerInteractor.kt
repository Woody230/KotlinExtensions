package com.bselzer.ktx.compose.ui.layout.drawer.modal

import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.column.ColumnInteractor
import com.bselzer.ktx.compose.ui.layout.drawer.header.DrawerHeaderInteractor
import com.bselzer.ktx.compose.ui.layout.drawer.section.DrawerSectionInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.project.Interactor

data class ModalDrawerInteractor(
    override val modifier: InteractableModifier = InteractableModifier,
    val container: ColumnInteractor = ColumnInteractor.Default,
    val header: DrawerHeaderInteractor? = null,
    val sections: List<DrawerSectionInteractor> = emptyList(),

    /**
     * Optional callback invoked to confirm or veto a pending state change.
     */
    val confirmStateChange: (DrawerValue) -> Boolean = { true },

    /**
     * The state of the drawer.
     */
    val state: DrawerState = DrawerState(initialValue = DrawerValue.Closed, confirmStateChange),

    /**
     * Whether or not drawer can be interacted by gestures
     */
    val gesturesEnabled: Boolean = true,
) : Interactor(modifier) {
    companion object {
        @Stable
        val Default = ModalDrawerInteractor()
    }
}