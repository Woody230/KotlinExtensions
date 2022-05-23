package com.bselzer.ktx.compose.ui.layout.drawer.modal

import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.saveable.rememberSaveable
import com.bselzer.ktx.compose.ui.layout.column.ColumnInteractor
import com.bselzer.ktx.compose.ui.layout.drawer.LocalDrawerState
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

/**
 * Remembers the [DrawerState] of the [ModalDrawerInteractor] and provides it to the [LocalDrawerState].
 */
@Composable
fun ModalDrawerInteractor.rememberState(
    content: @Composable () -> Unit
) = CompositionLocalProvider(
    LocalDrawerState provides rememberSaveable(
        saver = DrawerState.Saver(confirmStateChange),
        init = { state }
    ),
    content = content
)