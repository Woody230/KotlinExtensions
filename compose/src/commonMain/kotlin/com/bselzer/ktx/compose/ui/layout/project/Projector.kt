package com.bselzer.ktx.compose.ui.layout.project

import androidx.compose.runtime.Composable

abstract class Projector<Logic, Presentation> : Projectable<Logic, Presentation> where Logic : LogicModel, Presentation : PresentationModel<Presentation> {
    /**
     * Calls the specified block with the localized version of the [presentation].
     */
    @Composable
    protected fun contextualize(block: @Composable Presentation.() -> Unit) = presentation.localized().block() // TODO add the logic model with context receivers
}