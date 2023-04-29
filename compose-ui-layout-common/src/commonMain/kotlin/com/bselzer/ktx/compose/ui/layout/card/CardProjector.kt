package com.bselzer.ktx.compose.ui.layout.card

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector

class CardProjector(
    interactor: CardInteractor = CardInteractor.Default,
    presenter: CardPresenter = CardPresenter.Default
) : Projector<CardInteractor, CardPresenter>(interactor, presenter) {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        content: @Composable () -> Unit
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        if (interactor.onClick == null) {
            Card(
                modifier = combinedModifier,
                shape = presenter.shape,
                backgroundColor = presenter.backgroundColor,
                contentColor = presenter.contentColor,
                border = presenter.border,
                elevation = presenter.elevation,
                content = content
            )
        } else {
            Card(
                onClick = interactor.onClick,
                modifier = combinedModifier,
                enabled = interactor.enabled,
                shape = presenter.shape,
                backgroundColor = presenter.backgroundColor,
                contentColor = presenter.contentColor,
                border = presenter.border,
                elevation = presenter.elevation,
                interactionSource = interactionSource,
                content = content
            )
        }
    }
}