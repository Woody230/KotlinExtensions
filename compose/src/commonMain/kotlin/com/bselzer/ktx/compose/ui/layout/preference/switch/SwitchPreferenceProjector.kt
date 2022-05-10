package com.bselzer.ktx.compose.ui.layout.preference.switch

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.preference.PreferenceProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.compose.ui.layout.switch.SwitchProjector

class SwitchPreferenceProjector(
    override val interactor: SwitchPreferenceInteractor,
    override val presenter: SwitchPreferencePresenter = SwitchPreferencePresenter.Default
) : Projector<SwitchPreferenceInteractor, SwitchPreferencePresenter>() {
    private val preferenceProjector = PreferenceProjector(interactor.preference, presenter.preference)
    private val switchProjector = SwitchProjector(interactor.switch, presenter.switch)

    @Composable
    fun project(
        modifier: Modifier = Modifier,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    ) = contextualize(modifier) { combinedModifier ->
        preferenceProjector.project(combinedModifier) {
            switchProjector.project(interactionSource = interactionSource)
        }
    }
}