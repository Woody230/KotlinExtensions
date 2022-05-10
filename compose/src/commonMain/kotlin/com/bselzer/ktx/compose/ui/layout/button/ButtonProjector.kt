package com.bselzer.ktx.compose.ui.layout.button

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector

class ButtonProjector(
    override val interactor: ButtonInteractor,
    override val presenter: ButtonPresenter = ButtonPresenter.Default
) : Projector<ButtonInteractor, ButtonPresenter>() {
    @Composable
    fun project(
        modifier: Modifier = Modifier,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        content: @Composable RowScope.() -> Unit
    ) = contextualize(modifier) { combinedModifier ->
        Button(
            onClick = interactor.onClick,
            modifier = combinedModifier,
            enabled = interactor.enabled,
            interactionSource = interactionSource,
            elevation = elevation,
            shape = shape,
            border = border,
            colors = colors,
            contentPadding = contentPadding,
            content = content
        )
    }
}