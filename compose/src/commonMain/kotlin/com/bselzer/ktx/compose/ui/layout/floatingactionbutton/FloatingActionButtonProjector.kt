package com.bselzer.ktx.compose.ui.layout.floatingactionbutton

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.icon.IconProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.compose.ui.layout.text.TextProjector

class FloatingActionButtonProjector(
    interactor: FloatingActionButtonInteractor,
    presenter: FloatingActionButtonPresenter = FloatingActionButtonPresenter.Default
) : Projector<FloatingActionButtonInteractor, FloatingActionButtonPresenter>(interactor, presenter) {
    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        val textProjector = interactor.text?.let { TextProjector(it, presenter.text) }
        val iconProjector = interactor.icon?.let { IconProjector(it, presenter.icon) }

        if (textProjector == null) {
            FloatingActionButton(
                onClick = interactor.onClick,
                modifier = combinedModifier,
                interactionSource = interactionSource,
                shape = presenter.shape,
                backgroundColor = presenter.backgroundColor,
                contentColor = presenter.contentColor,
                elevation = presenter.elevation
            ) {
                iconProjector?.Projection()
            }
        } else {
            ExtendedFloatingActionButton(
                text = { textProjector.Projection() },
                onClick = interactor.onClick,
                modifier = combinedModifier,
                icon = iconProjector?.let { icon -> { icon.Projection() } },
                interactionSource = interactionSource,
                shape = presenter.shape,
                backgroundColor = presenter.backgroundColor,
                contentColor = presenter.contentColor,
                elevation = presenter.elevation
            )
        }
    }
}