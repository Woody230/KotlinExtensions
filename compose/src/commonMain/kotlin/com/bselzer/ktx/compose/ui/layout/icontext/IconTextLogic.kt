package com.bselzer.ktx.compose.ui.layout.icontext

import com.bselzer.ktx.compose.ui.layout.icon.IconLogic
import com.bselzer.ktx.compose.ui.layout.project.LogicModel
import com.bselzer.ktx.compose.ui.layout.row.RowLogic
import com.bselzer.ktx.compose.ui.layout.text.TextLogic

data class IconTextLogic(
    /**
     * The [LogicModel] of the container holding the icon and text.
     */
    val container: RowLogic = RowLogic.Default,

    /**
     * The [LogicModel] of the icon.
     */
    val icon: IconLogic,

    /**
     * The [LogicModel] of the text.
     */
    val text: TextLogic,
) : LogicModel