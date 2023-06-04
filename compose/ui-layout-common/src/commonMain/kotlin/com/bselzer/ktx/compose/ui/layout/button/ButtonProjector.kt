package com.bselzer.ktx.compose.ui.layout.button

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector

class ButtonProjector(
    interactor: ButtonInteractor,
    presenter: ButtonPresenter = ButtonPresenter.Default
) : Projector<ButtonInteractor, ButtonPresenter>(interactor, presenter) {
    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        content: @Composable RowScope.() -> Unit
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        Button(
            onClick = interactor.onClick,
            modifier = combinedModifier,
            enabled = interactor.enabled,
            interactionSource = interactionSource,
            elevation = presenter.elevation,
            shape = presenter.shape,
            border = presenter.border,
            colors = presenter.colors,
            contentPadding = presenter.contentPadding,
            content = content
        )
    }
}