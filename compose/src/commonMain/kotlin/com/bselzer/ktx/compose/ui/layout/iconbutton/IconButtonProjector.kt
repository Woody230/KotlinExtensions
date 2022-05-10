package com.bselzer.ktx.compose.ui.layout.iconbutton

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.icon.IconProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector

class IconButtonProjector(
    override val interactor: IconButtonInteractor,
    override val presenter: IconButtonPresenter = IconButtonPresenter.Default
) : Projector<IconButtonInteractor, IconButtonPresenter>() {
    private val iconProjection = IconProjector(interactor.icon, presenter.icon)

    @Composable
    fun project(
        modifier: Modifier = Modifier,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    ) = contextualize {
        IconButton(
            onClick = interactor.onClick,
            modifier = modifier,
            enabled = interactor.enabled,
            interactionSource = interactionSource,
        ) {
            iconProjection.project()
        }
    }
}
