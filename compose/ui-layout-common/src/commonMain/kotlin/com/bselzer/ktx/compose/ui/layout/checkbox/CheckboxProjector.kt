package com.bselzer.ktx.compose.ui.layout.checkbox

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector

class CheckboxProjector(
    interactor: CheckboxInteractor,
    presenter: CheckboxPresenter = CheckboxPresenter.Default
) : Projector<CheckboxInteractor, CheckboxPresenter>(interactor, presenter) {
    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        Checkbox(
            checked = interactor.checked,
            onCheckedChange = interactor.onCheckedChange,
            modifier = combinedModifier,
            enabled = interactor.enabled,
            interactionSource = interactionSource,
            colors = presenter.colors
        )
    }
}