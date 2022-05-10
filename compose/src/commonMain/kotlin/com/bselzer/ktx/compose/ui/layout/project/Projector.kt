package com.bselzer.ktx.compose.ui.layout.project

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.modifier.then

abstract class Projector<Interactor, Presenter> :
    Projectable<Interactor, Presenter> where Interactor : Interactable, Presenter : Presentable<Presenter> {

    // TODO add the interactor with context receivers
    /**
     * Calls the specified block with the localized version of the [presenter].
     */
    @Composable
    protected fun contextualize(modifier: Modifier, block: @Composable Presenter.(Modifier) -> Unit) = presenter.localized().block(
        modifier
            .then(interactor.modifiers)
            .then(presenter.modifiers)
    )
}