package com.bselzer.ktx.compose.ui.layout.icon

import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector

class IconProjector(
    interactor: IconInteractor,
    presenter: IconPresenter = IconPresenter.Default
) : Projector<IconInteractor, IconPresenter>(interactor, presenter) {
    @Composable
    fun Projection(
        modifier: Modifier = Modifier
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        Icon(
            painter = interactor.painter,
            contentDescription = interactor.contentDescription,
            modifier = combinedModifier,
            tint = presenter.tint
        )
    }
}