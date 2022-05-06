package com.bselzer.ktx.compose.ui.layout.radiobutton

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector

class RadioButtonProjection(
    override val logic: RadioButtonLogic,
    override val presentation: RadioButtonPresentation = RadioButtonPresentation.Default
) : Projector<RadioButtonLogic, RadioButtonPresentation>() {
    @Composable
    fun project(
        modifier: Modifier = Modifier,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    ) = contextualize {
        RadioButton(
            selected = logic.selected,
            onClick = logic.onClick,
            modifier = modifier,
            enabled = logic.enabled,
            interactionSource = interactionSource,
            colors = colors
        )
    }
}