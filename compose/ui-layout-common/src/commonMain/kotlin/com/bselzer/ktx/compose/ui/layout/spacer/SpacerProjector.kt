package com.bselzer.ktx.compose.ui.layout.spacer

import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector

class SpacerProjector(
    interactor: SpacerInteractor,
    presenter: SpacerPresenter = SpacerPresenter.Default
) : Projector<SpacerInteractor, SpacerPresenter>(interactor, presenter) {
    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        Spacer(modifier = combinedModifier)
    }
}