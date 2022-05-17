package com.bselzer.ktx.compose.ui.layout.iconbutton

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.icon.IconProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector

class IconButtonProjector(
    interactor: IconButtonInteractor,
    presenter: IconButtonPresenter = IconButtonPresenter.Default
) : Projector<IconButtonInteractor, IconButtonPresenter>(interactor, presenter) {

    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        val iconProjector = IconProjector(interactor.icon, presenter.icon)

        IconButton(
            onClick = interactor.onClick,
            modifier = combinedModifier,
            enabled = interactor.enabled,
            interactionSource = interactionSource,
        ) {
            iconProjector.Projection()
        }
    }
}
