package com.bselzer.ktx.compose.ui.layout.iconbutton

import com.bselzer.ktx.compose.ui.layout.icon.IconInteractor
import com.bselzer.ktx.compose.ui.layout.project.Interactor

data class IconButtonInteractor(
    /**
     *  Whether or not this IconButton will handle input events and appear enabled for semantics purposes
     */
    val enabled: Boolean = true,

    /**
     * The [Interactor] for the icon.
     */
    val icon: IconInteractor,

    /**
     * The lambda to be invoked when this icon is pressed
     */
    val onClick: () -> Unit,
) : Interactor()