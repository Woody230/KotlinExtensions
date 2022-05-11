package com.bselzer.ktx.compose.ui.layout.textbutton

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.button.ButtonProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.compose.ui.layout.text.TextProjector

class TextButtonProjector(
    override val interactor: TextButtonInteractor,
    override val presenter: TextButtonPresenter
) : Projector<TextButtonInteractor, TextButtonPresenter>() {
    private val buttonProjector = ButtonProjector(interactor.button, presenter.button)
    private val textProjector = TextProjector(interactor.text, presenter.text)

    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
    ) = contextualize(modifier) { combinedModifier ->
        buttonProjector.Projection(
            modifier = combinedModifier,
            interactionSource = interactionSource
        ) {
            textProjector.Projection()
        }
    }
}