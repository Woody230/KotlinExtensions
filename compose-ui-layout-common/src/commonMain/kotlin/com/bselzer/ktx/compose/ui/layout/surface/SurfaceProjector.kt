package com.bselzer.ktx.compose.ui.layout.surface

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector

class SurfaceProjector(
    interactor: SurfaceInteractor = SurfaceInteractor.Default,
    presenter: SurfacePresenter = SurfacePresenter.Default
) : Projector<SurfaceInteractor, SurfacePresenter>(interactor, presenter) {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        content: @Composable () -> Unit
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        if (interactor.onClick == null) {
            Surface(
                modifier = combinedModifier,
                shape = presenter.shape,
                color = presenter.color,
                contentColor = presenter.contentColor,
                border = presenter.border,
                elevation = presenter.elevation,
                content = content
            )
        } else {
            Surface(
                onClick = interactor.onClick,
                modifier = combinedModifier,
                enabled = interactor.enabled,
                shape = presenter.shape,
                color = presenter.color,
                contentColor = presenter.contentColor,
                border = presenter.border,
                elevation = presenter.elevation,
                interactionSource = interactionSource,
                content = content
            )
        }
    }
}