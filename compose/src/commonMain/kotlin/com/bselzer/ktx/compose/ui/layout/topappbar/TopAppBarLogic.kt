package com.bselzer.ktx.compose.ui.layout.topappbar

import com.bselzer.ktx.compose.ui.layout.iconbutton.IconButtonLogic
import com.bselzer.ktx.compose.ui.layout.project.LogicModel
import com.bselzer.ktx.compose.ui.layout.text.TextLogic

data class TopAppBarLogic(
    /**
     * The [LogicModel] for the title.
     */
    val title: TextLogic,

    /**
     * The [LogicModel] for the navigation icon.
     */
    val navigation: IconButtonLogic? = null,

    /**
     * The [LogicModel] for the action icons.
     */
    val actions: Collection<IconButtonLogic> = emptyList()
) : LogicModel