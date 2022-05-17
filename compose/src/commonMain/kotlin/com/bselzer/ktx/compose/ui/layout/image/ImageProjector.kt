package com.bselzer.ktx.compose.ui.layout.image

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector

class ImageProjector(
    interactor: ImageInteractor,
    presenter: ImagePresenter = ImagePresenter.Default
) : Projector<ImageInteractor, ImagePresenter>(interactor, presenter) {
    @Composable
    fun Projection(
        modifier: Modifier = Modifier
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        Image(
            painter = interactor.painter,
            contentDescription = interactor.contentDescription,
            modifier = combinedModifier,
            alignment = presenter.alignment,
            contentScale = presenter.contentScale,
            alpha = presenter.alpha,
            colorFilter = presenter.colorFilter
        )
    }
}