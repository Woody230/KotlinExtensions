package com.bselzer.ktx.compose.ui.layout.textbutton

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.button.ButtonProjection
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.compose.ui.layout.text.TextProjection

class TextButtonProjection(
    override val logic: TextButtonLogic,
    override val presentation: TextButtonPresentation
) : Projector<TextButtonLogic, TextButtonPresentation>() {
    private val button = ButtonProjection(logic.button, presentation.button)
    private val text = TextProjection(logic.text, presentation.text)

    @Composable
    fun project(
        modifier: Modifier = Modifier,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
    ) = button.project(
        modifier = modifier,
        interactionSource = interactionSource
    ) {
        text.project()
    }
}