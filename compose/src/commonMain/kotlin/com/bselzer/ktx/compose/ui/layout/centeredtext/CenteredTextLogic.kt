package com.bselzer.ktx.compose.ui.layout.centeredtext

import com.bselzer.ktx.compose.ui.layout.project.LogicModel
import com.bselzer.ktx.compose.ui.layout.spacer.SpacerLogic
import com.bselzer.ktx.compose.ui.layout.text.TextLogic

data class CenteredTextLogic(
    /**
     * The [LogicModel] of the starting text.
     */
    val start: TextLogic,

    /**
     * The [LogicModel] of the ending text.
     */
    val end: TextLogic,

    /**
     * The [LogicModel] of the spacing between the starting and ending text.
     */
    val spacer: SpacerLogic = SpacerLogic.Default
) : LogicModel