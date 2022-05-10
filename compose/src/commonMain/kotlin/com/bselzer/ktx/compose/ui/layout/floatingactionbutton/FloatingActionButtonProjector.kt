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
    private val textProjection = interactor.text?.let { text -> TextProjector(text, presenter.text) }
    private val iconProjection = interactor.icon?.let { icon -> IconProjector(icon, presenter.icon) }

    @Composable
    fun project(
        modifier: Modifier = Modifier,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    ) = contextualize {
        if (textProjection == null) {
            WithoutText(modifier, interactionSource)
        } else {
            WithText(modifier, interactionSource, textProjection)
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
        iconProjection?.project()
    }

    @Composable
    private fun FloatingActionButtonPresenter.WithText(
        modifier: Modifier,
        interactionSource: MutableInteractionSource,
        textProjection: TextProjector
    ) = ExtendedFloatingActionButton(
        text = { textProjection.project() },
        onClick = interactor.onClick,
        modifier = modifier,
        icon = iconProjection?.let { icon -> { icon.project() } },
        interactionSource = interactionSource,
        shape = shape,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        elevation = elevation
    )
}