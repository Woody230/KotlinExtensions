package com.bselzer.ktx.compose.ui.layout.preference.checkbox

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.checkbox.CheckboxProjector
import com.bselzer.ktx.compose.ui.layout.preference.PreferenceProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector

class CheckboxPreferenceProjector(
    interactor: CheckboxPreferenceInteractor,
    presenter: CheckboxPreferencePresenter = CheckboxPreferencePresenter.Default
) : Projector<CheckboxPreferenceInteractor, CheckboxPreferencePresenter>(interactor, presenter) {
    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        val preferenceProjector = PreferenceProjector(interactor.preference, presenter.preference)
        val checkboxProjector = CheckboxProjector(interactor.checkbox, presenter.checkbox)

        preferenceProjector.Projection(combinedModifier) {
            checkboxProjector.Projection(interactionSource = interactionSource)
        }
    }
}