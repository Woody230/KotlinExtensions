package com.bselzer.ktx.compose.ui.layout.image

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector

class ImageProjector(
    override val interactor: ImageInteractor,
    override val presenter: ImagePresenter = ImagePresenter.Default
) : Projector<ImageInteractor, ImagePresenter>() {
    @Composable
    fun project(
        modifier: Modifier = Modifier
    ) = contextualize {
        Image(
            painter = interactor.painter,
            contentDescription = interactor.contentDescription,
            modifier = modifier,
            alignment = alignment,
            contentScale = contentScale,
            alpha = alpha,
            colorFilter = colorFilter
        )
    }
}