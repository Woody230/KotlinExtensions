package com.bselzer.ktx.compose.ui.layout.radiobutton

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector

class RadioButtonProjector(
    interactor: RadioButtonInteractor,
    presenter: RadioButtonPresenter = RadioButtonPresenter.Default
) : Projector<RadioButtonInteractor, RadioButtonPresenter>(interactor, presenter) {
    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        RadioButton(
            selected = interactor.selected,
            onClick = interactor.onClick,
            modifier = combinedModifier,
            enabled = interactor.enabled,
            interactionSource = interactionSource,
            colors = presenter.colors
        )
    }
}