package com.bselzer.ktx.compose.ui.layout.modaldrawer

import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import com.bselzer.ktx.compose.ui.layout.column.ColumnLogic
import com.bselzer.ktx.compose.ui.layout.description.DescriptionLogic
import com.bselzer.ktx.compose.ui.layout.icontext.IconTextLogic
import com.bselzer.ktx.compose.ui.layout.image.ImageLogic
import com.bselzer.ktx.compose.ui.layout.project.LogicModel

data class ModalDrawerLogic(
    /**
     * The [LogicModel] for the container holding the header and sections.
     */
    val container: ColumnLogic = ColumnLogic.Default,

    /**
     * The [LogicModel] for the header image.
     */
    val image: ImageLogic? = null,

    /**
     * The [LogicModel] for the header description.
     */
    val description: DescriptionLogic? = null,

    /**
     * The [LogicModel]s for the sections of icon/text components.
     */
    val components: List<List<IconTextLogic>> = emptyList(),

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
) : LogicModel {
    /**
     * The state of the drawer.
     */
    val state = DrawerState(value, confirmStateChange)
}