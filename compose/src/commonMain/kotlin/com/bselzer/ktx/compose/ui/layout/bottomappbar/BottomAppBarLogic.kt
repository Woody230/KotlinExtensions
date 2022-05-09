package com.bselzer.ktx.compose.ui.layout.bottomappbar

import com.bselzer.ktx.compose.ui.layout.iconbutton.IconButtonLogic
import com.bselzer.ktx.compose.ui.layout.project.LogicModel

data class BottomAppBarLogic(
    /**
     * The [LogicModel] for the navigation icon.
     */
    val navigation: IconButtonLogic? = null,

    /**
     * The [LogicModel] for the action icons.
     */
    val actions: List<IconButtonLogic> = emptyList()
) : LogicModel