package com.bselzer.ktx.compose.ui.layout.background.image

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.box.BoxProjector
import com.bselzer.ktx.compose.ui.layout.image.ImageProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector

class BackgroundImageProjector(
    override val interactor: BackgroundImageInteractor,
    override val presenter: BackgroundImagePresenter = BackgroundImagePresenter.Default
) : Projector<BackgroundImageInteractor, BackgroundImagePresenter>() {
    private val containerProjector = BoxProjector(interactor.container, presenter.container)

    @Composable
    fun project(
        modifier: Modifier = Modifier,
        content: @Composable BoxScope.() -> Unit
    ) = contextualize(modifier) { combinedModifier ->
        containerProjector.Projection(modifier = combinedModifier) {
            ImageProjector(
                interactor = interactor.background,

                // TODO scoped modifiable handling?
                presenter = backgroundImagePresenter() merge presenter.background
            ).Projection()
            content()
        }
    }
}