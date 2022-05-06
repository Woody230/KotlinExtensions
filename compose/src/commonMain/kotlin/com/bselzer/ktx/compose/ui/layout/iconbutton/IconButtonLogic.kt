package com.bselzer.ktx.compose.ui.layout.iconbutton

import com.bselzer.ktx.compose.ui.layout.project.LogicModel

data class IconButtonLogic(
    /**
     *  Whether or not this IconButton will handle input events and appear enabled for semantics purposes
     */
    val enabled: Boolean = true,

    /**
     * The lambda to be invoked when this icon is pressed
     */
    val onClick: () -> Unit,
) : LogicModel