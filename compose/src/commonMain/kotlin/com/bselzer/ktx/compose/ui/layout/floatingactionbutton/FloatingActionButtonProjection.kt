package com.bselzer.ktx.compose.ui.layout.floatingactionbutton

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.icon.IconProjection
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.compose.ui.layout.text.TextProjection

class FloatingActionButtonProjection(
    override val logic: FloatingActionButtonLogic,
    override val presentation: FloatingActionButtonPresentation = FloatingActionButtonPresentation.Default
) : Projector<FloatingActionButtonLogic, FloatingActionButtonPresentation>() {
    private val textProjection = logic.text?.let { text -> TextProjection(text, presentation.text) }
    private val iconProjection = logic.icon?.let { icon -> IconProjection(icon, presentation.icon) }

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
    private fun FloatingActionButtonPresentation.WithoutText(
        modifier: Modifier,
        interactionSource: MutableInteractionSource
    ) = FloatingActionButton(
        onClick = logic.onClick,
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
    private fun FloatingActionButtonPresentation.WithText(
        modifier: Modifier,
        interactionSource: MutableInteractionSource,
        textProjection: TextProjection
    ) = ExtendedFloatingActionButton(
        text = { textProjection.project() },
        onClick = logic.onClick,
        modifier = modifier,
        icon = iconProjection?.let { icon -> { icon.project() } },
        interactionSource = interactionSource,
        shape = shape,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        elevation = elevation
    )
}