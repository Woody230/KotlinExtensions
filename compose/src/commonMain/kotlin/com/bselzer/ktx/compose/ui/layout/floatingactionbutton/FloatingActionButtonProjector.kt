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
    override val interactor: FloatingActionButtonInteractor,
    override val presenter: FloatingActionButtonPresenter = FloatingActionButtonPresenter.Default
) : Projector<FloatingActionButtonInteractor, FloatingActionButtonPresenter>() {
    private val textProjector = interactor.text?.let { text -> TextProjector(text, presenter.text) }
    private val iconProjector = interactor.icon?.let { icon -> IconProjector(icon, presenter.icon) }

    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    ) = contextualize(modifier) { combinedModifier ->
        if (textProjector == null) {
            WithoutText(combinedModifier, interactionSource)
        } else {
            WithText(combinedModifier, interactionSource, textProjector)
        }
    }

    @Composable
    private fun FloatingActionButtonPresenter.WithoutText(
        modifier: Modifier,
        interactionSource: MutableInteractionSource
    ) = FloatingActionButton(
        onClick = interactor.onClick,
        modifier = modifier,
        interactionSource = interactionSource,
        shape = shape,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        elevation = elevation
    ) {
        iconProjector?.Projection()
    }

    @Composable
    private fun FloatingActionButtonPresenter.WithText(
        modifier: Modifier,
        interactionSource: MutableInteractionSource,
        textProjector: TextProjector
    ) = ExtendedFloatingActionButton(
        text = { textProjector.Projection() },
        onClick = interactor.onClick,
        modifier = modifier,
        icon = iconProjector?.let { icon -> { icon.Projection() } },
        interactionSource = interactionSource,
        shape = shape,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        elevation = elevation
    )
}