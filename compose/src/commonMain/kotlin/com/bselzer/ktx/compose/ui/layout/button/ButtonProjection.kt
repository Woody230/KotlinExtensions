package com.bselzer.ktx.compose.ui.layout.button

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector

class ButtonProjection(
    override val logic: ButtonLogic,
    override val presentation: ButtonPresentation = ButtonPresentation.Default
) : Projector<ButtonLogic, ButtonPresentation>() {
    @Composable
    fun project(
        modifier: Modifier = Modifier,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        content: @Composable RowScope.() -> Unit
    ) = contextualize {
        Button(
            onClick = logic.onClick,
            modifier = modifier,
            enabled = logic.enabled,
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