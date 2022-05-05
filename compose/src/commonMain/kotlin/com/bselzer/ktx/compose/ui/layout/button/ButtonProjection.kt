package com.bselzer.ktx.compose.ui.layout.button

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
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
        val default = when (presentation.type) {
            ButtonType.Button -> containedButtonPresentation()
            ButtonType.OutlinedButton -> outlinedButtonPresentation()
            ButtonType.TextButton -> textButtonPresentation()
        }

        Button(
            onClick = logic.onClick,
            modifier = modifier,
            enabled = logic.enabled,
            interactionSource = interactionSource,
            elevation = ComposeMerger.buttonElevation.nullTake(presentation.elevation, default.elevation),
            shape = ComposeMerger.shape.safeTake(presentation.shape, default.shape),
            border = ComposeMerger.borderStroke.nullTake(presentation.border, default.border),
            colors = ComposeMerger.buttonColors.safeTake(presentation.colors, default.colors),
            contentPadding = ComposeMerger.paddingValues.safeTake(presentation.contentPadding, default.contentPadding),
            content = content
        )
    }
}