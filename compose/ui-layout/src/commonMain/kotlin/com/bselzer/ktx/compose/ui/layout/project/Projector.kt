package com.bselzer.ktx.compose.ui.layout.project

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

abstract class Projector<Interactor, Presenter>(
    private val interactor: Interactor,
    private val presenter: Presenter
) : Projectable<Interactor, Presenter> where Interactor : Interactable, Presenter : Presentable<Presenter> {

    // TODO context receivers
    /**
     * Calls the specified block with the localized version of the [presenter].
     */
    @Composable
    protected fun contextualize(modifier: Modifier, block: @Composable (Modifier, Interactor, Presenter) -> Unit) = presenter.localized().run {
        block(
            modifier
                .then(this.modifier.modifier)
                .then(interactor.modifier.modifier),
            interactor,
            this
        )
    }
}