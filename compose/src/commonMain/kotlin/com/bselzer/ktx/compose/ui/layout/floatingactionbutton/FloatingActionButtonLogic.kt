package com.bselzer.ktx.compose.ui.layout.floatingactionbutton

import com.bselzer.ktx.compose.ui.layout.icon.IconLogic
import com.bselzer.ktx.compose.ui.layout.project.LogicModel
import com.bselzer.ktx.compose.ui.layout.text.TextLogic

data class FloatingActionButtonLogic(
    /**
     * The [LogicModel] of the text for an expanded FAB.
     */
    val text: TextLogic? = null,

    /**
     * The [LogicModel] of the icon.
     */
    val icon: IconLogic? = null,

    /**
     * Callback invoked when this FAB is clicked
     */
    val onClick: () -> Unit,
) : LogicModel