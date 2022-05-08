package com.bselzer.ktx.compose.ui.layout.textbutton

import com.bselzer.ktx.compose.ui.layout.button.ButtonLogic
import com.bselzer.ktx.compose.ui.layout.project.LogicModel
import com.bselzer.ktx.compose.ui.layout.text.TextLogic

data class TextButtonLogic(
    /**
     * The [LogicModel] for the button.
     */
    val button: ButtonLogic,

    /**
     * The [LogicModel] for the text.
     */
    val text: TextLogic
) : LogicModel