package com.bselzer.ktx.compose.ui.layout.textbutton

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.button.ButtonProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.compose.ui.layout.text.TextProjector

class TextButtonProjector(
    interactor: TextButtonInteractor,
    presenter: TextButtonPresenter
) : Projector<TextButtonInteractor, TextButtonPresenter>(interactor, presenter) {

    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        val buttonProjector = ButtonProjector(interactor.button, presenter.button)
        val textProjector = TextProjector(interactor.text, presenter.text)

        buttonProjector.Projection(
            modifier = combinedModifier,
            interactionSource = interactionSource
        ) {
            textProjector.Projection()
        }
    }
}