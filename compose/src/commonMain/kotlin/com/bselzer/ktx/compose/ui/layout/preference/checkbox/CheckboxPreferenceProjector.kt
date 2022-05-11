package com.bselzer.ktx.compose.ui.layout.preference.checkbox

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.checkbox.CheckboxProjector
import com.bselzer.ktx.compose.ui.layout.preference.PreferenceProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector

class CheckboxPreferenceProjector(
    override val interactor: CheckboxPreferenceInteractor,
    override val presenter: CheckboxPreferencePresenter = CheckboxPreferencePresenter.Default
) : Projector<CheckboxPreferenceInteractor, CheckboxPreferencePresenter>() {
    private val preferenceProjector = PreferenceProjector(interactor.preference, presenter.preference)
    private val checkboxProjector = CheckboxProjector(interactor.checkbox, presenter.checkbox)

    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    ) = contextualize(modifier) { combinedModifier ->
        preferenceProjector.Projection(combinedModifier) {
            checkboxProjector.Projection(interactionSource = interactionSource)
        }
    }
}