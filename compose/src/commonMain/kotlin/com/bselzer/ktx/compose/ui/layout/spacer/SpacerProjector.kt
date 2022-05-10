package com.bselzer.ktx.compose.ui.layout.spacer

import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector

class SpacerProjector(
    override val interactor: SpacerInteractor,
    override val presenter: SpacerPresenter = SpacerPresenter.Default
) : Projector<SpacerInteractor, SpacerPresenter>() {
    @Composable
    fun project(
        modifier: Modifier = Modifier,
    ) = contextualize(modifier) { combinedModifier ->
        Spacer(modifier = combinedModifier)
    }
}