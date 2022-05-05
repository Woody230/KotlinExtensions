package com.bselzer.ktx.compose.ui.layout.button

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projectable

class ButtonProjection(
    override val logic: ButtonLogic = ButtonLogic(),
    override val presentation: ButtonPresentation = ButtonPresentation.Default
) : Projectable<ButtonLogic, ButtonPresentation> {
    @Composable
    fun project(
        modifier: Modifier = Modifier,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        content: @Composable RowScope.() -> Unit
    ) {
        val presentation = if (this.presentation !== ButtonPresentation.Default) this.presentation else containedButtonPresentation()
        Button(
            onClick = logic.onClick,
            modifier = modifier,
            enabled = logic.enabled,
            interactionSource = interactionSource,
            elevation = presentation.elevation,
            shape = presentation.shape,
            border = presentation.border,
            colors = presentation.colors,
            contentPadding = presentation.contentPadding,
            content = content
        )
    }
}