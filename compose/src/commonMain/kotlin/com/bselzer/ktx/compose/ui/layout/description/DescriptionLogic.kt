package com.bselzer.ktx.compose.ui.layout.description

import com.bselzer.ktx.compose.ui.layout.project.LogicModel
import com.bselzer.ktx.compose.ui.layout.text.TextLogic

data class DescriptionLogic(
    /**
     * The [LogicModel] for the title.
     */
    val title: TextLogic,

    /**
     * The [LogicModel] for the subtitle.
     */
    val subtitle: TextLogic
) : LogicModel