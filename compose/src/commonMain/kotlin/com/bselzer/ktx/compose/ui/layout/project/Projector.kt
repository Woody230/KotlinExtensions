package com.bselzer.ktx.compose.ui.layout.project

import androidx.compose.runtime.Composable

abstract class Projector<Interactor, Presenter> :
    Projectable<Interactor, Presenter> where Interactor : Interactable, Presenter : Presentable<Presenter> {
    /**
     * Calls the specified block with the localized version of the [presenter].
     */
    @Composable
    protected fun contextualize(block: @Composable Presenter.() -> Unit) = presenter.localized().block() // TODO add the interaction model with context receivers
}