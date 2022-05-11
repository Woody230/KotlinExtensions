package com.bselzer.ktx.compose.ui.layout.modaldrawer

import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.column.ColumnInteractor
import com.bselzer.ktx.compose.ui.layout.description.DescriptionInteractor
import com.bselzer.ktx.compose.ui.layout.icontext.IconTextInteractor
import com.bselzer.ktx.compose.ui.layout.image.ImageInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.InteractableModifiers
import com.bselzer.ktx.compose.ui.layout.project.Interactor

data class ModalDrawerInteractor(
    override val modifiers: InteractableModifiers = InteractableModifiers.Default,

    /**
     * The [Interactor] for the container holding the header and sections.
     */
    val container: ColumnInteractor = ColumnInteractor.Default,

    /**
     * The [Interactor] for the header image.
     */
    val image: ImageInteractor? = null,

    /**
     * The [Interactor] for the header description.
     */
    val description: DescriptionInteractor? = null,

    /**
     * The [Interactor]s for the sections of icon/text components.
     */
    val components: List<List<IconTextInteractor>> = emptyList(),

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
) : Interactor(modifiers) {
    /**
     * The state of the drawer.
     */
    val state = DrawerState(value, confirmStateChange)

    companion object {
        @Stable
        val Default = ModalDrawerInteractor()
    }
}